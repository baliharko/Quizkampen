package Quiz.ClientSide;

import Quiz.ServerSide.Database;
import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Question;
import Quiz.ServerSide.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:33
 * Project: Inlämning04
 * Copyright: MIT
 */

public class ClientProtocol {

    private Properties gameProperties;
    private int questionsAmount;
    private int roundsAmount;
    private int categoriesAmount;

    Database database;
    private String player1Name;
    private String player2Name;
    private boolean bothConnected;
    private ObjectOutputStream player1out;
    private ObjectOutputStream player2out;
    private Question p1CurrentQuestion;
    private Question p2CurrentQuestion;
    int player1Score = 0;
    int player2Score = 0;
//    private int counter = 4;

    private boolean[][] p1Answers;
    private boolean[][] p2Answers;

    private int p1CurrentQuestionCounter;
    private int p2CurrentQuestionCounter;
    private List<Question> currentGenre;
    private int currentRound;

    private boolean p1RoundFinished;
    private boolean p2RoundFinished;

    public enum State {
        WAITING, PLAYER_1_CONNECTED, GAME_RUNNING, SELECT_CATEGORY_FIRST
    }

    private State currentState;

    public ClientProtocol(Database database) {

        this.gameProperties = new Properties();
        this.getGameProperties();
        this.questionsAmount = Integer.parseInt(this.gameProperties.getProperty("Questions", "2"));
        this.roundsAmount = Integer.parseInt(this.gameProperties.getProperty("Rounds", "2"));
        this.categoriesAmount = Integer.parseInt(this.gameProperties.getProperty("Categories", "4"));
        this.currentState = State.WAITING;
        this.database = database;
        this.p1CurrentQuestionCounter = 0;
        this.p2CurrentQuestionCounter = 0;
        this.p1Answers = new boolean[this.roundsAmount][this.questionsAmount];
        this.p2Answers = new boolean[this.roundsAmount][this.questionsAmount];
    }

    public synchronized void processInput(Object in, int playerId) {
        switch (this.currentState) {
            case WAITING -> {
                if (in instanceof Request && ((Request) in).getStatus() == RequestStatus.INIT) {
                    try {
                        if (playerId == 2) {
                            System.out.println("Player 2 connected first");
                        } else {
                            currentState = State.PLAYER_1_CONNECTED;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            case PLAYER_1_CONNECTED -> {
                if (in instanceof Request && ((Request) in).getStatus() == RequestStatus.INIT && playerId == 2) {
                    try {
                        sendObject(new Initializer(this.player1Name, this.player2Name), 1);
                        sendObject(new Initializer(this.player2Name, this.player1Name), 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("protocol - BOTH_CONNECTED");
                }

                if (this.currentRound == 0) {
                    sendObject(new Response(Response.ResponseStatus.SELECT_CATEGORY), 1);
                    sendObject(new Response(Response.ResponseStatus.WAIT), 2);
                    currentState = State.SELECT_CATEGORY_FIRST;
                }
            }
            case SELECT_CATEGORY_FIRST -> {
                if (in instanceof Request && ((Request) in).getStatus() == RequestStatus.CATEGORY_SELECTED) {
                    if (this.currentRound == 0 && playerId == 1) {
                        // Använder playername pga Konstruktor i Request-klassen, är egentligen category i detta fall.
                        this.currentGenre = database.getQuestionByCategory(((Request) in).getPlayerName());
                        this.p1CurrentQuestion = this.currentGenre.get(0);
                        this.p2CurrentQuestion = this.currentGenre.get(0);

                        sendObject(new Response(Response.ResponseStatus.NEW_ROUND_START, this.p1CurrentQuestion), 1);
                        sendObject(new Response(Response.ResponseStatus.NEW_ROUND_START, this.p2CurrentQuestion), 2);
                        this.currentState = State.GAME_RUNNING;
                    } else {
                        sendObject(new Response(Response.ResponseStatus.WAIT), playerId);
                    }

                }
            }
            case GAME_RUNNING -> {
                if (in instanceof Request) {
                    if (((Request) in).getStatus() == RequestStatus.ANSWER) {
                        if (playerId == 1) {
                            boolean answer = this.p1CurrentQuestion.isRightAnswer(((Request) in).getAnswerText());

                            sendObject(new Response(
                                            Response.ResponseStatus.CHECKED_ANSWER, answer),
                                    playerId);

                            this.p1Answers[this.currentRound][this.p1CurrentQuestionCounter] = answer;
                        }

                        if (playerId == 2) {
                            boolean answer = this.p2CurrentQuestion.isRightAnswer(((Request) in).getAnswerText());

                            sendObject(new Response(
                                            Response.ResponseStatus.CHECKED_ANSWER, answer),
                                    playerId);

                            this.p2Answers[this.currentRound][this.p2CurrentQuestionCounter] = answer;
                        }
                    }

                    if (((Request) in).getStatus() == RequestStatus.NEXT_QUESTION) {
                        if (playerId == 1) {
                            this.p1CurrentQuestionCounter++;
                            if (this.p1CurrentQuestionCounter < this.questionsAmount) {
                                this.p1CurrentQuestion = this.currentGenre.get(this.p1CurrentQuestionCounter);
                                sendObject(new Response(Response.ResponseStatus.NEW_QUESTION, this.p1CurrentQuestion), playerId);
                            } else {
                                sendObject(new Response(Response.ResponseStatus.WAIT), playerId);
                                updatePlayerScore(1);
                                this.p1RoundFinished = true;
                            }
                        }

                        if (playerId == 2) {
                            this.p2CurrentQuestionCounter++;
                            if (this.p2CurrentQuestionCounter < this.questionsAmount) {
                                this.p2CurrentQuestion = this.currentGenre.get(this.p2CurrentQuestionCounter);
                                sendObject(new Response(Response.ResponseStatus.NEW_QUESTION, this.p2CurrentQuestion), playerId);
                            } else {
                                sendObject(new Response(Response.ResponseStatus.WAIT), playerId);
                                updatePlayerScore(2);
                                System.out.println("player 2 score = " + player2Score);
                                this.p2RoundFinished = true;
                            }
                        }
                    }

                    if (((Request) in).getStatus() == RequestStatus.CATEGORY_SELECTED) {

                        this.currentGenre = this.database.getQuestionByCategory(((Request) in).getPlayerName());
                        this.p1CurrentQuestion = this.currentGenre.get(0);
                        this.p2CurrentQuestion = this.currentGenre.get(0);
                        sendObject(new Response(Response.ResponseStatus.NEW_ROUND_START, this.p1CurrentQuestion), 1);
                        sendObject(new Response(Response.ResponseStatus.NEW_ROUND_START, this.p2CurrentQuestion), 2);
                    }

                    if (((Request) in).getStatus() == RequestStatus.NEXT_ROUND) {

                        if (playerId == 1) {
                            sendObject(this.currentRound % 2 == 0 ?
                                    new Response(Response.ResponseStatus.SELECT_CATEGORY) : new Response(Response.ResponseStatus.WAIT), playerId);
                        }

                        if (playerId == 2) {
                            sendObject(this.currentRound % 2 != 0 ?
                                    new Response(Response.ResponseStatus.SELECT_CATEGORY) : new Response(Response.ResponseStatus.WAIT), playerId);
                        }

//                        if (this.currentRound % 2 == 0) {
//                            if (playerId == 1)
//                                sendObject(new Response(Response.ResponseStatus.SELECT_CATEGORY), 1);
//                            else
//                                sendObject(new Response(Response.ResponseStatus.WAIT), 2);
//                        } else {
//                            if (playerId == 1)
//                                sendObject(new Response(Response.ResponseStatus.SELECT_CATEGORY), 1);
//                            else
//                                sendObject(new Response(Response.ResponseStatus.WAIT), 2);
//                        }
                    }
                }

                if (p1RoundFinished && p2RoundFinished) {
                    this.p1RoundFinished = false;
                    this.p2RoundFinished = false;
                    this.p1CurrentQuestionCounter = 0;
                    this.p2CurrentQuestionCounter = 0;

                    sendObject(new Response(Response.ResponseStatus.RESULTS, this.currentRound, p1Answers[this.currentRound], p2Answers[this.currentRound], player1Score, player2Score), 1);
                    sendObject(new Response(Response.ResponseStatus.RESULTS, this.currentRound, p2Answers[this.currentRound], p1Answers[this.currentRound], player2Score, player1Score), 2);
                    this.currentRound++;
                }
            }
        }
    }

    public void setPlayer(String playerName, int playerId) {
        if (playerId == 1 && this.player1Name == null) {
            System.out.println("set player 1 to " + playerName);
            this.player1Name = playerName;
        } else if (playerId == 2 && this.player2Name == null) {
            System.out.println("set player 2 to " + playerName);
            this.player2Name = playerName;
        } else {
            System.out.println("both names already set.");
        }
    }

    public void setPlayerOuts(ObjectOutputStream player1Out, ObjectOutputStream player2out) {
        this.player1out = player1Out;
        this.player2out = player2out;
    }

    private void sendObject(Object object, int playerId) {
        try {
            if (playerId == 1)
                this.player1out.writeObject(object);
            else if (playerId == 2)
                this.player2out.writeObject(object);
            else
                throw new Exception();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sent " + object + " to player " + playerId);
    }

    private void getGameProperties() {

        if (!new File("src/main/java/Quiz/ClientSide/GameSetup.properties").exists()) {
            try {
                Files.createFile(Path.of("src/main/java/Quiz/ClientSide/GameSetup.properties"));

                try (FileWriter fw = new FileWriter("src/main/java/Quiz/ClientSide/GameSetup.properties")) {
                    fw.append("Categories=4\n");
                    fw.append("Questions=2\n");
                    fw.append("Rounds=2");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.gameProperties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
                this.gameProperties.setProperty("Categories", "4");
                this.gameProperties.setProperty("Questions", "2");
                this.gameProperties.setProperty("Rounds", "2");
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Failed to create file.");
                e.printStackTrace();
            }
        }

        try {
            this.gameProperties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePlayerScore(int playerId) {
        if (playerId == 1) {
            for (boolean ans : p1Answers[currentRound]) {
                if (ans)
                    this.player1Score++;
            }
        }

        if (playerId == 2) {
            for (boolean ans : p2Answers[currentRound]) {
                if (ans)
                    this.player2Score++;
            }
        }
    }
}
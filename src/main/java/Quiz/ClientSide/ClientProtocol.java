package Quiz.ClientSide;

import Quiz.ServerSide.Database;
import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Question;
import Quiz.ServerSide.Response;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:33
 * Project: Inlämning04
 * Copyright: MIT
 */

public class ClientProtocol {

    Database database;
    private String player1Name;
    private String player2Name;
    private boolean bothConnected;
    private ObjectOutputStream player1out;
    private ObjectOutputStream player2out;
    private int questionNo;
    private Question currentQuestion;
    int currentRound = 1;
    int player1Score = 0;
    int player2Score = 0;
    private int counter = 4;

    public enum State {
        WAITING, PLAYER_1_CONNECTED, BOTH_CONNECTED, ANSWER_RECEIVED, QUESTION_SENT,
    }

    private State currentState;

    public ClientProtocol(Database database) {
        this.currentState = State.WAITING;
        this.database = database;
        this.questionNo = 0;
    }

    //Tillfällig fråga avsedd för test
    //Question testQuestion = new Question("Nu kom en fråga från servern!", "Rätt svar", new String[] { "Åsna", "Rätt svar", "Orm", "Cykel" });

    public synchronized Object ProcessInput(Object in) {
        Object out = null;

        if (this.areBothConnected())
            this.currentState = State.BOTH_CONNECTED;

        switch (this.currentState) {
            case WAITING -> {
                if (in instanceof String && ((String)in).equalsIgnoreCase("init")) {
                    out = new Initializer();
                    currentState = State.PLAYER_1_CONNECTED;
                }
            }
            case PLAYER_1_CONNECTED -> {
                if (in instanceof String && ((String)in).equalsIgnoreCase("init")) {

                    this.currentQuestion = database.getQuestion(this.questionNo);
                    try {
                        out = new Initializer(); // skicka init till player2
                        synchronized (this) {
                            this.player1out.writeObject(new Initializer(this.player1Name, this.player2Name, this.currentQuestion));
                        }

                        synchronized (this) {
                            this.player2out.writeObject(new Initializer(this.player2Name, this.player1Name, this.currentQuestion));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.bothConnected = true;
                    currentState = State.BOTH_CONNECTED;
                    System.out.println("protocol - BOTH_CONNECTED");
                }
            }
            case BOTH_CONNECTED -> {
                if (in instanceof Request) {

                    if (((Request)in).getStatus() == RequestStatus.ANSWER) {
                        // Skickar true eller false för rätt eller fel svar
                        out = new Response(Response.ResponseStatus.CHECKED_ANSWER, this.currentQuestion.isRightAnswer(((Request) in).getAnswerText())); // Skickar tillbaka index på knappen
                    }

                    if (((Request)in).getStatus() == RequestStatus.NEXT_QUESTION) {
                        out = new Response(Response.ResponseStatus.NEW_QUESTION, this.database.getQuestion(++questionNo));
                    }
                }

//                String inAns = in.split(",")[1];
//                String plAns = in.split(",")[0];

//                out = this.currentQuestion.isRightAnswer(inAns) ? "Correct" : "False";
//
//                if (out.equals("Correct") && plAns.equalsIgnoreCase(player1Name)) {
//                    System.out.println("Poäng ++ " + plAns);
//                    player1Score++;
//                    System.out.println(player1Score);
//                } else if (out.equals("Correct") && plAns.equalsIgnoreCase(player2Name)) {
//                    System.out.println("Poäng ++ " + plAns);
//                    player2Score++;
//                    System.out.println(player2Score);
//                } else if (out.equals("False") && plAns.equalsIgnoreCase(player1Name)) {
//                    System.out.println("Fel svar " + plAns);
//                    System.out.println(player1Score);
//                } else if (out.equals("False") && plAns.equalsIgnoreCase(player2Name)) {
//                    System.out.println("Fel svar " + plAns);
//                    System.out.println(player2Score);
//                }
                getRoundNumber();
            }
        }
        return out;
    }

    public  int getRoundNumber() {
        counter--;
        System.out.println("Round: "+currentRound);
        if (counter == 0) {
            currentRound++;
            this.counter=4;
            //System.out.println("Round: "+currentRound);
        }
        return currentRound;
    }

    public synchronized void setPlayer(String playerName) {
        if (this.player1Name == null) {
            System.out.println("set player 1 to " + playerName);
            this.player1Name = playerName;
        } else if (this.player2Name == null) {
            System.out.println("set player 2 to " + playerName);
            this.player2Name = playerName;
        } else {
            System.out.println("both names already set.");
        }
    }

    public boolean areBothConnected() {
        return bothConnected;
    }

    public synchronized void setPlayerOut(ObjectOutputStream playerOut) {
        if (player1out != null) {
            player2out = playerOut;
        } else
            player1out = playerOut;
    }
}




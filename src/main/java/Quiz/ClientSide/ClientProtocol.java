package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Question;

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

    private String player1Name;
    private String player2Name;
    private boolean bothConnected;
    private ObjectOutputStream player1out;
    private ObjectOutputStream player2out;


    public enum State {
        WAITING, PLAYER_1_CONNECTED, BOTH_CONNECTED, ANSWER_RECEIVED, QUESTION_SENT,
    }

    private State currentState;

    public ClientProtocol() {
        this.currentState = State.WAITING;
    }

    // Tillfällig fråga avsedd för test
    Question testQuestion = new Question("Nu kom en fråga från servern!", "Rätt svar", new String[] { "Åsna", "Rätt svar", "Orm", "Cykel" });

    public synchronized Object ProcessInput(String in) {
        Object out = null;

        if (this.areBothConnected())
            this.currentState = State.BOTH_CONNECTED;

        switch (this.currentState) {
            case WAITING -> {
                System.out.println("protocol - WAITING");
                if (in.equalsIgnoreCase("init")) {
                    System.out.println("protocol - WAITING (init)");
                    out = new Initializer();
                    currentState = State.PLAYER_1_CONNECTED;
                    System.out.println("Player 1 connected - waiting");
                }
            }
            case PLAYER_1_CONNECTED -> {
                System.out.println("protocol - PLAYER_1_CONNECTED");
                if (in.equalsIgnoreCase("init")) {
                    System.out.println("protocol - PLAYER_1_CONNECTED (init)");
//                    out = new Initializer(this.player2Name, this.player1Name, testQuestion);
                    try {
                        player1out.writeObject(new Initializer(this.player1Name, this.player2Name, this.testQuestion));
                        player2out.writeObject(new Initializer(this.player2Name, this.player1Name, this.testQuestion));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.bothConnected = true;
                    currentState = State.BOTH_CONNECTED;
                }
            }
            case BOTH_CONNECTED -> {
                System.out.println("protocol - BOTH_CONNECTED");
                out = testQuestion.isRightAnswer(in) ? "Correct" : "False";
                // Ändrar inte state p.g.a test för tilfället
            }
        }
        return out;
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

    public String getPlayer1Name() {
        return this.player1Name;
    }

    public String getPlayer2Name() {
        return this.player2Name;
    }

    public Question getCurrentQuestion() {
        return this.testQuestion; // Test
    }

    public boolean areBothConnected() {
        return bothConnected;
    }

    public void setPlayerOut(ObjectOutputStream playerOut) {
        if (player1out != null) {
            player2out = playerOut;
        } else
            player1out = playerOut;
    }
}




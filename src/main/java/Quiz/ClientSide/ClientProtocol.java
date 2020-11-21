package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Question;

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

    private enum State {
        WAITING, PLAYER_1_CONNECTED, PLAYER_2_CONNECTED, ANSWER_RECEIVED, QUESTION_SENT,
    }

    private State currentState;

    public ClientProtocol() {
        this.currentState = State.WAITING;
    }

    // Tillfällig fråga avsedd för test
    Question testQuestion = new Question("HEJHEJEHEJ VAD HETER JAG", "Rätt svar", new String[] {"Åsna", "Rätt svar", "Orm", "Cykel"});

    public synchronized Object ProcessInput(String in) {
        Object out = null;
        switch (this.currentState) {
            case WAITING -> {
                if (in.equalsIgnoreCase("init")) {
                    out = new Initializer(this.player1Name, this.player2Name, testQuestion); //Player 1 och Player 2 strängar ska ersättas med namn man får av Client
                    currentState = State.PLAYER_1_CONNECTED;
                }
            }
            case PLAYER_1_CONNECTED -> {
                if (in.equalsIgnoreCase("init")) {
                    out = new Initializer(this.player2Name, this.player1Name, testQuestion);
                    currentState = State.PLAYER_2_CONNECTED;
                }
            }
            case PLAYER_2_CONNECTED -> {
                out = testQuestion.isRightAnswer(in) ? "Correct" : "False";
                // Ändrar inte state p.g.a test för tilfället
            }
        }
        return out;
    }

    public synchronized void setPlayer(String playerName) {
        if (this.player1Name != null) {
            this.player2Name = playerName;
        } else {
            this.player1Name = playerName;
        }
    }
}




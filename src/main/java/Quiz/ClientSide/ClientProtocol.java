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

    private enum State {
        WAITING, PLAYER_1_CONNECTED, PLAYER_2_CONNECTED, ANSWER_RECEIVED, QUESTION_SENT,
    }

    private State currentState = State.WAITING;

    // ENDAST TEST
    Question testQuestion = new Question("HEJHEJEHEJ VAD HETER JAG", "Rätt svar", new String[] {"Åsna", "Rätt svar", "Orm", "Cykel"});

    public synchronized Object ProcessInput(String in) {
        Object out = null;

        switch (this.currentState) {
            case WAITING -> {
                if (in.equalsIgnoreCase("init")) {
                    out = new Initializer("Player 1", "Player 2", testQuestion); // Namn som vi har tagit emot från client
                    currentState = State.PLAYER_1_CONNECTED;
                }
            }
            case PLAYER_1_CONNECTED -> {
                if (in.equalsIgnoreCase("init")) {
                    out = new Initializer("Player 2", "Player 1", testQuestion); // Namn som vi har tagit emot från client
                    currentState = State.PLAYER_2_CONNECTED;
                }
            }
            case PLAYER_2_CONNECTED -> {
                out = testQuestion.isRightAnswer(in) ? "Correct" : "False";
//                currentState = State.ANSWER_RECEIVED;
            }
        }
        return out;
    }
}




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

    enum State {
        WAITING, PLAYER_1_CONNECTED, PLAYER_2_CONNECTED, READY, QUESTION_SENT, ANSWER_RECEIVED
    }

    private State state = State.WAITING;

    // ENDAST TEST
    Question testQuestion = new Question("HEJHEJEHEJ VAD HETER JAG", "Rätt svar", new String[] {"Åsna", "Rätt svar", "Orm", "Cykel"});

    public synchronized Object ProcessInput(String in) {
        Object out = null;
        if (state == State.WAITING && in.equalsIgnoreCase("init")) {
            out = new Initializer("Player 1", "Player 2", testQuestion); // Namn som vi har tagit emot från client
            state = State.PLAYER_1_CONNECTED;
        } else if (state == State.PLAYER_1_CONNECTED && in.equalsIgnoreCase("init")) {
            out = new Initializer("Player 2", "Player 1", testQuestion); // Namn som vi har tagit emot från client
            state = State.PLAYER_2_CONNECTED;
        }
        return out;
    }
}




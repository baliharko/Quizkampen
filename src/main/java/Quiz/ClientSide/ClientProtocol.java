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

    enum State { WAITING, READY, QUESTION_SENT, ANSWER_RECEIVED }
    private State state = State.WAITING;

    // ENDAST TEST
    Question testQuestion = new Question("HEJHEJEHEJ VAD HETER JAG", "Rätt svar", new String[] {"Åsna", "Rätt svar", "Orm", "Cykel"});

    public Object ProcessInput(String in) {
        Object out = null;

        if (state == State.WAITING) {
            out = new Initializer("Player", "Opponent", testQuestion); // Namn som vi har tagit emot från client
            state = State.READY;
        } else if (state == State.READY) {
            out = testQuestion;
            state = State.QUESTION_SENT;
        }
        return out;
    }
}




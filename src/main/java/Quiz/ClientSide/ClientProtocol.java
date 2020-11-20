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

    // ENDAST TEST
    Question testQuestion = new Question("HEJHEJEHEJ VAD HETER JAG", "Rätt svar", new String[] {"svar1", "Rätt svar", "svar3", "svar4"});

    enum State { WAITING, READY, QUESTION_SENT, ANSWER_RECEIVED };
    private State state = State.WAITING;

    public Object ProcessInput(String in) {
        Object out = null;

        if (state == State.WAITING) {
            out = new Initializer("player", "opponent", testQuestion); // Namn som vi har tagit emot från client
            state = State.READY;
        } else if (state == State.READY) {
            out = "Playername joined the game!";
        }
        return out;
    }
}




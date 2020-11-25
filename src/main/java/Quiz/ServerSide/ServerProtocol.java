package Quiz.ServerSide;

import Quiz.ClientSide.QuizProtocol;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-20
 * Time:    15:09
 * Project: Inlämning04
 * Copyright: MIT
 */

public class ServerProtocol {

    private enum State {
        BEFORE_INIT, SENT_QUESTION_TO_PLAYERS, PLAYERS_ANSWERED,
        VALIDATE_ANSWERS, VALIDATION_SENT
    }

    private State currentState = State.BEFORE_INIT;
    //    Database dao = new Database();
    Question demoQuestion = new Question("Vad blir summan av 1+1?", "Correct", new String[]{"1", "2", "3", "4"});

    public Object ProcessInput(String input){
        Object output = null;

        if(currentState == State.BEFORE_INIT){
            output = demoQuestion;
            currentState = State.SENT_QUESTION_TO_PLAYERS;
        }
        else if(currentState == State.SENT_QUESTION_TO_PLAYERS){
            if(demoQuestion.)

        }
    }


}

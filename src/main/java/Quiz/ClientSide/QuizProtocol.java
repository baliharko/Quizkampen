package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Question;
import Quiz.ServerSide.ServerProtocol;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-23
 * Time:    16:55
 * Project: Inlämning04
 * Copyright: MIT
 */
public class QuizProtocol {
   /* //    private void gameRound
    private enum State {
        PLAYERS_CONNECTED, SENT_QUESTION_TO_PLAYERS, PLAYERS_ANSWERED,
        VALIDATE_ANSWERS, VALIDATION_SENT
    }

    private State currentState = State.PLAYERS_CONNECTED;
    //    Database dao = new Database();
    Question tQuestion = new Question("Vad blir summan av 1+1?", "Rätt svar", new String[]{"1", "2", "3", "4"});

    String answerFromDb;
    private int p1Score;
    private int p2Score;

    public void processInput(String in) {
        Object p1out = null;
        Object p2out = null;
        if (currentState == State.PLAYERS_CONNECTED) {
            p1out = new Initializer("Player 1", "Player 2", tQuestion);
            p2out = new Initializer("Player 2", "Player 1", tQuestion);
            currentState = State.SENT_QUESTION_TO_PLAYERS;
        }else if (currentState == State.SENT_QUESTION_TO_PLAYERS) {
            p1out = tQuestion.isRightAnswer(in) ? "Correct" : "False";
            if (tQuestion){
                p1Score++;
            }
            p2out = tQuestion.isRightAnswer(in) ? "Correct" : "False";
            currentState = State.PLAYERS_ANSWERED;
        }
        else if (currentState == State.PLAYERS_ANSWERED){
            if (p1out )
        }

    }
*/

}

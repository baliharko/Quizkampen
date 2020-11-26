package Quiz.ClientSide;

import Quiz.ServerSide.Databas;
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

    Databas db = new Databas();

    private enum State {
        PLAYERS_CONNECTED, SENT_QUESTION_TO_PLAYERS, PLAYERS_ANSWERED,
        VALIDATE_ANSWERS, VALIDATION_SENT
    }

    private int p1Score;
    private int p2Score;
    private int round;
    private State currentState;

    public QuizProtocol(Databas db) {
        this.currentState = State.PLAYERS_CONNECTED;
        this.db = db;
    }
//    Question tQuestion = new Question("Vad blir summan av 1+1?", "Rätt svar", new String[]{"1", "2", "3", "4"});

//    String answerFromDb = this.db.getAnswer;


    public void processInput(String in) {
        Object p1out = null;
        Object p2out = null;
        if (currentState == State.PLAYERS_CONNECTED) {
            //p1out = new Initializer("Player 1", "Player 2", tQuestion);
            p1out = new Initializer("Player 1", "Player 2", db.getQuestion());
            p2out = new Initializer("Player 2", "Player 1", db.getQuestion());
            currentState = State.SENT_QUESTION_TO_PLAYERS;
        }else if (currentState == State.SENT_QUESTION_TO_PLAYERS) {
//            p1out = answerFromDb.isRightAnswer(in) ? "Correct" : "False";
//            if (){
//                p1Score++;
            }
//            p2out = tQuestion.isRightAnswer(in) ? "Correct" : "False";
            currentState = State.PLAYERS_ANSWERED;
        }
/*        else if (currentState == State.PLAYERS_ANSWERED){

        }

    }

 */

}

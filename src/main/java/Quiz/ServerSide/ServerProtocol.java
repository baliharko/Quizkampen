package Quiz.ServerSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-20
 * Time:    15:09
 * Project: Inlämning04
 * Copyright: MIT
 */

public class ServerProtocol {

    private enum State {
        WAITING, SEND_CATEGORIES, RECEIVE_CATEGORIES, SEND_QUESTION, RECIEVE_ANSWER,
        VALIDATE_ANSWERS
    }

    private State currentState = State.WAITING;
    //    Database dao = new Database();
    Question demoQuestion = new Question("Vad blir summan av 1+1?", "Correct", new String[]{"1", "2", "3", "4"});

    public Object ProcessInput(String input) {
        Object p1output = null;
        Object p2output = null;

        if (currentState == State.WAITING) {
            p1output = "Skicka kategorier";
            p2output = "Wait";
            currentState = State.SEND_CATEGORIES;
        } else if (currentState == State.SEND_CATEGORIES) {
            p1output = "Ta emot kategorival";
            currentState = State.RECEIVE_CATEGORIES;
        } else if (currentState == State.RECEIVE_CATEGORIES) {
            p1output = "Skicka frågor till p1";
            p2output = "Wait";
            currentState = State.SEND_QUESTION;
        } else if (currentState == State.SEND_QUESTION) {
            p1output = "Ta emot svar från p1";
            p2output = "Skicka frågor till p2";
            currentState = State.RECIEVE_ANSWER;
        } else if (currentState == State.RECIEVE_ANSWER) {
            p1output = "Wait";
            p2output = "Ta emot svar p2";
        }

        //Repetera,
        // skicka ut resultat till p1
        // skicka ut resultat till p2
        return p1output;
    }

}




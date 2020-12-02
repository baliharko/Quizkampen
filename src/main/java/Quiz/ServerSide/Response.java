package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    public enum ResponseStatus {
        CHECKED_ANSWER, NEW_QUESTION, WAIT, NEXT_ROUND
    }

    private boolean isRightAnswer;
    private ResponseStatus status;
    private Question question;
    private int round;
    private boolean[] roundResults;

    public Response(ResponseStatus status, boolean isRightAnswer) {
        this.status = status;
        this.isRightAnswer = isRightAnswer;
    }

    public Response(ResponseStatus status, Question newQuestion) {
        this.status = status;
        this.question = newQuestion;
    }

    public Response(ResponseStatus status,int round, boolean[] roundResults) {
        this.status = status;
        this.round = round;
        this.roundResults = roundResults;
    }

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public boolean isRightAnswer() {
        return this.isRightAnswer;
    }

    public ResponseStatus getResponseStatus() {
        return this.status;
    }

    public Question getQuestion() {
        return this.question;
    }
}

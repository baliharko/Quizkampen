package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    public enum ResponseStatus {
        CHECKED_ANSWER, NEW_QUESTION
    }

    private boolean isRightAnswer;
    private ResponseStatus status;
    private Question question;

    public Response(ResponseStatus status, boolean isRightAnswer) {
        this.status = status;
        this.isRightAnswer = isRightAnswer;
    }

    public Response(ResponseStatus status, Question newQuestion) {
        this.status = status;
        this.question = newQuestion;
    }

    public Response(ResponseStatus state) {
        this.status = state;
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

package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    public enum ResponseStatus {
        CHECKED_ANSWER, NEW_QUESTION
    }

    private boolean isRightAnswer;
    private int answerButtonIndex;
    private ResponseStatus status;
    private Question question;

    public Response(ResponseStatus status, boolean isRightAnswer, int answerButtonIndex) {
        this.status = status;
        this.isRightAnswer = isRightAnswer;
        this.answerButtonIndex = answerButtonIndex;
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

    public int getAnswerButtonIndex() {
        return this.answerButtonIndex;
    }

    public ResponseStatus getResponseStatus() {
        return this.status;
    }
}

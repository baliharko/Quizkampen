package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    private boolean isRightAnswer;
    private int answerButtonIndex;

    public Response(boolean isRightAnswer, int answerButtonIndex) {
        this.isRightAnswer = isRightAnswer;
        this.answerButtonIndex = answerButtonIndex;
    }

    public boolean isRightAnswer() {
        return this.isRightAnswer;
    }

    public int getAnswerButtonIndex() {
        return this.answerButtonIndex;
    }
}

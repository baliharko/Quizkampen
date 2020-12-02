package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    public enum ResponseStatus {
        CHECKED_ANSWER, NEW_QUESTION, WAIT, NEXT_ROUND, RESULTS
    }

    private boolean isRightAnswer;
    private ResponseStatus status;
    private Question question;
    private int round;
    private boolean[] playerRoundResults;
    private boolean[] opponentRoundResults;

    public Response(ResponseStatus status, boolean isRightAnswer) {
        this.status = status;
        this.isRightAnswer = isRightAnswer;
    }

    public Response(ResponseStatus status, Question newQuestion) {
        this.status = status;
        this.question = newQuestion;
    }

    public Response(ResponseStatus status,int round, boolean[] playerRoundResults, boolean[] opponentRoundResults) {
        this.status = status;
        this.round = round;
        this.playerRoundResults = playerRoundResults;
        this.opponentRoundResults = opponentRoundResults;
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

    public int getRound() {
        return round;
    }

    public boolean[] getPlayerRoundResults() {
        return playerRoundResults;
    }

    public boolean[] getOpponentRoundResults() {
        return opponentRoundResults;
    }
}

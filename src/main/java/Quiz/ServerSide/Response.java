package Quiz.ServerSide;

import java.io.Serializable;

public class Response implements Serializable {

    public enum ResponseStatus {
        CHECKED_ANSWER, NEW_QUESTION, WAIT, RESULTS, SELECT_CATEGORY, NEW_ROUND_START
    }

    private boolean isRightAnswer;
    private ResponseStatus status;
    private Question question;
    private int round;
    private boolean[] playerRoundResults;
    private boolean[] opponentRoundResults;
    private int playerScore;
    private int opponentScore;
    private String message;

    public Response(ResponseStatus status, boolean isRightAnswer) {
        this.status = status;
        this.isRightAnswer = isRightAnswer;
    }

    public Response(ResponseStatus status, Question newQuestion) {
        this.status = status;
        this.question = newQuestion;
    }

    public Response(ResponseStatus status,int round, boolean[] playerRoundResults, boolean[] opponentRoundResults, int playerScore, int opponentScore) {
        this.status = status;
        this.round = round;
        this.playerRoundResults = playerRoundResults;
        this.opponentRoundResults = opponentRoundResults;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
    }

    public Response(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
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

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public String getMessage() {
        return message;
    }
}

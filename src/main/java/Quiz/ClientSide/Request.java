package Quiz.ClientSide;

import java.io.Serializable;

enum RequestStatus {
    SET_NAME, NEXT_QUESTION, ANSWER
}

public class Request implements Serializable {

    private RequestStatus status;
    private String answerText;
    private String playerName;

    public Request(RequestStatus status) {
        this.status = status;
    }

    public Request(RequestStatus status, String playerName) {
        this.status = status;
        this.playerName = playerName;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getPlayerName() {
        if (this.playerName != null)
            return this.playerName;
        else
            return "No playername is set.";
    }
}

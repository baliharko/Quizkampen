package Quiz.ClientSide;

import java.io.Serializable;

enum RequestStatus {
    SET_NAME, NEXT_QUESTION, ANSWER
}

public class Request implements Serializable {

    private RequestStatus status;
    private String answerText;
    private int answerButtonIndex;
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

    public void setAnswerButtonIndex(int answerButtonIndex) {
        this.answerButtonIndex = answerButtonIndex;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int getAnswerButtonIndex() {
        return answerButtonIndex;
    }

    public String getPlayerName() {
        if (this.playerName != null)
            return this.playerName;
        else
            return "No playername is set.";
    }
}

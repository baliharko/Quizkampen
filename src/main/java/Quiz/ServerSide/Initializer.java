package Quiz.ServerSide;

import java.io.Serializable;

public class Initializer implements Serializable {

    private String playerName;
    private String opponentName;
    private Question firstQuestion;

    public Initializer(String playerName, String opponentName, Question firstQuestion) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.firstQuestion = firstQuestion;
    }

    public synchronized String getOpponent() {
        return this.opponentName;
    }

    public synchronized Question getFirstQuestion() {
        return this.firstQuestion;
    }
}

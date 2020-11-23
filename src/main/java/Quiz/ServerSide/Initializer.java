package Quiz.ServerSide;

import java.io.Serializable;

public class Initializer implements Serializable {

    private String player1Name;
    private String opponentName;
    private Question firstQuestion;
    boolean bothConnected;

    public Initializer(String playerName, String opponentName, Question firstQuestion) {
        this.player1Name = playerName;
        this.opponentName = opponentName;
        this.firstQuestion = firstQuestion;
        this.bothConnected = true;
    }

    public Initializer() {
        this.bothConnected = false;
    }

    public String getOpponent() {
        return this.opponentName;
    }

    public synchronized Question getFirstQuestion() {
        return this.firstQuestion;
    }

    public boolean areBothConnected() {
        return this.bothConnected;
    }
}

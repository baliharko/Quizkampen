package Quiz.ServerSide;

import java.io.Serializable;

public class Initializer implements Serializable {

    private String player1Name;
    private String opponentName;
    boolean bothConnected;

    public Initializer(String playerName, String opponentName) {
        this.player1Name = playerName;
        this.opponentName = opponentName;
        this.bothConnected = true;
    }

    public Initializer() {
        this.bothConnected = false;
    }

    public String getOpponent() {
        return this.opponentName;
    }

    public boolean areBothConnected() {
        return this.bothConnected;
    }
}

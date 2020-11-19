package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:33
 * Project: Inlämning04
 * Copyright: MIT
 */

enum State {WAITING, READY};

public class ClientProtocol {

    //Lägga till funktion för namn?

    private State state = State.WAITING;

    public String ProcessInput(String in) {

        String theOutput = null;
        Object out = null;

        if (state == State.WAITING) {
            theOutput = "Waiting for another player";
            state = State.READY;

            //Istället för "ready to play" kan man ange namnet på playern som anslutit
//            theOutput = "Ready to play!";
            theOutput = "Playername joined the game!";
        } else if (state == State.READY) {
//            theOutput = "Ready to play!";
            theOutput = "Playername joined the game!";
        }
        return theOutput;
    }
}




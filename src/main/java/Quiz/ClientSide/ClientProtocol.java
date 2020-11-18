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

    private State state = State.WAITING;

    public String ProcessInput(String in) {

        String theOutput = null;
        Object out = null;

        if (state == State.WAITING) {
            theOutput = "Waiting for another player";
            state = State.READY;
            theOutput = "Ready to play!";
        } else if (state == State.READY) {
            theOutput = "Ready to play!";
        }
        return theOutput;
    }
}




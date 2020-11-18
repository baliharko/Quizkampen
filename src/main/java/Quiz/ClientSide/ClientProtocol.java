package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:33
 * Project: Inlämning04
 * Copyright: MIT
 */
public class ClientProtocol {

    private static final int WAITINGFOROPPONENT = 0;
    private static final int OPPONENTENTERED = 1;
    private static final int READYTOPLAY = 2;

    private int state = WAITINGFOROPPONENT;

    public Object ProcessInput(String input) {

        String theOutput = null;
        Object out = null;

        if (state == WAITINGFOROPPONENT) {
            theOutput = "Waiting for another player";
            state = OPPONENTENTERED;
        } else if (state == OPPONENTENTERED) {
                theOutput = "Another player entered";
            }
            state = READYTOPLAY;
        return out;
    }
}




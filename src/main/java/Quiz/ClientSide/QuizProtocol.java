package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    17:07
 * Project: Inlämning04
 * Copyright: MIT
 */
public class QuizProtocol {
   /* private static final int WAITING = 0;
    private static final int SENTRIDDLE = 1;
    private static final int SENTANSWER = 2;

    private static final int NUMRIDDLES = 3;

    private int state = WAITING;
    private int currentRiddle = 0;

    private String[] clues = {"Med vad börjar natten och slutar dagen?",
            "Vad är det som går och går men aldrig kommer till dörren?",
            "Vilket ord har 29 bokstäver?"};

    private String[] answers = {"n",
            "klockan",
            "alfabetet"};

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING || state == SENTANSWER) {
            theOutput = clues[currentRiddle];
            state = SENTRIDDLE;
        } else if (state == SENTRIDDLE) {
            if (theInput.equalsIgnoreCase(answers[currentRiddle])) {
                theOutput = "Rätt";
            } else {
                theOutput = "Fel";
            }
            state = SENTANSWER;
            currentRiddle++;
        }
        return theOutput;
    }
}

    */
}

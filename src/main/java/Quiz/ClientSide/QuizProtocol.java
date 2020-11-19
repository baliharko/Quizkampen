package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    17:07
 * Project: Inlämning04
 * Copyright: MIT
 */

enum QuizState {WAITINGFORTURN, SENTQUESTION, SELECTEDANSWER, TOTALAMOUNTOFQUESTIONS, ROUND};

public class QuizProtocol {

    private QuizState state = QuizState.WAITINGFORTURN;
    //private int currentQuestion = 0;

   /* public String quizProcessInput(String in){

    }

    */


   /* private static final int WAITINGINGFORTURN = 0;
    private static final int SENTQUESTION = 1;
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

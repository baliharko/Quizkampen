package Quiz.ClientSide;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:33
 * Project: Inlämning04
 * Copyright: MIT
 */
public class QuizProtocol {
    private static final int WAITING = 0;
    private static final int SENTRIDDLE = 1;
    private static final int SENTANSWER = 2;

    private int state = WAITING;
    private int currentRiddle = 0;

    private String[] clues = {"Vad är summan av 1+1?",
            "Vad är det som går och går men aldrig kommer till dörren",
            "Vad är motsatsen till natt?"};

    private String[] answers = {"2",
            "Klockan",
            "Dag",};


}

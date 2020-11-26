package Quiz.ClientSide;

import Quiz.ServerSide.Databas;
import Quiz.ServerSide.Initializer;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-25
 * Time:    15:38
 * Project: Inlämning04
 * Copyright: MIT
 */
public class QuizGame {
    //private final databas
    Databas db = new Databas();

    protected Initializer initializer;
    protected GameSetup gameSetup;

    public QuizGame(GameSetup gameSetup){
        this.gameSetup = gameSetup;
        //svar från databas?

    }
    public void run(){
        int player1Score=0;
        int player2Score=0;
        int gameRound=1;
//        int question=0;
        while(gameRound<=GameSetup.getRounds());

    }

}

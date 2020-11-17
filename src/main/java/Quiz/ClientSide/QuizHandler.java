package Quiz.ClientSide;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-17
 * Time:    15:29
 * Project: Inlämning04
 * Copyright: MIT
 */
public class QuizHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public QuizHandler (Socket socket) {
        this.socket = socket;


    }
}

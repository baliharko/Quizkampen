package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Thread thread;
    private Socket player1;
    private Socket player2;

    public ClientHandler(Socket player1, Socket player2) {
        this.thread = new Thread(this);
        this.player1 = player1;
        this.player2 = player2;
        this.thread.start();
        System.out.println("Two players connected!");
    }

    @Override
    public void run() {

        try (
                PrintWriter p1Out = new PrintWriter(new OutputStreamWriter(player1.getOutputStream()));
                PrintWriter p2Out = new PrintWriter(new OutputStreamWriter(player2.getOutputStream()));
                BufferedReader p1In = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                BufferedReader p2In = new BufferedReader(new InputStreamReader(player2.getInputStream()));
        ) {

            p1Out.println("Player1");
            p2Out.println("Player2");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

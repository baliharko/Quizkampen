package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Thread thread;
    private final Socket player1;
    private final Socket player2;


    public ClientHandler(Socket player1, Socket player2) {
        this.thread = new Thread(this);
        this.player1 = player1;
        this.player2 = player2;
        this.thread.start();
    }

    @Override
    public void run() {

        System.out.println("Two players connected!");

        try (
                PrintWriter p1Out = new PrintWriter(new OutputStreamWriter(player1.getOutputStream()), true);
                PrintWriter p2Out = new PrintWriter(new OutputStreamWriter(player2.getOutputStream()), true);
                BufferedReader p1In = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                BufferedReader p2In = new BufferedReader(new InputStreamReader(player2.getInputStream()));
        ) {

            ClientProtocol cP = new ClientProtocol();
            p1Out.println(cP.ProcessInput("1"));
            p2Out.println(cP.ProcessInput("1"));

           // p1Out.println("Welcome player 1!");
           // p2Out.println("Welcome player 2!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

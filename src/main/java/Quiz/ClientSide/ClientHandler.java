package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Thread thread;
    private final Socket player1;
    private final Socket player2;
    private String player1Name;
    private String player2Name;


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
                ObjectOutputStream p1Out = new ObjectOutputStream(player1.getOutputStream());
                ObjectOutputStream p2Out = new ObjectOutputStream(player2.getOutputStream());
                ObjectInputStream p1In = new ObjectInputStream(player1.getInputStream());
                ObjectInputStream p2In = new ObjectInputStream(player2.getInputStream());
        ) {



            ClientProtocol player1Protocol = new ClientProtocol();
            ClientProtocol player2Protocol = new ClientProtocol();

            p1Out.writeObject(player1Protocol.ProcessInput(null));
            System.out.println("init sent to player 1");
            p2Out.writeObject(player2Protocol.ProcessInput(null));
            System.out.println("init sent to player 2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    String playerName;
    private final Socket playerSocket;
    ClientProtocol protocol;
    ObjectOutputStream out;
    BufferedReader in;

    public ClientHandler(Socket player, ClientProtocol protocol, ObjectOutputStream out, BufferedReader in) {
        this.playerSocket = player;
        this.protocol = protocol;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (this.playerName == null) {
                this.playerName = in.readLine();
                this.protocol.setPlayer(this.playerName);
            }

            this.protocol.setPlayerOut(out);

            out.writeObject(this.protocol.ProcessInput("init"));

            String fromClient;
            while ((fromClient = in.readLine()) != null) {
                System.out.println("received answer " + fromClient);
                out.writeObject(this.protocol.ProcessInput(this.playerName + "," + fromClient));
            }

            this.playerSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

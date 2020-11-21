package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Thread thread;
    String playerName;
    private final Socket playerSocket;
    ClientProtocol protocol;

    public ClientHandler(Socket player, ClientProtocol protocol) {
        this.thread = new Thread(this);
        this.playerSocket = player;
        this.protocol = protocol;
        this.thread.start();
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(playerSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        ) {

            while (this.playerName == null) {
                this.playerName = in.readLine();
                this.protocol.setPlayer(this.playerName);
            }

            synchronized (this) {
                out.writeObject(this.protocol.ProcessInput("init"));
            }

            String fromClient;
            while ((fromClient = in.readLine()) != null) {
                out.writeObject(this.protocol.ProcessInput(fromClient));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

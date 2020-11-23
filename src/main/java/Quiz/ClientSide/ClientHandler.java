package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;

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

            this.protocol.setPlayerOut(out);

            out.writeObject(this.protocol.ProcessInput("init"));

            // Uppdaterar den väntande spelarens (player 1) interface när spelare 2 kopplats upp
//            if (this.protocol.areBothConnected() && this.playerName.equalsIgnoreCase(protocol.getPlayer1Name()))
//                out.writeObject(new Initializer(protocol.getPlayer1Name(), protocol.getPlayer2Name(), protocol.getCurrentQuestion()));


            String fromClient;
            while ((fromClient = in.readLine()) != null) {
                out.writeObject(this.protocol.ProcessInput(fromClient));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

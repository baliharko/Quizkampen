package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    String playerName;
    private final Socket playerSocket;
    ClientProtocol protocol;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientHandler(Socket player, ClientProtocol protocol, ObjectOutputStream out, ObjectInputStream in) {
        this.playerSocket = player;
        this.protocol = protocol;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
//            while (this.playerName == null) {
//                this.playerName = (String)in.readObject();
//                this.protocol.setPlayer(this.playerName);
//            }

//            this.protocol.setPlayerOut(out);

//            out.writeObject(this.protocol.ProcessInput("init"));

            Object fromClient;
            while ((fromClient = in.readObject()) != null) {

                if (fromClient instanceof Request) {
                    if (((Request)fromClient).getStatus() == RequestStatus.SET_NAME) {
                        this.playerName = ((Request)fromClient).getPlayerName();
                        this.protocol.setPlayer(this.playerName);
                        this.protocol.setPlayerOut(out);
                        out.writeObject(this.protocol.ProcessInput("init"));
                    }
                    else if (((Request)fromClient).getStatus() == RequestStatus.ANSWER) {
                        System.out.println("received answer " + fromClient);
                        out.writeObject(this.protocol.ProcessInput(fromClient));
                    }
                }
            }
            this.playerSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

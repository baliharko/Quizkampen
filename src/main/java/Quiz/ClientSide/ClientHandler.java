package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private int playerId;
    String playerName;
    private final Socket playerSocket;
    ClientProtocol protocol;
    ObjectInputStream in;

    public ClientHandler(Socket player, ClientProtocol protocol, ObjectInputStream in, int playerId) {
        this.playerSocket = player;
        this.protocol = protocol;
        this.in = in;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try {
            Object fromClient;
            while ((fromClient = in.readObject()) != null) {
                if (fromClient instanceof Request) {
                    if (((Request) fromClient).getStatus() == RequestStatus.SET_NAME) {
                        this.playerName = ((Request) fromClient).getPlayerName();
                        this.protocol.setPlayer(this.playerName, this.playerId);
                        this.protocol.processInput(new Request(RequestStatus.INIT), this.playerId);
                    } else {
                        System.out.println("received answer " + fromClient);
                        this.protocol.processInput(fromClient, this.playerId);
                    }
                }
            }
            this.playerSocket.close();
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

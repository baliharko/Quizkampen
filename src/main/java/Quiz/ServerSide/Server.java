package Quiz.ServerSide;

import Quiz.ClientSide.ClientHandler;
import Quiz.ClientSide.ClientProtocol;
import Quiz.ClientSide.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        System.out.println("Server started");

        try {
            final ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Socket player1 = null;
            Socket player2 = null;

            while (true) {
                if (player1 == null) {
                    player1 = serverSocket.accept();
                    System.out.println("Player 1 connected");
                }
                else {
                    player2 = serverSocket.accept();
                    System.out.println("Player 2 connected");
                }

                if (player1 != null && player2 != null) {
                    ClientProtocol protocol = new ClientProtocol();
                    new ClientHandler(player1, protocol);
                    new ClientHandler(player2, protocol);
                    player1 = null;
                    player2 = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

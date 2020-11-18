package Quiz.ServerSide;

import Quiz.ClientSide.ClientHandler;
import Quiz.ClientSide.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Socket player1 = null;
            Socket player2 = null;

            while (true) {
                if ((player1 == null || player2 == null)) {
                    if (player1 == null)
                        player1 = serverSocket.accept();

                    player2 = serverSocket.accept();
                }

                new ClientHandler(player1, player2);
                player1 = null;
                player2 = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

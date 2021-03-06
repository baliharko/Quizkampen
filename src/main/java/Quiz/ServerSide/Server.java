package Quiz.ServerSide;

import Quiz.ClientSide.ClientHandler;
import Quiz.ClientSide.ClientProtocol;
import Quiz.ClientSide.Constants;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    protected static final Database DATABASE = new Database();

    public static void main(String[] args) {

        System.out.println("Server started");

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    System.out.println("SERVER - Closed serverSocket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true) {

                ClientProtocol protocol = new ClientProtocol(DATABASE);
                Socket player1 = serverSocket.accept();
                System.out.println("SERVER - player1 connected");
                Socket player2 = serverSocket.accept();
                System.out.println("SERVER - player2 connected");

                // Två ClientHandlers(spelare) delar på ett protokoll
                ObjectOutputStream p1Out = new ObjectOutputStream(player1.getOutputStream());
                ObjectInputStream p1In = new ObjectInputStream(player1.getInputStream());
                ClientHandler p1Handler = new ClientHandler(player1, protocol, p1In, 1);

                ObjectOutputStream p2Out = new ObjectOutputStream(player2.getOutputStream());
                ObjectInputStream p2In = new ObjectInputStream(player2.getInputStream());
                ClientHandler p2Handler = new ClientHandler(player2, protocol, p2In, 2);

                protocol.setPlayerOuts(p1Out, p2Out);
                Thread p1 = new Thread(p1Handler);
                Thread p2 = new Thread(p2Handler);

                p1.start();
                Thread.sleep(100);
                p2.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

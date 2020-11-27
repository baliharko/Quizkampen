package Quiz.ServerSide;

import Quiz.ClientSide.ClientHandler;
import Quiz.ClientSide.ClientProtocol;
import Quiz.ClientSide.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        Databas databas = new Databas();

        try {
            new ServerSocket(Constants.SERVER_PORT).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server started");

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {

            while (true) {

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        serverSocket.close();
                        System.out.println("SERVER - Closed serverSocket");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));

                ClientProtocol protocol = new ClientProtocol(databas);
                Socket player1 = serverSocket.accept();
                System.out.println("SERVER - player1 connected");
                Socket player2 = serverSocket.accept();
                System.out.println("SERVER - player2 connected");

                if (player1 != null && player2 != null) {

                    // Två ClientHandlers(spelare) delar på ett protokoll
                    ObjectOutputStream p1Out = new ObjectOutputStream(player1.getOutputStream());
                    BufferedReader p1In = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                    ClientHandler p1Handler = new ClientHandler(player1, protocol, p1Out, p1In);
                    Thread p1 = new Thread(p1Handler);
                    p1.start();

                    ObjectOutputStream p2Out = new ObjectOutputStream(player2.getOutputStream());
                    BufferedReader p2In = new BufferedReader(new InputStreamReader(player2.getInputStream()));
                    ClientHandler p2Handler = new ClientHandler(player2, protocol, p2Out, p2In);
                    Thread p2 = new Thread(p2Handler);
                    p2.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

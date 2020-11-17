package Quiz.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;

/*For att skapa mer player*/
public class ServerListener {
    public static void main(String[] args) throws IOException {
        ServerSocket listenerSocket = new ServerSocket(Server.SERVER_PORT);
        Server playerOne = new Server(listenerSocket.accept());
        System.out.println("Player one joined");
        System.out.println("Waiting for player 2");
        Server playerTwo = new Server(listenerSocket.accept());
        System.out.println("Player two joined");
    }
}

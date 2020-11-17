package Quiz.ServerSide;

import java.io.*;
import java.net.*;

public class Server extends Thread {
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 5050;
    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected Socket socket;
    protected Server secondPlayer;
    protected String playerName;

    public Server(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

}

package Quiz.ClientSide;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;

    public Client() {
        this.thread = new Thread(this);
        this.thread.start();
        System.out.println("Client started");
    }

    @Override
    public void run() {

        try (
                Socket socketToServer = new Socket("127.0.0.1", 50000);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketToServer.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
        ) {

            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

package Quiz.ClientSide;

import javafx.scene.Parent;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;
    private boolean isConnected;
    private GameInterfaceController controller;

    public Client(GameInterfaceController controller) {
        this.thread = new Thread(this);
        this.controller = controller;
        this.thread.start();
        System.out.println("Client started");
    }

    @Override
    public void run() {

        try (
                Socket socketToServer = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketToServer.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
        ) {

            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                this.controller.setConnectionStatus(fromServer);
                if (fromServer.equalsIgnoreCase("1")) {
                    this.isConnected = true;
                    this.controller.connectionStatus.setStyle("-fx-fill: green");
                }
                else
                    System.out.println(fromServer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}

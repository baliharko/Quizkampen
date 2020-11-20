package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;
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
                ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream());
        ) {

            Object fromServer;

            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof Initializer) {
                    this.controller.setConnectionStatus(((Initializer) fromServer).getOpponent());
                    this.controller.connectionStatus.setStyle("-fx-fill: green");
                    this.controller.setQuestionText(((Initializer) fromServer).getFirstQuestion().getQuestion());
//                    this.controller.setToggleButtons(((Initializer) fromServer).getFirstQuestion().getOptions());
                }
//                else
//                    System.out.println(fromServer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

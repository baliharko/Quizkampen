package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    Thread thread;
    private GameInterfaceController controller;

    public Client(GameInterfaceController controller) {
        this.controller = controller;
        this.thread = new Thread(this);
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
                    System.out.println("received initializer from server");
                    Object temp = fromServer;
                    Platform.runLater(() -> {
                        this.controller.setConnectionStatus(((Initializer) temp).getOpponent() + " joined the game!");
                        this.controller.connectionStatus.setStyle("-fx-fill: green");
                        this.controller.setQuestionText(((Initializer) temp).getFirstQuestion().getQuestion());
                        this.controller.setToggleButtonsText(((Initializer) temp).getFirstQuestion().getOptions());
                    });
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

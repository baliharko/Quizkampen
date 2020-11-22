package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import javafx.application.Platform;
import javafx.scene.control.ToggleButton;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;
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
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketToServer.getOutputStream()), true);
                ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream());
        ) {

            // Skickar texten på markerad knapp till ClientHandler och hanteras av ClientProtocol
            controller.acceptButton.setOnAction(event -> {
                out.println(controller.getSelectedToggleText());
            });

            Object fromServer;
            while ((fromServer = in.readObject()) != null) {

                // Tar emot Initializer-objekt när två spelare kopplats upp, innehåller första frågan.
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
                else if (fromServer instanceof String) {
                    String temp = (String)fromServer;
                    Platform.runLater(() -> {
                        this.controller.setQuestionText(temp);
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

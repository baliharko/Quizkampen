package Quiz.ClientSide;

import Quiz.ClientSide.controllers.QuestionInterfaceController;
import Quiz.ServerSide.Initializer;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;
    private QuestionInterfaceController controller;
    public String playerName;

    public Client(QuestionInterfaceController controller, String playerName) {
        this.controller = controller;
        this.playerName = playerName;
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

            // Skickar texten på markerad knapp i frågerutan till ClientHandler och hanteras av ClientProtocol
            controller.acceptButton.setOnAction(event -> {
                out.println(controller.getSelectedToggleText());
            });

            out.println(this.playerName);

            Object fromServer;
            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof Initializer) { // Tar emot Initializer-objekt när två spelare kopplats upp, innehåller första frågan.
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

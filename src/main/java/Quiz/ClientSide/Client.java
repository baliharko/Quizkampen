package Quiz.ClientSide;

import Quiz.ClientSide.controllers.QuestionInterfaceController;
import Quiz.ClientSide.controllers.SelectCategoryInterfaceController;
import Quiz.ServerSide.Initializer;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client implements Runnable {


    private Thread thread;
    private QuestionInterfaceController questionInterfaceController;
    public SelectCategoryInterfaceController categoryInterfaceController;
    public String playerName;

    public Client(QuestionInterfaceController controller, String playerName) {
        this.questionInterfaceController = controller;
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
            questionInterfaceController.acceptButton.setOnAction(event -> {
                System.out.println("sent " + Objects.requireNonNull(questionInterfaceController.getSelectedToggleText()));
                out.println(Objects.requireNonNull(questionInterfaceController.getSelectedToggleText()));
            });

            out.println(this.playerName);

            Object fromServer;
            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof Initializer) { // Tar emot Initializer-objekt när två spelare kopplats upp, innehåller första frågan.
                    System.out.println("received initializer from server");
                    Object temp = fromServer;
                    Platform.runLater(() -> {
                        if (((Initializer) temp).areBothConnected()) {
                            System.out.println("Received initializer opponent = " + ((Initializer) temp).getOpponent());
                            this.questionInterfaceController.setConnectionStatus(((Initializer) temp).getOpponent() + " joined the game!");
                            this.questionInterfaceController.connectionStatus.setStyle("-fx-fill: green");
                            this.questionInterfaceController.setQuestionText(((Initializer) temp).getFirstQuestion().getQuestion());
                            this.questionInterfaceController.setToggleButtonsText(((Initializer) temp).getFirstQuestion().getOptions());
                        }
                    });
                } else if (fromServer instanceof String) {
                    String temp = (String) fromServer;
                    Platform.runLater(() -> {
                        this.questionInterfaceController.setQuestionText(temp);
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

package Quiz.ClientSide;

import Quiz.ClientSide.controllers.QuestionInterfaceController;
import Quiz.ClientSide.controllers.SelectCategoryInterfaceController;
import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Response;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client implements Runnable {

    private Thread thread;
    public GameSetup game;
    public String playerName;
    private ObjectOutputStream out;

    public Client(GameSetup game, String playerName) {
        this.game = game;
        this.playerName = playerName;
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
            // Ger tillgång till printwriter utanför run - metoden
            this.setClientOutStream(out);

//            out.println(this.playerName);
            out.writeObject(new Request(RequestStatus.SET_NAME, this.playerName));

            Object fromServer;
            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof Initializer) { // Tar emot Initializer-objekt när två spelare kopplats upp, innehåller första frågan.
                    System.out.println("received initializer from server");
                    Object temp = fromServer;
                    Platform.runLater(() -> {
                        if (((Initializer) temp).areBothConnected()) {
                            System.out.println("Received initializer opponent = " + ((Initializer) temp).getOpponent());
                            game.getQuestionInterfaceController().setConnectionStatus(((Initializer) temp).getOpponent() + " joined the game!");
                            game.getQuestionInterfaceController().connectionStatus.setStyle("-fx-fill: green");
                            game.getQuestionInterfaceController().setQuestionText(((Initializer) temp).getFirstQuestion().getQuestion());
                            game.getQuestionInterfaceController().setToggleButtonsText(((Initializer) temp).getFirstQuestion().getOptions());
                        }
                    });
                } else if (fromServer instanceof Response) {
                    Response temp = (Response) fromServer;
                    Platform.runLater(() -> {
//                        game.getQuestionInterfaceController().setQuestionText("" + temp.isRightAnswer()); // test
                        // TODO - Sätt färg på knappen här
                        game.getQuestionInterfaceController().setToggleButtonColor(temp.isRightAnswer(), temp.getAnswerButtonIndex());

                    });
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClientOutStream(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectOutputStream getClientOutStream() {
        return this.out;
    }

}

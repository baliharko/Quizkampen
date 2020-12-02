package Quiz.ClientSide;

import Quiz.ServerSide.Initializer;
import Quiz.ServerSide.Response;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;
    public GameSetup game;
    public String playerName;
    public String opponentName;
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
                            this.game.getGameInterface().primaryStage.setScene(this.game.getGameInterface().questionScene);
                            this.opponentName = ((Initializer)temp).getOpponent();
                            System.out.println("Received initializer opponent = " + this.opponentName);
                            game.getQuestionInterfaceController().setConnectionStatus(this.opponentName + " joined the game!");
                            game.getQuestionInterfaceController().connectionStatus.setStyle("-fx-fill: green");
                            game.getQuestionInterfaceController().setQuestionText(((Initializer) temp).getFirstQuestion().getQuestion());
                            game.getQuestionInterfaceController().setToggleButtonsText(((Initializer) temp).getFirstQuestion().getOptions());

                            game.getResultFromRoundController().p1Name.setText(this.playerName);
                            game.getResultFromRoundController().p2Name.setText(this.opponentName);
                        }
                    });
                } else if (fromServer instanceof Response) {
                    Response temp = (Response) fromServer;

                    if (temp.getResponseStatus() == Response.ResponseStatus.CHECKED_ANSWER) {
                        Platform.runLater(() -> {
                            game.getQuestionInterfaceController().setToggleButtonColor(temp.isRightAnswer());
                            game.getQuestionInterfaceController().setAcceptButtonText("Nästa fråga");
                        });
                    }
                    else if (temp.getResponseStatus() == Response.ResponseStatus.NEW_QUESTION) {
                        Platform.runLater(() -> {
                            game.getQuestionInterfaceController().setAcceptButtonText("Svara");
                            game.getQuestionInterfaceController().setQuestionText(temp.getQuestion().getQuestion());
                            game.getQuestionInterfaceController().refreshButtons();
                            game.getQuestionInterfaceController().setToggleButtonsText(temp.getQuestion().getOptions());
                        });
                    }
                    else if (temp.getResponseStatus() == Response.ResponseStatus.WAIT) {
                        Platform.runLater(() -> {
                            game.getWaitController().waitPromptText.setText("Vänta medan spelare 2 svarar klart på frågorna...");
                            game.getGameInterface().primaryStage.setScene(this.game.getGameInterface().waitScene);
                        });
                    } else if (temp.getResponseStatus() == Response.ResponseStatus.RESULTS) {
                        Platform.runLater(() -> {
                            game.getResultFromRoundController().updateResults(temp.getPlayerRoundResults(), temp.getOpponentRoundResults(), temp.getRound());
                            game.getResultFromRoundController().currentScore.setText(temp.getPlayerScore() + " - " + temp.getOpponentScore());
                            game.getGameInterface().primaryStage.setScene(game.getGameInterface().resultRoundScene);
                        });
                    } else if (temp.getResponseStatus() == Response.ResponseStatus.NEXT_ROUND) {
                        Platform.runLater(() -> {
                            game.getGameInterface().primaryStage.setScene(game.getGameInterface().categoryScene);
                        });
                    }
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

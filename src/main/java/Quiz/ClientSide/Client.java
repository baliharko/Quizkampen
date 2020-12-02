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
    private boolean isStartup;

    public Client(GameSetup game, String playerName) {
        this.game = game;
        this.playerName = playerName;
        this.thread = new Thread(this);
        this.isStartup = true;
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

            out.writeObject(new Request(RequestStatus.SET_NAME, this.playerName));

            Object fromServer;
            while ((fromServer = in.readObject()) != null) {
                if (fromServer instanceof Initializer) { // Tar emot Initializer-objekt när två spelare kopplats upp. Sätter alla namn.
                    Object temp = fromServer;
                    Platform.runLater(() -> {
                        if (((Initializer) temp).areBothConnected()) {
                            this.opponentName = ((Initializer)temp).getOpponent();
                            System.out.println("Received initializer opponent = " + this.opponentName);
                            game.getQuestionInterfaceController().setConnectionStatus("Du spelar mot: " + this.opponentName);
                            game.getQuestionInterfaceController().connectionStatus.setStyle("-fx-fill: green");

                            game.getResultFromRoundController().p1Name.setText(this.playerName);
                            game.getResultFromRoundController().p2Name.setText(this.opponentName);
                        }
                    });
                } else if (fromServer instanceof Response) {
                    Response temp = (Response) fromServer;

                    // Om servern har skickat tillbaka true eller false (svar rätt eller fel)
                    if (temp.getResponseStatus() == Response.ResponseStatus.CHECKED_ANSWER) {
                        Platform.runLater(() -> {
                            game.getQuestionInterfaceController().setToggleButtonColor(temp.isRightAnswer());
                            game.getQuestionInterfaceController().setAcceptButtonText("Nästa fråga");
                        });
                    }

                    // Servern skickar en ny fråga
                    else if (temp.getResponseStatus() == Response.ResponseStatus.NEW_QUESTION) {
                        Platform.runLater(() -> {
                            game.getQuestionInterfaceController().setAcceptButtonText("Svara");
                            game.getQuestionInterfaceController().setQuestionText(temp.getQuestion().getQuestion());
                            game.getQuestionInterfaceController().refreshButtons();
                            game.getQuestionInterfaceController().setToggleButtonsText(temp.getQuestion().getOptions());
                        });
                    }

                    // Vänta
                    else if (temp.getResponseStatus() == Response.ResponseStatus.WAIT) {
                        Platform.runLater(() -> {
                            if (temp.getMessage() != null)
                                game.getWaitController().waitPromptText.setText(temp.getMessage());

//                            game.getWaitController().waitPromptText.setText("Vänta medan spelare 2 svarar klart på frågorna...");
                            game.getGameInterface().primaryStage.setScene(this.game.getGameInterface().waitScene);
                        });


                    }
                    // Visa resultatfönstret
                    else if (temp.getResponseStatus() == Response.ResponseStatus.RESULTS) {
                        Platform.runLater(() -> {
                            game.getResultFromRoundController().updateResults(temp.getPlayerRoundResults(), temp.getOpponentRoundResults(), temp.getRound());
                            game.getResultFromRoundController().currentScore.setText(temp.getPlayerScore() + " - " + temp.getOpponentScore());
                            game.getGameInterface().primaryStage.setScene(game.getGameInterface().resultRoundScene);
                        });
                    }

                    // Starta nästa runda
                    else if (temp.getResponseStatus() == Response.ResponseStatus.NEW_ROUND_START) {
                        Platform.runLater(() -> {
                            game.getQuestionInterfaceController().setAcceptButtonText("Svara");
                            game.getQuestionInterfaceController().setQuestionText(temp.getQuestion().getQuestion());

                            if (!isStartup)
                                game.getQuestionInterfaceController().refreshButtons();

                            game.getQuestionInterfaceController().setToggleButtonsText(temp.getQuestion().getOptions());
                            game.getGameInterface().primaryStage.setScene(game.getGameInterface().questionScene);
                            this.isStartup = false;
                        });
                    }

                    // Välj kategori
                    else if (temp.getResponseStatus() == Response.ResponseStatus.SELECT_CATEGORY) {
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

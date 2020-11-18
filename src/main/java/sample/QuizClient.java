package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class QuizClient {
    private static String serverIP;
    private static int serverPort;
    private int score;
    public Question question;


    public static void main(String[] args)  {
        QuizClient qc = new QuizClient();
        qc.start();
    }

    public QuizClient() {
        this.serverIP = "localhost";
        this.serverPort = 5000;
        this.score = 0;
        this.question = null;
    }

    private void start() {
        try(Socket socket = new Socket(QuizClient.serverIP, QuizClient.serverPort);
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {

            Question q = (Question) in.readObject();
            System.out.println(q.getQuestion());

            out.write("2");
            out.flush();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("The connection was closed!");
            e.printStackTrace();
        }
    }

    private boolean checkAnswer() {
        return false;
    }

}

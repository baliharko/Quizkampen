package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    private static final int PORT = 5000;
    private ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
        QuizServer quizServer = new QuizServer();
        quizServer.startServer();
    }

    private void startServer() throws IOException{
        System.out.println("Starting server!");
        this.serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            QuizThread qt = new QuizThread(clientSocket);
            qt.run();
        }
    }

    private class QuizThread implements Runnable {
        private Socket clientSocket;

        private QuizThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Client connected on port " + clientSocket.getPort());

            try(ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                out.writeObject(new Question("1+1", "2"));
                out.flush();

                String s = br.readLine();
                System.out.println("client answers " + s);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

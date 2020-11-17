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
        private InputStreamReader inputStream;
        private ObjectOutputStream outputStream;

        private QuizThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.inputStream = null;
            this.outputStream = null;
        }

        @Override
        public void run() {
            System.out.println("Client connected on port " + clientSocket.getPort());
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new InputStreamReader(clientSocket.getInputStream());

                outputStream.writeObject(new Question("1+1", "2"));
                outputStream.flush();

                BufferedReader br = new BufferedReader(inputStream);
                String s = br.readLine();
                System.out.println("client answers " + s);

                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

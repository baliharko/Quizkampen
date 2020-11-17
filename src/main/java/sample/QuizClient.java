package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class QuizClient {

    public static void main(String[] args) throws ClassNotFoundException, IOException  {
        Socket socket = new Socket("localhost", 5000);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());

        Question q = (Question) in.readObject();
        System.out.println(q.getQuestion());
        out.write("2");
        out.flush();

        in.close();
        out.close();
        socket.close();

    }
}

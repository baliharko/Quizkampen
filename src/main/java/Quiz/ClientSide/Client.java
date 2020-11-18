package Quiz.ClientSide;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    public Client() throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setSize(210, 100);
        JTextArea waiting = new JTextArea("waiting for player 2");
        jFrame.setLocationRelativeTo(null);
        jFrame.add(waiting);

        String name = JOptionPane.showInputDialog("name:");
        if (name == null) System.exit(0);
        jFrame.setTitle(name);

        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        Socket newSocket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
        ObjectOutputStream output = new ObjectOutputStream(newSocket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(newSocket.getInputStream());
        output.writeObject(name);
        jFrame.setTitle(name);
        start();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}

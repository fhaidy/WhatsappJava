package sockets;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        Socket socket;
        PrintStream output;
        Scanner in;
        Scanner out;
        try {
            socket = new Socket("localhost", 9876);
        } catch (Exception e) {
            System.out.println("Failed to connect to server \n" + "Details: " + e.getMessage());
            return;
        }
        try {
            in = new Scanner(socket.getInputStream());
            String entrada = in.nextLine();
            System.out.println("Message received: " + entrada);

            output = new PrintStream(socket.getOutputStream());
            out = new Scanner(System.in);
            String text = out.nextLine();
            output.println(text);
        } catch (Exception e) {
            System.out.println("Failed to send message  \n" + "Details: " + e.getMessage());
            return;
        }

        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("Failed to end connection \n" + "Details: " + e.getMessage());
        }

    }

}
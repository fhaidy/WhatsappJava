import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient
{
    public static void main(String[] args)
            throws IOException
    {

        Scanner consoleIn = new Scanner(System.in);
        System.out.print("Address: ");
        String address = consoleIn.nextLine();

        System.out.print("Port: ");
        int port = Integer.parseInt(consoleIn.nextLine());

        System.out.println("Connecting");
        Socket socket = new Socket(address, port);
        System.out.println("Connected to port " + port + " at " + address);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Input and output streams established");

        boolean done = false;
        do
        {
            System.out.print("Message: ");

            String message = consoleIn.nextLine();

            if (message.length() == 0)
            {
                System.out.println("Ending conversation");
                socketOut.println("");
                done = true;
            }

            if (!done)
            {
                socketOut.println(message);

                String response = socketIn.nextLine();
                if (response.length() != 0)
                    System.out.println("Response: " + response);
                else
                {
                    System.out.println("Server ended conversation");
                    done = true;
                }
            }
        } while (!done);

        System.out.println("Closing connection");
        socket.close();
    }
}
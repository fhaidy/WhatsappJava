package sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args) {

        ServerSocket server = createServerSocket();
        if (server.isClosed()) return;

        Socket client = establishConnection(server);
        if (client.isClosed()) return;

        while (client.isConnected()) {
            try {
                Scanner input = new Scanner(client.getInputStream());
                if (input.hasNext()){
                    printInputToServer(input);
                    printFeedbackToClient(client);
                }else{
                    closeConnection(server, client);
                }
            } catch (Exception e) {
                System.out.println("Error communicating \n"
                        + "Log: " + e.getMessage());
                return;
            }
        }
    }

    private static void printInputToServer(Scanner in) {
        String input = in.nextLine();
        System.out.println("Client: " + input);
    }

    private static void printFeedbackToClient(Socket client) throws IOException {
        PrintStream out;
        out = new PrintStream(client.getOutputStream());
        out.println("Message Received");
    }

    private static Socket establishConnection(ServerSocket server) {
        Socket client;
        try {
            System.out.println("Waiting...");
            client = server.accept();
            System.out.println("Conexão estabelecida \n"  + client.getInetAddress().getHostAddress());
        } catch (Exception e) {
            System.out.println("Error connecting  \n" + "Log: " + e.getMessage());
            return null;
        }
        return client;
    }

    private static ServerSocket createServerSocket() {
        ServerSocket server;
        try {
            server = new ServerSocket(9876);
        } catch (Exception e) {
            System.out.println("Erro ao criar servidor na porta 9876 \n"
                    + "Log: " + e.getMessage());
            return null;
        }
        return server;
    }

    private static void closeConnection(ServerSocket server, Socket cliente) {
        try {
            server.close();
            cliente.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Error finishing connection\n"
                    + "Log: " + e.getMessage());
        }
    }
}
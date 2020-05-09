package sockets;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args) {

        ServerSocket server;
        Socket cliente;
        Scanner in;
        PrintStream saida;
        Boolean start = true;

        try {
            server = new ServerSocket(9876);
        } catch (Exception e) {
            System.out.println("Erro ao criar servidor na porta 9876 \n"
                    + "Log: " + e.getMessage());
            return;
        }
        while (start) {
            try {
                System.out.println("Aguardando conexão...");
                cliente = server.accept();
                System.out.println("Conexão estabelecida \n"
                        + cliente.getInetAddress().getHostAddress());
            } catch (Exception e) {
                System.out.println("Erro ao conectar com cliente :C  \n"
                        + "Log: " + e.getMessage());
                return;
            }
            try {
                in = new Scanner(cliente.getInputStream());
                String entrada;
                entrada = in.nextLine();
                System.out.println("Client: " + entrada);
                saida = new PrintStream(cliente.getOutputStream());
                saida.println("Server message");
            } catch (Exception e) {
                System.out.println("Erro de comunicação :C \n"
                        + "Log: " + e.getMessage());
                return;
            }
            try {
                cliente.close();
                if (cliente.getInetAddress().getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                    System.out.println("Eduardo se conectou :P");
                    server.close();
                    cliente.close();
                    start = false;
                }
            } catch (Exception e) {
                System.out.println("Erro ao encerrar conexão :C \n"
                        + "Log: " + e.getMessage());
            }
        }
    }
}
package socket.java;

import custom.logger.ConsoleLogger;
import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private final static ConsoleLogger log = new ConsoleLogger();

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Server.class);
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader inputtedInformation;
        final PrintWriter outputtedInformation;
        final Scanner scanner;

        try {
            serverSocket = new ServerSocket(5000);
            clientSocket = serverSocket.accept();
            outputtedInformation = new PrintWriter(clientSocket.getOutputStream(), true);
            inputtedInformation = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);

            System.out.println("Enter your name: ");
            final String sendName = scanner.nextLine();
            outputtedInformation.println(sendName);

            final String receivedName = inputtedInformation.readLine();
            System.out.println("Chatting started");

            Thread sender = new Thread(() -> {
                String messageForClient;
                while (scanner.hasNext()) {
                    messageForClient = scanner.nextLine();
                    outputtedInformation.println(messageForClient);
                }

            });

            sender.start();

            Thread receiver = new Thread(() -> {

                try {
                    String messageReceivedFromClient = inputtedInformation.readLine();
                    while (messageReceivedFromClient != null) {
                        System.out.println(receivedName + ": " + messageReceivedFromClient);
                        messageReceivedFromClient = inputtedInformation.readLine();
                    }
                    serverSocket.close();
                    clientSocket.close();
                    outputtedInformation.close();
                    inputtedInformation.close();
                    scanner.close();
                    System.out.println(receivedName + " disconnected");
                } catch (Exception exception) {
                    logger.error("Error happened in receiving from " + receivedName, exception);
                    log.ERROR("Error happened in receiving from ");
                }

            });

            receiver.start();

        } catch (Exception exception) {
            logger.error("Error happened in IO files ", exception);
            log.ERROR("Error happened in IO files ");
        }
    }
}

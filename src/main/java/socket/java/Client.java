package socket.java;

import custom.logger.AbstractLogger;
import custom.logger.ConsoleLogger;
import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private static final ConsoleLogger log = new ConsoleLogger();

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Client.class);
        final Socket clientSocket;
        final BufferedReader inputtedInformation;
        final PrintWriter outputtedInformation;
        final Scanner scanner;

        

        try {
            clientSocket = new Socket("127.0.0.1", 5000);
            outputtedInformation = new PrintWriter(clientSocket.getOutputStream(), true);
            inputtedInformation = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            System.out.println("Enter your name:  ");
            final String sendName = scanner.nextLine();
            outputtedInformation.println(sendName);

            final String receivedName = inputtedInformation.readLine();
            System.out.println("Chatting started");

            Thread sender = new Thread(() -> {
                String message;
                while (scanner.hasNext()) {
                    message = scanner.nextLine();
                    outputtedInformation.println(message);
                }
            });

            sender.start();

            Thread receiver = new Thread(() -> {
                try {
                    String message = inputtedInformation.readLine();
                    while (message != null) {
                        System.out.println(receivedName + ": " + message);
                        message = inputtedInformation.readLine();
                    }
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

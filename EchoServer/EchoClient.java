import java.net.*;
import java.io.*;
import java.util.*;

/**
 * EchoClient
 */
public class EchoClient {

    public static void main(String[] args) {
        // Initialize host and port variables
        String host = "127.0.0.1";
        int port = 32000;
        // Create socket
        try (Socket socket = new Socket(host, port)) {
            // prints and reads from the client  
            PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Reads the inputted data
            Scanner scanner = new Scanner(System.in);
            String line = null;
            // While client is still connected, read and display back what they send
            while (!"quit".equalsIgnoreCase(line)) {
                // moves to the next line
                line = scanner.nextLine();
                // prints that line to the client side
                toClient.println(line);
                toClient.flush();
                String tempoString = fromClient.readLine();
                // Client disconnects from server
                if("quit".equalsIgnoreCase(tempoString)){
                    // if quit exists then disconnect from the server
                    System.out.println("Disconected from server");
                    break;
                }
                // print out the server reply
                System.out.println("Server replied: " + tempoString);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

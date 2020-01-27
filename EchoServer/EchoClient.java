import java.net.*;
import java.io.*;
import java.util.*;

/**
 * EchoClient
 */
public class EchoClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 32000;
        try (Socket socket = new Socket(host, port)) {
            PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String line = null;
            String splitline; //read individual characters in the stream
            // While client is still connected, read and display back what they send
            while (!"quit".equalsIgnoreCase(line)) {
                line = scanner.nextLine();
                toClient.println(line);
                toClient.flush();
                String tempoString = fromClient.readLine();
                if("quit".equalsIgnoreCase(tempoString)){
                    System.out.println("Disconected from server");
                    break;
                }
                System.out.println("Server replied: " + tempoString);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

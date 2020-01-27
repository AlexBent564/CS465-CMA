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
            String[] splitline; //read individual characters in the stream
            // While client is still connected, read and display back what they send
            while (!"quit".equalsIgnoreCase(line)) {
                line = scanner.nextLine();
                toClient.println(line);
                splitline = line.split("");
                //System.out.println(splitline[0]); //test
                int len = line.length();
                for (int i = 0; i < len; i++) {
                  // checks whether the character is not a letter
                  // if it is not a letter ,it will return false
                if ((Character.isLetter(line.charAt(i)) == true)) {
                    System.out.println(line.charAt(i));
                }
              }
                toClient.flush();
                System.out.println("hello");
                System.out.println("Server replied " + fromClient.readLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

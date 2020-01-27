import java.net.*;
import java.io.*;

/**
 * EchoServer
 */
public class EchoServer {
    public static void  main(final String arg[]) throws IOException
	{
        ServerSocket server = null;
        try {
            // Starts the server up
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            System.out.println("Server listening...");
            // Server can accept new connections when they appear
            while (true) {
                final Socket client = server.accept();
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                final EchoThread clientSock = new EchoThread(client);
 
                // Background thread will handle each client separately
                new Thread(clientSock).start();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // EchoThread handles talking to the client
    private static class EchoThread implements Runnable {
        private final Socket clientSocket;
 
        public EchoThread(final Socket socket) {
            this.clientSocket = socket;
        }
 
        @Override
        public void run() {
            PrintWriter toClient = null;
            BufferedReader fromClient = null;
            try {
                toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                // Display what is being sent by client to the server
                while ((line = fromClient.readLine()) != null) {
                    System.out.printf("Sent from the client: %s\n", line);
                    String lineCheck = line.replaceAll("[^\\p{IsAlphabetic}]", "");
                    toClient.println(lineCheck);
                    if("quit".equalsIgnoreCase(lineCheck)){
                        break;
                    }


                }
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (toClient != null) {
                        toClient.close();
                    }
                    if (fromClient != null)
                        fromClient.close();
                    // Client has disconnected from the server
                    System.out.println("Client disconnecting...");
                    clientSocket.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
           
		
    


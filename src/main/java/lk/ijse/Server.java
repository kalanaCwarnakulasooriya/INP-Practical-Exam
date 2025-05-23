package lk.ijse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8000;
    private static long startTime;
    public static void main(String[] args) throws IOException {
        startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
//            List<Socket> clients = new ArrayList<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
//                clients.add(clientSocket);
                pool.execute(new ClientHandler(clientSocket,startTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

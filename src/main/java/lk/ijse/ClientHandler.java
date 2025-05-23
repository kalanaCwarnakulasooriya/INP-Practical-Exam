package lk.ijse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientHandler implements Runnable{
    private Socket socket;
    private long startTime;

    public ClientHandler(Socket socket, long startTime) {
        this.socket = socket;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true);)
        {
            String input;
            while ((input = in.readLine()) != null ){
                switch (input.toUpperCase()){
                    case "TIME":
                        out.println("Time : " + LocalTime.now().toString());
                        break;

                    case "DATE":
                        out.println("Date : " + LocalDate.now().toString());
                        break;

                    case "UPTIME":
                        long uptime = (System.currentTimeMillis() - startTime) / 1000;
                        out.println("Server UpTime : " + uptime + " seconds");
                        break;

                    case "HELP":
                        out.println("Available commands : TIME , DATE , UPTIME , BYE , HELP");
                        break;

                    case "BYE":
                        out.println("Good Bye !");
                        socket.close();
                        return;

                    default:
                        out.println("Unknown command ! Type HELP for available commands");
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

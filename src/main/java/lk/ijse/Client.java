package lk.ijse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 8000;

        try(Socket socket = new Socket(HOST,PORT);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true))
            {
                Thread thread = new Thread(()->{
                    try {
                        String response;
                        while ((response = in.readLine()) != null){
                            System.out.println(response);
                        }
                    } catch (IOException e) {
                        System.out.println("Connection Closed");
                    }
                });
                thread.start();

                String userInput;
                while ((userInput = input.readLine()) != null){
                    out.println(userInput);
                    if ("BYE".equalsIgnoreCase(userInput)) break;
                }

            } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

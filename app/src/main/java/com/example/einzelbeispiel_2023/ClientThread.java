package com.example.einzelbeispiel_2023;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread implements Runnable {
    private String host = "se2-isys.aau.at";
    private int port = 53212;
    private String matrNumber;
    private String answerFromServer;

    public ClientThread(String matrNumber) {
        this.matrNumber = matrNumber;
    }

    public String getAnswerFromServer() {
        return answerFromServer;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            OutputStream output = socket.getOutputStream();
            InputStream input = socket.getInputStream();

            //print writer is used to send data in text format instead of byte array
            // true - writer flushes data after each method call
            PrintWriter writer = new PrintWriter(output, true);
            //Buffered Reader is used to read data from higher level (f.E. Strings) and not just bytes
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // write to output stream
            writer.println(matrNumber);

            // read the answer
            String line;
            StringBuilder answer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            answerFromServer = answer.toString();

        }
        catch (UnknownHostException ex) {
            Log.e("Error", "Server not found " + ex.getMessage());
        }
        catch (IOException ex) {
            Log.e("Error", "I/O error " + ex.getMessage());
        }

    }
}

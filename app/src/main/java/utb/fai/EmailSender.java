package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();

        try {
            Thread.sleep(500);
            if (input.available() > 0) {
                byte[] response = new byte[1024];
                int len = input.read(response);
                System.out.write(response, 0, len);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        byte[] buffer;
        final byte[] response = new byte[1024];
        int len;
        String message;

        try {
            message = "EHLO localhost\r\n";
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                len = input.read(response);
                System.out.write(response, 0, len);
            }

            message = "MAIL FROM:<" + from + ">\r\n";
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                len = input.read(response);
                System.out.write(response, 0, len);
            }

            message = "RCPT TO:<" + to + ">\r\n";
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                len = input.read(response);
                System.out.write(response, 0, len);
            }
            message = "DATA\r\n";
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                len = input.read(response);
                System.out.write(response, 0, len);
            }

            message = "From: " + from + "\r\n" +
                      "To: " + to + "\r\n" +
                      "Subject: " + subject + "\r\n" +
                      "\r\n" + 
                      text + "\r\n" +
                      ".\r\n";
                      
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                len = input.read(response);
                System.out.write(response, 0, len);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            String message = "QUIT\r\n";
            byte[] buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            if (input.available() > 0) {
                byte[] response = new byte[1024];
                int len = input.read(response);
                System.out.write(response, 0, len);
            }

            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package utb.fai;

import java.net.Socket;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing
        if (args.length < 6) {
            System.out.println("Nedostatek parametrů");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String from = args[2];
        String to = args[3];
        String subject = args[4];
        String text = args[5];

        try {
            EmailSender sender = new EmailSender(host, port);
            sender.send(from, to, subject, text);
            sender.close();
            
            System.out.println("Email byl uspesne odeslan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

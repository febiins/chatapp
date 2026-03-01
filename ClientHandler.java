import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    // Called by ChatServer
    public void send(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true);

            // First message = username
            username = in.readLine();
            ChatServer.clients.put(username, this);

            String encrypted;
            while ((encrypted = in.readLine()) != null) {

                String msg = CryptoUtil.decrypt(encrypted);
                if (msg == null) continue;

                // 🔒 PRIVATE CHAT
                if (msg.startsWith("@")) {
                    int i = msg.indexOf(" ");
                    if (i != -1) {
                        ChatServer.privateMessage(
                                msg.substring(1, i),
                                username,
                                msg.substring(i + 1));
                    }
                }

                // 👥 GROUP CHAT
                else if (msg.startsWith("#")) {
                    int i = msg.indexOf(" ");
                    if (i != -1) {
                        String group = msg.substring(1, i);
                        String text = msg.substring(i + 1);

                        ChatServer.joinGroup(group, this);
                        ChatServer.groupMessage(
                                group, username, text);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            ChatServer.clients.remove(username);

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
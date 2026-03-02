import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String username;
    private int userId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    // Used by ChatServer to send messages
    public void send(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
    try {
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        username = in.readLine();
        ChatServer.clients.put(username, this);

        String enc;
        while ((enc = in.readLine()) != null) {
            String msg = CryptoUtil.decrypt(enc);

            //  PRIVATE CHAT
            if (msg.startsWith("@")) {
                int i = msg.indexOf(" ");
                String toUser = msg.substring(1, i);
                String text = msg.substring(i + 1);

                ChatServer.sendPrivate(
                        toUser,
                        CryptoUtil.encrypt(
                                "[PRIVATE] " + username + ": " + text)
                );
            }

            //  GROUP CHAT (FIXED)
            else if (msg.startsWith("#")) {
                int i = msg.indexOf(" ");
                String group = msg.substring(1, i);
                String text = msg.substring(i + 1);

                //  JOIN GROUP FIRST
                ChatServer.joinGroup(group, this);

                //  SEND TO GROUP MEMBERS
                ChatServer.sendGroup(
                        group,
                        CryptoUtil.encrypt(
                                "[" + group + "] " + username + ": " + text)
                );
            }
        }
    } catch (Exception e) {
        ChatServer.clients.remove(username);
    }
}
}
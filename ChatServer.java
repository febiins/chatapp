import java.net.*;
import java.util.*;

public class ChatServer {

    // Online users
    static Map<String, ClientHandler> clients = new HashMap<>();

    // Group name → members
    static Map<String, Set<ClientHandler>> groups = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);
        System.out.println("Chat Server started on port 5000");

        while (true) {
            Socket socket = server.accept();
            new ClientHandler(socket).start();
        }
    }

    // PRIVATE MESSAGE
    static void privateMessage(String toUser, String fromUser, String msg) {
        ClientHandler target = clients.get(toUser);
        if (target != null) {
            target.send(CryptoUtil.encrypt(
                "[PRIVATE] " + fromUser + ": " + msg
            ));
        }
    }

    // GROUP MESSAGE
    static void groupMessage(String group, String fromUser, String msg) {
        Set<ClientHandler> members = groups.get(group);
        if (members != null) {
            for (ClientHandler ch : members) {
                ch.send(CryptoUtil.encrypt(
                    "[" + group + "] " + fromUser + ": " + msg
                ));
            }
        }
    }

    // Join a group (auto join on first message)
    static void joinGroup(String group, ClientHandler ch) {
        groups.computeIfAbsent(group, g -> new HashSet<>()).add(ch);
    }
}
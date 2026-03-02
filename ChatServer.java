import java.net.*;
import java.util.*;

public class ChatServer {

    static Map<String, ClientHandler> clients = new HashMap<>();

    // 🔥 ADD THIS: group → members
    static Map<String, Set<ClientHandler>> groups = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server started");

        while (true)
            new ClientHandler(server.accept()).start();
    }

    // PRIVATE MESSAGE
    static void sendPrivate(String user, String msg) {
        if (clients.containsKey(user))
            clients.get(user).send(msg);
    }

    // GROUP MESSAGE
    static void sendGroup(String group, String msg) {
        if (!groups.containsKey(group)) return;

        for (ClientHandler ch : groups.get(group)) {
            ch.send(msg);
        }
    }

    // JOIN GROUP
    static void joinGroup(String group, ClientHandler ch) {
        groups.computeIfAbsent(group, g -> new HashSet<>()).add(ch);
    }
}
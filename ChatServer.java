import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static Map<String, ClientHandler> clients = new HashMap<>();
    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(1234);
            System.out.println("Chat Server Started");
            while(true){
                new ClientHandler(ss.accept()).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        static void broadcast(String msg) {
        for (ClientHandler c : clients.values()){
            c.send(CryptoUtil.encrypt(msg));
        }    
        }

    static void privateMsg(String to, String from, String msg) {
        ClientHandler c = clients.get(to);
        if (c != null){
            c.send(CryptoUtil.encrypt(
                    "[PRIVATE] " + from + ": " + msg));
        }            
    }
    }
}

import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws Exception {

        ServerSocket serversocket = new ServerSocket(1234);
        ChatbotServer server = new ChatbotServer(serversocket);
        server.startServer();
    }
}
import java.net.ServerSocket;
import java.net.Socket;

public class ChatbotServer {
    private ServerSocket serversocket;
    public ChatbotServer(ServerSocket serversocket){
        this.serversocket = serversocket;
    }
    public void startServer(){
        try{
            System.out.println("Server started! ");
            while (!serversocket.isClosed()){
                Socket socket = serversocket.accept();
                System.out.println("A client has connected ");
                ClientHandler clienthandler = new ClientHandler(socket);
                Thread t = new Thread(clienthandler);

                t.start();

            }

        } catch(Exception e){
        e.printStackTrace();
        }
    }
    public void closeServersocket(){
        try{
            if (serversocket != null){
                serversocket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

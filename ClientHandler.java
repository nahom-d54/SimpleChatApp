import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clienthandler = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // the first line is the person username

            this.clientUsername = bufferedReader.readLine();

            clienthandler.add(this);
            brodcastMessage("Server: "+clientUsername+" has entered the chat");
        }catch (Exception e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }
    @Override
    public void run(){
        String clientmsg;
        while (socket.isConnected()){
            try {
                clientmsg = bufferedReader.readLine();
                brodcastMessage(clientmsg);
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void brodcastMessage(String msg){
        for(ClientHandler clientHandler: clienthandler){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(msg);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (Exception e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clienthandler.remove(this);

        brodcastMessage("Server: "+clientUsername+" Has left the chat!");

    }
    public void closeEverything(Socket sk, BufferedReader br, BufferedWriter bw){
        removeClientHandler();
        try{
            if (br != null){
                br.close();
            }
            if (bw != null){
                bw.close();
            }
            if (sk != null){
                sk.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

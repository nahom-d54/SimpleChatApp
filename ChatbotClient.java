import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatbotClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public  ChatbotClient(Socket socket, String username){
        try{
            this.socket = socket;
            this.username = username;
            this.bufferedWriter =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (Exception e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    public void sendMessage(String message){
        try {

            bufferedWriter.write(username+": "+message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void setUsername(String username){
        try {

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void listenForMessage(JTextArea textArea){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        String messageFromChat;
                        while (socket.isConnected()){
                            messageFromChat = bufferedReader.readLine();
                            System.out.println(messageFromChat);
                            textArea.append(messageFromChat + "\n");
                        }
                    }catch (Exception e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }

                }
            }).start();


    }
    public void closeEverything(Socket sk, BufferedReader br, BufferedWriter bw){
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

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your username for groupchat: ");
        Gui gui = new Gui();
        gui.getUsername();

    }
}

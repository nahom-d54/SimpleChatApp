import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

public class Gui {
    // create frame
    public String username;
    private ChatbotClient client;
    private Socket socket;

    public Gui(){
        try {
            this.socket = new Socket("localhost", 1234);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // ChatbotClient

    }


    public void getUsername(){
        JFrame dialog = new JFrame("Set Username");

        dialog.setResizable(false);

        JLabel user = new JLabel("Username");
        JTextField userf = new JTextField();
        JButton userok = new JButton("Ok");
        dialog.setSize(300, 230);
        dialog.setLayout(null);
        user.setBounds(10, 10 , 90, 30);
        userf.setBounds(75,10,200, 30);
        userok.setBounds(10,60,250,30);


        dialog.add(user);
        dialog.add(userf);
        dialog.add(userok);
        userok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = userf.getText();
                dialog.dispose();

                client = new ChatbotClient(socket, username);
                client.setUsername(username);
                startGui();

                //client.sendMessage();

            }
        });
        dialog.setVisible(true);
    }



    public void startGui(){
        JFrame f=new JFrame("Chat app: user -> "+ username);

        f.setResizable(false);

        JButton b=new JButton("Send");
        JTextArea textArea = new JTextArea();
        JTextField msgField = new JTextField();

        textArea.setBounds(10,10, 270,280);
        b.setBounds(225,320,70, 30);
        msgField.setBounds(10,320, 210, 30);

        textArea.setEditable(false);

        client.listenForMessage(textArea);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append(msgField.getText() + "\n");
                client.sendMessage(msgField.getText());
                msgField.setText("");
            }
        });


        f.add(b);
        f.add(textArea);
        f.add(msgField);
        f.setSize(330,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

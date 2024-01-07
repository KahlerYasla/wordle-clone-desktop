package onlinePackage;

import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Client extends JFrame
{
    private JTextPane jtp0;

    protected JButton jb0;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket myClient;
    private String srv;
    private JFrame jf;

    private int row=0;

    private JLabel[][] charBoxs;

    public Client(JFrame jf0,String str0,JTextPane jtp,JButton jb,JLabel[][] jl)
    {
        srv=str0;
        jtp0=jtp;
        jb0=jb;
        jf=jf0;
        charBoxs=jl.clone();
    }
    public void runClient() {

        try {
            System.out.println("0");
            connToS();
            System.out.println("1");
            streams();
            System.out.println("2");
            processConn();
            System.out.println("3");
        }
        catch (EOFException e) {
            dispMessage("\nClient Terminated Connection\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeConn();
        }
    }

    private void connToS() throws IOException{
        dispMessage("Attempting\n");
        myClient = new Socket(InetAddress.getByName(srv), 31313);
    }

    private void streams() throws IOException{
        oos = new ObjectOutputStream(myClient.getOutputStream());
        oos.flush();

        ois = new  ObjectInputStream(myClient.getInputStream());
        dispMessage("\n Streams\n");
    }

    private void processConn() throws IOException{
        send("Successful");
        setButtonEnabled(true);
        String msg = "";

        do {
            try {
                msg = (String) ois.readObject();
                if(msg.charAt(2)=='#')
                {
                    jtp0.setText("opponent has entered the : "+msg);
                    for (int i=0;i<5;i++){charBoxs[row][i].setText(String.valueOf(msg.charAt(i+3)));}
                    row++;
                }
                dispMessage("\n" + msg);
            }
            catch(ClassNotFoundException e) {
                dispMessage("Unknown");
            }
        }while(!msg.equals("S:ExitTheSystem"));
    }

    private void closeConn() {
        dispMessage("\nTerminating Connection\n");
        setButtonEnabled(false);

        try {
            oos.close();
            ois.close();
            myClient.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        try {
            oos.writeObject("C:" + text);
            oos.flush();
            dispMessage("\nC:" + text);
        }
        catch (IOException e) {
            jtp0.setText(jtp0.getText()+"\nError\n");
        }
    }

    private void dispMessage(final String string) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                jtp0.setText(jtp0.getText()+string);
            }
        });
    }

    private void setButtonEnabled(final boolean b) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                jb0.setEnabled(b);
            }
        });
    }
}

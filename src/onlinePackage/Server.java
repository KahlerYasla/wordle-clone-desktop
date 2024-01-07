package onlinePackage;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Server extends JFrame
{
    private JTextPane jtp0;
    protected JButton jb0;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket server0;
    private Socket conn;

    private JFrame jf;

    private int row=0;

    private JLabel[][] charBoxs;


    Server(JFrame jf0, JTextPane jtp, JButton jb, JLabel[][] jl)
    {
        jtp0=jtp;
        jb0=jb;
        jf=jf0;
        charBoxs=jl.clone();
    }

    public void runServer() {
        try {
            server0 = new ServerSocket(31313, 1000);

            while(true) {
                System.out.println("runServer");
                try {
                    waitConn();
                    System.out.println("runServer>try>between waitConn-streams");
                    streams();
                    processConn();
                }
                catch (EOFException e) {
                    dispMessage("\nServer Terminated Connection\n");
                    System.out.println("runServer>catch");
                }
                finally {
                    System.out.println("closeConnInRunServer");
                    closeConn();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitConn() throws IOException{
        dispMessage("Please Wait...\n");
        System.out.println("waitConn0");
        conn = server0.accept();
        System.out.println("waitConn1");
        dispMessage("Connection Received\n");
        System.out.println("waitConn2");
    }

    private void streams() throws IOException{
        System.out.println("streams");
        oos = new ObjectOutputStream(conn.getOutputStream());
        oos.flush();

        ois = new  ObjectInputStream(conn.getInputStream());
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
        }while(!msg.equals("C:ExitTheSystem"));
    }

    private void closeConn() {
        System.out.println("closeConn");
        dispMessage("\nTerminating Connection\n");
        setButtonEnabled(false);

        try {
            oos.close();
            ois.close();
            conn.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        System.out.println("send");
        try {
            oos.writeObject("S:" + text);
            oos.flush();
            dispMessage("\nS:" + text);
        }
        catch (IOException e) {
            jtp0.setText(jtp0.getText()+"\nError\n");
        }
    }

    private void dispMessage(final String string) {
        System.out.println("dispMessage");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("dispMessage>run");
                jtp0.setText(jtp0.getText()+string);
            }
        });
        System.out.println("dispMessage>run>quiting");
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

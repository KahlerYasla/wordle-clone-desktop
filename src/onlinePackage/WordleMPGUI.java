package onlinePackage;

import gamePackage.MainMenu;
import gamePackage.WordDictionary;
import gamePackage.WordTablePanel;
import gamePackage.WordleSpGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordleMPGUI extends JFrame implements Runnable
{
    KeyboardPanel kP=new KeyboardPanel();

    WordDictionary dictionar = new WordDictionary();
    String answerWord = dictionar.getWord().toUpperCase();
    boolean firstPlayer=false;
    boolean IsSpectate;
    boolean capsLock=false;
    int row0 = 0;
    int column0 = 0;
    boolean flag1=true;

    WordTablePanel wtp0 = new WordTablePanel();
    WordTablePanel wtp1 = new WordTablePanel();
    public class KeyboardPanel extends JPanel {
        JButton[] keyboardButtons = new JButton[30];

        public KeyboardPanel() {
            // TODO: 3.06.2022 - keyboardPanel - keyboardButtons Z Eklenecek.
            setLayout(new GridLayout(3, 1, 5, 0));
            this.setBackground(new Color(20, 20, 30));

            JPanel panelKeyboard0 = new JPanel();
            JPanel panelKeyboard1 = new JPanel();
            JPanel panelKeyboard2 = new JPanel();

            panelKeyboard0.setLayout(new GridLayout(1, 10, 0, 5));
            panelKeyboard1.setLayout(new GridLayout(1, 9, 0, 5));
            panelKeyboard2.setLayout(new GridLayout(1, 9, 0, 5));

            Border boxBorder = BorderFactory.createLineBorder(new Color(20, 20, 30));

            for (int i = 0; i < 10; i++) {
                keyboardButtons[i] = new JButton("" + (char) (65 + i));
                keyboardButtons[i].setBackground(Color.darkGray);
                keyboardButtons[i].setForeground(Color.white);
                keyboardButtons[i].setBorder(boxBorder);
                keyboardButtons[i].setFont(new Font("", Font.PLAIN, 20));
                keyboardButtons[i].setFocusable(false);
                panelKeyboard0.add(keyboardButtons[i]);
            }

            for (int i = 10; i < 19; i++) {
                keyboardButtons[i] = new JButton("" + (char) (65 + i));
                keyboardButtons[i].setBackground(Color.darkGray);
                keyboardButtons[i].setForeground(Color.white);
                keyboardButtons[i].setBorder(boxBorder);
                keyboardButtons[i].setFont(new Font("", Font.PLAIN, 20));
                keyboardButtons[i].setFocusable(false);
                panelKeyboard1.add(keyboardButtons[i]);
            }

            for (int i = 19; i < 28; i++)
            {
                if (i == 19)
                {
                    keyboardButtons[i] = new JButton("Enter");
                    keyboardButtons[i].setFont(new Font("", Font.PLAIN, 30));
                    keyboardButtons[i].setBackground(Color.darkGray);
                    keyboardButtons[i].setForeground(Color.white);
                    keyboardButtons[i].setBorder(boxBorder);
                    panelKeyboard2.add(keyboardButtons[i]);
                    keyboardButtons[i].setFocusable(false);

                }

                else if (i == 27)
                {
                    keyboardButtons[i] = new JButton("DEL" );
                    keyboardButtons[i].setFont(new Font("", Font.PLAIN, 30));
                    keyboardButtons[i].setBackground(Color.darkGray);
                    keyboardButtons[i].setForeground(Color.white);
                    keyboardButtons[i].setBorder(boxBorder);
                    keyboardButtons[i].setFocusable(false);
                    panelKeyboard2.add(keyboardButtons[i]);

                }

                else
                {
                    keyboardButtons[i] = new JButton("" + (char) (65 + i-1) );
                    keyboardButtons[i].setBackground(Color.darkGray);
                    keyboardButtons[i].setForeground(Color.white);
                    keyboardButtons[i].setBorder(boxBorder);
                    keyboardButtons[i].setFont(new Font("", Font.PLAIN, 20));
                    keyboardButtons[i].setFocusable(false);
                    panelKeyboard2.add(keyboardButtons[i]);
                }

            }

            panelKeyboard0.setBackground(new Color(20, 20, 30));
            panelKeyboard1.setBackground(new Color(20, 20, 30));
            panelKeyboard2.setBackground(new Color(20, 20, 30));

            setBounds(100, 150, 700, 250);

            add(panelKeyboard0);
            add(panelKeyboard1);
            add(panelKeyboard2);

            for (int i=0; i<27; i++)
            {
               // keyboardButtons[i].addMouseListener(mouseListener);
            }

        }
    }

    class chatBoxPanel extends JTextPane
    {
        public JButton sendButton= new JButton("SEND");
        public JTextField inputArea=new JTextField("first click the chat box and toggle with Caps Lock to use Chatbox");

        public chatBoxPanel()
        {
        inputArea.setEditable(false);

        setBounds(850,50,550,350);
        setBackground(new Color(20, 20, 30)); setForeground(Color.WHITE);
        LineBorder lineBorder =new LineBorder(Color.black, 3, true);
        setBorder(lineBorder);
        setLayout(new BorderLayout());
        setEditable(false);

        JPanel panel33=new JPanel();
        panel33.setLayout(new GridLayout(2,1));

        panel33.add(inputArea);
        panel33.add(sendButton);

        add(panel33,BorderLayout.SOUTH);

        }
    }
    public chatBoxPanel chatbox0= new chatBoxPanel();
    public Server server=new Server(this,chatbox0,chatbox0.sendButton,wtp1.charBoxs);
    public Client client=new Client(this,"127.0.0.1",chatbox0,chatbox0.sendButton,wtp1.charBoxs);

    public Spectator spectator=new Spectator(this,"127.0.0.1",chatbox0,chatbox0.sendButton,wtp1.charBoxs);
    public void checkWord()
    {
        // TODO: 10.06.2022 - I ve i harfinde okuma problemi var.
        boolean win=true;
        for(int i =0; i<5; i++)
        {

            if(answerWord.contains(wtp0.charBoxs[row0][i].getText()))
            {
                wtp0.charBoxs[row0][i].setBackground(Color.orange);

                for(int j=0; j<27; j++) {
                    if (Objects.equals(wtp0.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText())) {
                        kP.keyboardButtons[j].setBackground(Color.orange);
                        System.out.print("" + kP.keyboardButtons[j].getText());
                    }
                }

                System.out.println(answerWord.charAt(i) + " " + wtp0.charBoxs[row0][i].getText().charAt(0));

                if(wtp0.charBoxs[row0][i].getText().charAt(0) == (answerWord.charAt(i)))
                {
                    wtp0.charBoxs[row0][i].setBackground(Color.green);

                    for(int j=0; j<27; j++) {
                        if (Objects.equals(wtp0.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText())) {
                            kP.keyboardButtons[j].setBackground(Color.green);
                            System.out.print("" + kP.keyboardButtons[j].getText());
                        }
                    }
                }

            }
            else
            {
                wtp0.charBoxs[row0][i].setBackground(Color.lightGray);
                for(int j=0; j<27; j++) {
                    if (Objects.equals(wtp0.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText())) {
                        kP.keyboardButtons[j].setBackground(Color.lightGray);
                        System.out.print("" + kP.keyboardButtons[j].getText());
                    }
                }


            }
            if(wtp0.charBoxs[row0][i].getBackground()!=Color.green){win=false;}
        }
        if(win)
        {
                JOptionPane.showMessageDialog(this, "You won","Excellent!", JOptionPane.INFORMATION_MESSAGE);
                WordleMPGUI.this.dispose();
                new MainMenu();
        }

    }



    public WordleMPGUI(boolean xxx)
    {
        IsSpectate=xxx;

        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                if(capsLock)
                {
                    if(e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
                    {
                        capsLock= !capsLock;
                        System.out.println("capsLock");
                        chatbox0.inputArea.setText("first click the chat box and toggle with Caps Lock to use Chatbox");
                    }

                    String keyChar = String.valueOf(e.getKeyChar());

                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                    }
//
                    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    {
                        System.out.println("3");
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                                "Quit", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        } else if (result == JOptionPane.NO_OPTION) {
                            System.out.println("5");
                        }
                    }

                    else if (e.getKeyCode() == KeyEvent.VK_HOME)
                    {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?",
                                "Quit", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            dispose();
                            new WordleMPGUI(false);
                        } else if (result == JOptionPane.NO_OPTION) {
                            System.out.println("5");
                        }
                    }

                    else if (e.getKeyCode() >= 65 && e.getKeyCode() < 91)
                    {
                        chatbox0.inputArea.setText(chatbox0.inputArea.getText()+keyChar);
                    }

                }

                else {

                    if(e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
                    {
                        capsLock= !capsLock;
                        System.out.println("capsLock");
                        chatbox0.inputArea.setText("");
                    }

                    String keyChar = String.valueOf(e.getKeyChar());

                    if (e.getKeyCode() == KeyEvent.VK_ENTER && column0 == 4 && !(wtp0.charBoxs[row0][4].getText().equals("")))
                    {
                        String s = "";

                        for (int i = 0; i < 5; i++) {
                            char ch = wtp0.charBoxs[row0][i].getText().charAt(0);
                            s = s + ch;
                        }

                        System.out.println(s + "");

                        boolean willSend=false;

                        if (dictionar.wordsList.contains(s.toLowerCase())) {
                            checkWord();
                            row0++;
                            column0 = 0;
                            willSend=true;

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Entered word not in our database");
                        }

                        if(willSend)
                        {
                            if (firstPlayer)
                            {
                                String sendingMsg = "";
                                for (int m = 0; m < 5; m++) {
                                    sendingMsg += wtp0.charBoxs[row0 - 1][m].getText();
                                }
                                String sendingMsg2 = "#" + sendingMsg;
                                System.out.println(sendingMsg2 + " wordleMPGUI 310 server ");
                                server.send(sendingMsg2);
                            }
                            else
                            {
                                String sendingMsg = "";
                                for (int m = 0; m < 5; m++) {
                                    sendingMsg += wtp0.charBoxs[row0 - 1][m].getText();
                                }
                                String sendingMsg2 = "#" + sendingMsg;
                                System.out.println(sendingMsg2 + " wordleMPGUI 310 client ");
                                client.send(sendingMsg2);
                            }
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && column0 >= 0) {
                        if (column0 == 4)
                        {
                            wtp0.charBoxs[row0][column0].setText("");
                            column0--;
                            flag1 = false;
                        }
                        else if (column0 != 0 && flag1)
                        {
                            column0--;
                            wtp0.charBoxs[row0][column0].setText("");
                        } else if (!flag1) {
                            wtp0.charBoxs[row0][column0].setText("");
                            flag1 = true;
                        }
                        System.out.println("0");
                    }
//

                    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    {
                        System.out.println("3");
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                                "Quit", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION)
                        {
                            System.exit(0);
                        }
                        else if (result == JOptionPane.NO_OPTION)
                        {
                            System.out.println("5");
                        }

                    }
                    else if (e.getKeyCode() == KeyEvent.VK_DELETE)
                    {
                        row0 = 0;
                        column0 = 0;
                        System.out.println("4");
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_HOME)
                    {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?",
                                "Quit", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION)
                        {
                            dispose();
                            new WordleMPGUI(false);
                        }
                        else if (result == JOptionPane.NO_OPTION)
                        {
                            System.out.println("5");
                        }
                    }
                    else if (e.getKeyCode() >= 65 && e.getKeyCode() < 91)
                    {
                        wtp0.charBoxs[row0][column0].setText(keyChar.toUpperCase() + "");
                        if (column0 != 4)
                        {
                            column0++;
                        }
                    }

                    if (row0 >= 6) {
                        JOptionPane.showMessageDialog(null, "All Boxes Entered Try Again!");
                        dispose();
                        new MainMenu();
                    }

                    wtp0.highlight(row0, column0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // unnecessary.
            }

        };

        if(!IsSpectate)
        {
            int result = JOptionPane.showConfirmDialog(null, "Will you be the first player (Do you want to be hosting side I mean Server)?",
                    "Hosting Or Joining The Session", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                firstPlayer = true;
            } else if (result == JOptionPane.NO_OPTION) {
                firstPlayer = false;
            }
        }
        else {firstPlayer=false;}

        chatbox0.sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(firstPlayer)
                {
                    server.send(chatbox0.inputArea.getText());
                    chatbox0.inputArea.setText(chatbox0.inputArea.getText()+"\n"+chatbox0.inputArea.getText());
                    chatbox0.inputArea.setText("");
                }
                else
                {
                    client.send(chatbox0.inputArea.getText());
                    chatbox0.inputArea.setText(chatbox0.inputArea.getText()+"\n"+chatbox0.inputArea.getText());
                    chatbox0.inputArea.setText("");
                }


            }
        });

        JPanel panel0= new JPanel();
        JPanel panel1= new JPanel();

        JPanel panelNorth = new JPanel();
        JPanel panelSouth = new JPanel();

        panel0.setBackground(new Color(20,20,30));
        panel1.setBackground(new Color(20,20,30));

        panelSouth.setBackground(new Color(20,20,30));

        panelSouth.setLayout(null);
        panelNorth.setLayout(new GridLayout(1,2));
        panel0.setLayout(null);
        panel1.setLayout(null);

        wtp0.setBounds(100,60,600,400);
        panel0.add(wtp0);

        wtp1.setBounds(60,60,600,400);
        panel1.add(wtp1);


        panelSouth.add(kP);
        panelSouth.add(chatbox0);

        panelNorth.add(panel0);
        panelNorth.add(panel1);

        if(!IsSpectate){chatbox0.addKeyListener(keyListener);setTitle("NERDLE Multi Player (Spectating)");}
        else
        {
            if (firstPlayer) {
                setTitle("NERDLE Multi Player (Hosting)");
            } else {
                setTitle("NERDLE Multi Player (Has Joined the Server)");
            }
        }

        setLayout(new GridLayout(2,1));
        // TODO: 4.06.2022 - Size'ı pc ekranı bilgilerini alarak yazılacak.
        setBounds(500,150,1500,927);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        revalidate();

        add(panelNorth);
        add(panelSouth);

        setVisible(true);

    }

    @Override
    public void run()
    {
        if(firstPlayer)
        {
            server.runServer();
        }
        else
        {
            client.runClient();
            System.out.println("first player değilken olan elsenin içine girdi ve runclientı bitirdi");
        }
    }


    public static void main(String[] args)
    {
        WordleMPGUI wordleMPGUI0=new WordleMPGUI(false);
        ExecutorService exec0 = Executors.newCachedThreadPool();
        exec0.execute(wordleMPGUI0);
    }
}

package gamePackage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;


public class WordleSpGUI extends JFrame implements MouseMotionListener
{
    JLabel title = new JLabel("NERDLE");
    JLabel scoreLbl = new JLabel("Score: " + 0);
    JLabel timeLbl = new JLabel("timer");

    JLabel esclbl = new JLabel(" Press ESC to exit");

    JLabel homelbl = new JLabel(" Press HOME to Restart");

    static JLabel highscoreLbl = new JLabel("Highscore: " + 0);
    Timer stopWatch;
    int timeCounter;
    int scoreCounter = 0;
    static int highscoreCounter;
    static int winCounter = 0;
    int row0 = 0;
    int column0 = 0;
    boolean flag1=true;
    int draggingX = 0;
    int draggingY = 0;
    int draggingXOffset = 0;
    int draggingYOffset = 0;
    boolean win=false;
    boolean win2=false;



    WordTablePanel wTP = new WordTablePanel();
    KeyboardPanel kP = new KeyboardPanel();
    WordDictionary dictionar = new WordDictionary();
    String answerWord = dictionar.getWord().toUpperCase();



    private void loadStats(){
        BufferedReader br = null;
        String line = "";
        File statsFile = new File("stats.txt");
        java.util.List<String> profileNameList = new ArrayList<String>();
        int i;

        try
        {
            profileNameList = Files.readAllLines(statsFile.toPath());


            for(i = 0; i < profileNameList.size(); i++)
            {
                if(profileNameList.get(i).contains(LoginFrame.loginName))
                {
                    String[] lineSplit = profileNameList.get(i).split("&");
                    if(lineSplit[0].equals(LoginFrame.loginName))
                    {
                        highscoreCounter = Integer.parseInt(lineSplit[1]);
                        highscoreLbl.setText("Highscore: " + highscoreCounter);
                        winCounter = Integer.parseInt(lineSplit[2]);

                    }

                    break;
                }
            }

            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/stats.txt"));
            line = br.readLine();
            br.close();

        }
        catch (IOException e)
        {
            line = "";
        }


    }

    private void saveStats(){
        BufferedWriter bw = null;
        try
        {

            File statsFile = new File("stats.txt");
            java.util.List<String> statsList = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/stats.txt"));
            bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/stats.txt", true));

            statsList = Files.readAllLines(statsFile.toPath());


            boolean flag = false;
            String line;
            int i ;

            for(i = 0; i < statsList.size(); i++)
            {
                if(statsList.get(i).contains(LoginFrame.loginName))
                {
                    statsList.set(i, LoginFrame.loginName + "&" + highscoreCounter + "&" + winCounter);
                    flag = true;
                    break;
                }
            }

            if(flag)
            {

                new FileOutputStream("stats.txt").close();

                for (int j= 0; j < statsList.size(); j++)
                {
                   bw.write(statsList.get(j));
                   if(j<statsList.size()-1)
                   {
                       bw.newLine();
                   }


                }

                bw.flush();
                bw.close();
            }


            else
            {
                if(br.readLine() == null)
                {
                    winCounter = 1;
                    bw.write(LoginFrame.loginName + "&" + highscoreCounter + "&" + winCounter);
                    bw.flush();
                    bw.close();
                }
                else
                {
                    bw.newLine();
                    winCounter = 1;
                    bw.write(LoginFrame.loginName + "&" + highscoreCounter + "&" + winCounter);
                    bw.flush();
                    bw.close();
                }


            }

        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error while saving highscore", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void startTimer()
    {
        ActionListener action = e -> {

            timeLbl.setText("Time: " + timeCounter);
            timeCounter++;

        };

        stopWatch = new Timer(1000, action);
        stopWatch.start();
        stopWatch.setInitialDelay(0);
    }

    public void paintLast(boolean f, Graphics g2)
    {
        if(f)
        {
            //dispose();
            g2.setColor(Color.GREEN);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("", Font.PLAIN, 50));
            g2.drawString("You Win!", 800, 100);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Press 'Esc' to exit", 800, 750);

        }
    }
    public void paint(Graphics g)
    {
        super.paintComponents(g);


            Graphics2D g2 = (Graphics2D)g;

            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

            int buttonWidth = kP.keyboardButtons[0].getWidth();
            int buttonHeight = kP.keyboardButtons[0].getHeight();
            Color buttonColor = kP.keyboardButtons[0].getBackground();
            Color labelColor = wTP.charBoxs[row0][column0].getForeground();
            String labelText = wTP.charBoxs[row0][column0].getText();
            Font labelFont = wTP.charBoxs[row0][column0].getFont();
            FontMetrics metrics = wTP.charBoxs[row0][column0].getFontMetrics(labelFont);
            int labelWidth = metrics.stringWidth(labelText);
            int labelHeight = metrics.getHeight();
            int labelAscent = metrics.getAscent();

            g2.setColor(buttonColor);
            g2.fillRect(draggingX, draggingY, buttonWidth, buttonHeight);

            g2.setColor(labelColor);
            g2.setFont(labelFont);
            g2.drawString(labelText, draggingX + (buttonWidth - labelWidth)/2, draggingY + (buttonHeight - labelHeight)/2 + labelAscent);

            paintLast(win2, g2);


        }




    public void checkWord()
    {

        for(int i =0; i<5; i++)
        {

            if(answerWord.contains(wTP.charBoxs[row0][i].getText()))
                    {
                        wTP.charBoxs[row0][i].setBackground(Color.orange);

                        scoreCounter = scoreCounter + (6-row0) * 5 * (100 - timeCounter);
                        scoreLbl.setText("Score: " + scoreCounter);

                        for(int j=0; j<27; j++) {
                            if (Objects.equals(wTP.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText())) {
                                kP.keyboardButtons[j].setBackground(Color.orange);

                            }
                        }



                        if(wTP.charBoxs[row0][i].getText().charAt(0) == (answerWord.charAt(i)))
                        {
                            wTP.charBoxs[row0][i].setBackground(Color.green);
                            scoreCounter = scoreCounter + (6-row0) * 5;

                            scoreLbl.setText("Score: " + scoreCounter);

                            for(int j=0; j<27; j++) {
                                if (Objects.equals(wTP.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText())) {
                                    kP.keyboardButtons[j].setBackground(Color.green);

                                }
                            }
                        }

                    }
                    else
                    {
                        wTP.charBoxs[row0][i].setBackground(Color.lightGray);
                        for(int j=0; j<27; j++)
                        {
                            if (Objects.equals(wTP.charBoxs[row0][i].getText(), kP.keyboardButtons[j].getText()))
                            {
                                kP.keyboardButtons[j].setBackground(Color.lightGray);

                            }
                        }


                    }
            if(wTP.charBoxs[row0][i].getBackground()==Color.green)
            {
                win=true;
            }

        }
        System.out.println(row0);
        if(win)
        {
            System.out.println("You Win!");

            scoreCounter = scoreCounter + (6-row0) * 5 * (100 - timeCounter) * 10;
            scoreLbl.setText("Score: " + scoreCounter);
            winCounter++;
            win2 = true;
            repaint();

            if(scoreCounter > highscoreCounter)
            {
                highscoreCounter = scoreCounter;
                highscoreLbl.setText("Highscore: " + highscoreCounter);
                JOptionPane.showMessageDialog(this, "Your final score is: " + scoreCounter, "You beat the high score!", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Your final score is: " + scoreCounter, "Game Finished Congrats!", JOptionPane.INFORMATION_MESSAGE);
            }
            saveStats();
            stopWatch.stop();
            dispose();
            new MainMenu();

        }

    }



    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {

            String keyChar = String.valueOf(e.getKeyChar());

            if(e.getKeyCode() == KeyEvent.VK_ENTER && column0 == 4 && !(wTP.charBoxs[row0][4].getText().equals("")))
            {
                String s = "";

                for(int i=0; i<5;i++)
                {
                    char ch = wTP.charBoxs[row0][i].getText().charAt(0);
                    s = s + ch;
                }



                if(dictionar.wordsList.contains(s.toLowerCase()))
                {
                    checkWord();
                    row0++;
                    column0 = 0;

                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Entered word not in our database");
                }
            }


             if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && column0 >= 0)
            {
                if (column0 == 4)
                {
                    wTP.charBoxs[row0][column0].setText("");
                    column0--;
                    flag1=false;
                }
                else if(column0 != 0 && flag1)
                {
                    column0--;wTP.charBoxs[row0][column0].setText("");
                }
                else if(!flag1)
                {
                    wTP.charBoxs[row0][column0].setText("");
                    flag1=true;
                }

            }


            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {

                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                        "Quit", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
                else if(result == JOptionPane.NO_OPTION)
                {

                }

                }


            else if(e.getKeyCode() == KeyEvent.VK_DELETE)
            {
                row0 = 0;
                column0 = 0;

            }
            else if(e.getKeyCode() == KeyEvent.VK_HOME)
            {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?",
                        "Restart", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION)
                {
                    dispose();
                    new WordleSpGUI();
                }
                else if(result == JOptionPane.NO_OPTION)
                {

                }
            }
            else if(e.getKeyCode()>=65 && e.getKeyCode()<91)
            {
                wTP.charBoxs[row0][column0].setText(keyChar.toUpperCase() + "");
                if(column0!=4){column0++;}

            }

            if (row0>=6)
            {
                    JOptionPane.showMessageDialog(null, "All Boxes Entered Try Again!");
                    dispose();
                    new MainMenu();
            }

            wTP.highlight(row0, column0);


        }

        @Override
        public void keyReleased(KeyEvent e) {
            // unnecessary.
        }

    };

    @Override
    public void mouseDragged(MouseEvent e)
    {
        draggingX = e.getX() -50;
        draggingY = e.getY() -30;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }

    public class KeyboardPanel extends JPanel implements MouseMotionListener
    {
        JButton[] keyboardButtons = new JButton[30];

        public KeyboardPanel()
        {
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

            for (int i = 0; i < 10; i++)
            {
                keyboardButtons[i] = new JButton("" + (char) (65 + i));
                keyboardButtons[i].setBackground(Color.darkGray);
                keyboardButtons[i].setForeground(Color.white);
                keyboardButtons[i].setBorder(boxBorder);
                keyboardButtons[i].setFont(new Font("", Font.PLAIN, 20));
                keyboardButtons[i].setFocusable(false);
                panelKeyboard0.add(keyboardButtons[i]);
            }

            for (int i = 10; i < 19; i++)
            {
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

            setBounds(250, 600, 1000, 200);

            add(panelKeyboard0);
            add(panelKeyboard1);
            add(panelKeyboard2);

            for (int i=0; i<27; i++)
            {
                keyboardButtons[i].addMouseListener(mouseListener);
            }
            for (int i=0; i<27; i++)
            {
                keyboardButtons[i].addMouseMotionListener(this);
            }


        }


        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    public MouseListener mouseListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {}

        String letter;
        @Override
        public void mousePressed(MouseEvent e){

        }


        @Override
        public void mouseReleased(MouseEvent e)
        {
            Object source = e.getSource();//gets which button was pressed

            letter = ((JButton) source).getText();


            if(letter.equals("Enter") && column0 == 4 && !(wTP.charBoxs[row0][4].getText().equals("")))
            {
                String s = "";

                for(int i=0; i<5;i++)
                {
                    char ch = wTP.charBoxs[row0][i].getText().charAt(0);
                    s = s + ch;
                }



                if(dictionar.wordsList.contains(s.toLowerCase()))
                {
                    checkWord();
                    row0++;
                    column0 = 0;

                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Entered word not in our database");
                }
            }


            else if(letter.equals("DEL") && column0 >= 0)
            {
                if(column0 == 4){wTP.charBoxs[row0][column0].setText("");column0--;flag1=false;}
                else if(column0 != 0 && flag1){column0--;wTP.charBoxs[row0][column0].setText("");}
                else if(!flag1){wTP.charBoxs[row0][column0].setText("");flag1=true;}

            }

            else if(!(letter.equals("Enter")))
            {
                wTP.charBoxs[row0][column0].setText(letter);
                if(column0!=4){column0++;}
            }


            if (row0>=6)
            {
                JOptionPane.showMessageDialog(null, "All Boxes Entered Try Again!");
                dispose();
                new MainMenu();

            }

            wTP.highlight(row0, column0);



        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    };

    WordleSpGUI()
    {
        int res = JOptionPane.showOptionDialog(null, "Welcome to NERDLE!", "NERDLE",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Keyboard", "Drag & Drop"}, null);


        if(res == 0)
        {

            title.setOpaque(true);
            title.setBackground(new Color(20,20,30));
            title.setForeground(Color.MAGENTA);
            title.setFont(new Font("", Font.PLAIN, 35));
            title.setHorizontalAlignment(JLabel.CENTER);
            title.setVerticalAlignment(JLabel.CENTER);
            title.setBounds(350,10,800,50);

            timeLbl.setOpaque(true);
            timeLbl.setBackground(new Color(20,20,30));
            timeLbl.setForeground(Color.WHITE);
            timeLbl.setFont(new Font("", Font.ITALIC, 20));
            timeLbl.setBounds(1200,60,300,30);

            scoreLbl.setOpaque(true);
            scoreLbl.setBackground(new Color(20,20,30));
            scoreLbl.setForeground(Color.WHITE);
            scoreLbl.setFont(new Font("", Font.ITALIC, 20));
            scoreLbl.setBounds(1200,100,300,30);

            highscoreLbl.setOpaque(true);
            highscoreLbl.setBackground(new Color(20,20,30));
            highscoreLbl.setForeground(Color.WHITE);
            highscoreLbl.setFont(new Font("", Font.ITALIC, 20));
            highscoreLbl.setBounds(1200,140,300,30);

            esclbl.setOpaque(true);
            esclbl.setBackground(new Color(20,20,30));
            esclbl.setForeground(Color.WHITE);
            esclbl.setFont(new Font("", Font.ITALIC, 20));
            esclbl.setBounds(50,180,300,30);

            homelbl.setOpaque(true);
            homelbl.setBackground(new Color(20,20,30));
            homelbl.setForeground(Color.WHITE);
            homelbl.setFont(new Font("", Font.ITALIC, 20));
            homelbl.setBounds(50,220,300,30);


            loadStats();

            this.setTitle("NERDLE Single Player");
            this.setLayout(null);
            // TODO: 4.06.2022 - Size'ı pc ekranı bilgilerini alarak yazılacak.
            this.setBounds(500,150,1500,927);

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.revalidate();

            this.getContentPane().setBackground(new Color(20,20,30));
            wTP.setBounds(300,125,850,700);

            this.add(wTP);

            this.addKeyListener(keyListener);

            this.add(timeLbl);
            this.add(scoreLbl);
            this.add(title);
            this.add(highscoreLbl);
            this.add(esclbl);
            this.add(homelbl);


            this.setVisible(true);

            startTimer();
        }
        if(res == 1)
        {
            title.setOpaque(true);
            title.setBackground(new Color(20,20,30));
            title.setForeground(Color.MAGENTA);
            title.setFont(new Font("", Font.PLAIN, 35));
            title.setHorizontalAlignment(JLabel.CENTER);
            title.setVerticalAlignment(JLabel.CENTER);
            title.setBounds(350,10,800,50);

            timeLbl.setOpaque(true);
            timeLbl.setBackground(new Color(20,20,30));
            timeLbl.setForeground(Color.WHITE);
            timeLbl.setFont(new Font("", Font.ITALIC, 20));
            timeLbl.setBounds(1200,60,300,30);

            scoreLbl.setOpaque(true);
            scoreLbl.setBackground(new Color(20,20,30));
            scoreLbl.setForeground(Color.WHITE);
            scoreLbl.setFont(new Font("", Font.ITALIC, 20));
            scoreLbl.setBounds(1200,100,300,30);

            highscoreLbl.setOpaque(true);
            highscoreLbl.setBackground(new Color(20,20,30));
            highscoreLbl.setForeground(Color.WHITE);
            highscoreLbl.setFont(new Font("", Font.ITALIC, 20));
            highscoreLbl.setBounds(1200,140,300,30);

            this.setTitle("NERDLE Single Player");
            this.setLayout(null);

            this.setBounds(500,150,1500,927);

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.revalidate();

            this.getContentPane().setBackground(new Color(20,20,30));

            loadStats();

            this.add(wTP);
            this.add(kP);

            for (int i=0; i<28; i++)
            {
                kP.keyboardButtons[i].addMouseListener(mouseListener);
            }
            this.addMouseMotionListener(this);

            this.add(timeLbl);
            this.add(scoreLbl);
            this.add(title);
            this.add(highscoreLbl);

            this.setVisible(true);

            startTimer();
        }


    }

}

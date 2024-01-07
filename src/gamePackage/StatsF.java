package gamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import static gamePackage.WordleSpGUI.highscoreCounter;

public class StatsF extends JFrame implements ActionListener
{

    public JLabel statsLabel = new JLabel();
    public JLabel profilelbl = new JLabel();
    public JLabel highscorelbl = new JLabel();
    public JLabel winlbl = new JLabel();

    public JButton backbtn = new JButton("BACK");

    public String getHighscore()
    {
        BufferedReader br = null;
        String line = "";
        File statsFile = new File("stats.txt");
        java.util.List<String> profileNameList = new ArrayList<String>();
        int i;

        try {
            profileNameList = Files.readAllLines(statsFile.toPath());


            for(i = 0; i < profileNameList.size(); i++)
            {
                if(profileNameList.get(i).contains(LoginFrame.loginName))
                {
                    String[] lineSplit = profileNameList.get(i).split("&");
                    if(lineSplit[0].equals(LoginFrame.loginName))
                    {
                        highscoreCounter = Integer.parseInt(lineSplit[1]);

                        WordleSpGUI.winCounter = Integer.parseInt(lineSplit[2]);

                    }

                    break;
                }
            }

            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/stats.txt"));
            line = br.readLine();
            br.close();


        } catch (IOException e) {
            line = "";
        }

        return String.valueOf(highscoreCounter);
    }

    public String getWinCount()
    {
        BufferedReader br = null;
        String line = "";
        File statsFile = new File("stats.txt");
        java.util.List<String> profileNameList = new ArrayList<String>();
        int i;

        try {
            profileNameList = Files.readAllLines(statsFile.toPath());


            for(i = 0; i < profileNameList.size(); i++)
            {
                if(profileNameList.get(i).contains(LoginFrame.loginName))
                {
                    String[] lineSplit = profileNameList.get(i).split("&");
                    if(lineSplit[0].equals(LoginFrame.loginName))
                    {

                        WordleSpGUI.winCounter = Integer.parseInt(lineSplit[2]);

                    }

                    break;
                }
            }

            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/stats.txt"));
            line = br.readLine();
            br.close();


        } catch (IOException e) {
            line = "";
        }
        return String.valueOf(WordleSpGUI.winCounter);
    }


    StatsF() throws FileNotFoundException
    {
        statsLabel.setText("Profile Stats: " );
        statsLabel.setFont(new Font("", Font.PLAIN, 25));
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setBounds(15, 10, 500, 100);
        add(statsLabel);

        profilelbl.setText("Profile Name: " + LoginFrame.loginName);
        profilelbl.setFont(new Font("", Font.PLAIN, 25));
        profilelbl.setForeground(Color.WHITE);
        profilelbl.setBounds(15, 120, 500, 100);
        add(profilelbl);

        highscorelbl.setText("Highscore: " + getHighscore());
        highscorelbl.setFont(new Font("", Font.PLAIN, 25));
        highscorelbl.setForeground(Color.WHITE);
        highscorelbl.setBounds(15, 230, 500, 100);
        add(highscorelbl);

        winlbl.setText("Win Count: " + getWinCount());
        winlbl.setFont(new Font("", Font.PLAIN, 25));
        winlbl.setForeground(Color.WHITE);
        winlbl.setBounds(15, 340, 300, 100);
        add(winlbl);

        backbtn.setBounds(400, 450, 100, 50);
        backbtn.setBackground(Color.BLACK);
        backbtn.setForeground(Color.WHITE);
        backbtn.addActionListener(this);
        backbtn.setFocusable(false);
        add(backbtn);

        this.setTitle("Stats");
        this.getContentPane().setBackground(new Color(20,20,30));
        this.getContentPane().setLayout(new BorderLayout());
        this.setBounds(600,100,800,600);
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);



    }

    public static void main(String[] args) throws FileNotFoundException {
        new StatsF();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backbtn)
        {
            this.dispose();
            new MainMenu();
        }
    }
}


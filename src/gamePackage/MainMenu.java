package gamePackage;

import onlinePackage.WordleMPGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainMenu extends JFrame implements ActionListener {

    JButton singlePlayerButton = new JButton("Single (SinglePlayer)");
    JButton multiPlayerButton = new JButton("I Have Some Friends (MultiPlayer)");
    JButton spectateButton = new JButton("I Watch Them (SideKickMode)");

    JButton statsButton = new JButton("Stats");
    JButton settingsButton = new JButton("Settings");
    JButton exitButton = new JButton("Exit");

    public MainMenu() {
        setLayout(null);
        // 0 frame'e yakın
        JPanel panel0 = new JPanel();
        JPanel panel1 = new JPanel();

        setBounds(270, 100, 0, 0);
        panel0.setBounds(450, 150, 600, 600);
        panel1.setBounds(0, 0, 62, 62);

        panel0.setBackground(Color.blue);
        panel1.setBackground(Color.white);

        JPanel panel2 = new JPanel();
        panel2.setBounds(550, 40, 400, 100);
        panel2.setBackground(Color.black);
        panel2.setForeground(Color.white);
        add(panel2, BorderLayout.NORTH);

        panel0.setLayout(new BorderLayout());

        ImageIcon singlePlayerButtonImage = new ImageIcon("singlePlayerButton.png");
        ImageIcon multiPlayerButtonImage = new ImageIcon("multiPlayerButton.png");
        ImageIcon spectatorButtonImage = new ImageIcon("spectateButton.png");
        ImageIcon statsButtonImage = new ImageIcon("statsButton.png");
        ImageIcon settingsButtonImage = new ImageIcon("settingsButton.png");
        ImageIcon exitButtonImage = new ImageIcon("exitButton.png");

        Image img0 = singlePlayerButtonImage.getImage(); // transform it
        Image newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        singlePlayerButtonImage = new ImageIcon(newimg); // transform it back

        img0 = multiPlayerButtonImage.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        multiPlayerButtonImage = new ImageIcon(newimg); // transform it back

        img0 = spectatorButtonImage.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        spectatorButtonImage = new ImageIcon(newimg); // transform it back

        img0 = statsButtonImage.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        statsButtonImage = new ImageIcon(newimg); // transform it back

        img0 = settingsButtonImage.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        settingsButtonImage = new ImageIcon(newimg); // transform it back

        img0 = exitButtonImage.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        exitButtonImage = new ImageIcon(newimg); // transform it back

        ImageIcon logo31 = new ImageIcon("logo0.png");

        img0 = logo31.getImage(); // transform it
        newimg = img0.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way
        logo31 = new ImageIcon(newimg); // transform it back
        // bozuq
        JLabel label0 = new JLabel("NERDLE");
        label0.setFont(new Font("", Font.PLAIN, 40));
        label0.setForeground(Color.WHITE);
        label0.setIcon(logo31);
        panel2.add(label0, BorderLayout.NORTH);

        singlePlayerButton.setBackground(Color.darkGray);
        singlePlayerButton.setForeground(Color.white);
        singlePlayerButton.setFocusable(false);
        singlePlayerButton.setIcon(singlePlayerButtonImage);
        singlePlayerButton.addActionListener(this);

        multiPlayerButton.setBackground(Color.darkGray);
        multiPlayerButton.setForeground(Color.white);
        multiPlayerButton.setFocusable(false);
        multiPlayerButton.setIcon(multiPlayerButtonImage);
        multiPlayerButton.addActionListener(this);

        spectateButton.setBackground(Color.darkGray);
        spectateButton.setForeground(Color.white);
        spectateButton.setFocusable(false);
        spectateButton.setIcon(spectatorButtonImage);
        spectateButton.addActionListener(this);

        statsButton.setBackground(Color.darkGray);
        statsButton.setForeground(Color.white);
        statsButton.setFocusable(false);
        statsButton.setIcon(statsButtonImage);
        statsButton.addActionListener(this);

        settingsButton.setBackground(Color.darkGray);
        settingsButton.setForeground(Color.white);
        settingsButton.setFocusable(false);
        settingsButton.setIcon(settingsButtonImage);
        settingsButton.addActionListener(this);

        exitButton.setBackground(Color.darkGray);
        exitButton.setForeground(Color.white);
        exitButton.setFocusable(false);
        exitButton.setIcon(exitButtonImage);
        exitButton.addActionListener(this);

        panel1.setLayout(new GridLayout(6, 1, 0, 2));

        panel1.add(singlePlayerButton);
        panel1.add(multiPlayerButton);
        panel1.add(spectateButton);

        panel1.add(statsButton);
        panel1.add(settingsButton);
        panel1.add(exitButton);

        Border border0 = BorderFactory.createLineBorder(Color.white, 3);
        panel1.setBorder(border0);

        panel0.add(panel1, BorderLayout.CENTER);
        add(panel0);

        this.setVisible(true);
        this.setSize(1500, 927);
        this.setTitle("NERDLE");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image0 = new ImageIcon("logo0.png");

        setIconImage(image0.getImage());
        getContentPane().setBackground(new Color(0, 0, 0));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == singlePlayerButton) {
            this.dispose();
            new WordleSpGUI();
        } else if (source == multiPlayerButton) {
            this.dispose();
            WordleMPGUI wordleMPGUI0 = new WordleMPGUI(false);
            ExecutorService exec0 = Executors.newCachedThreadPool();
            exec0.execute(wordleMPGUI0);
        } else if (source == spectateButton) {
            WordleMPGUI wordleMPGUI1 = new WordleMPGUI(true);
            ExecutorService exec0 = Executors.newCachedThreadPool();
            exec0.execute(wordleMPGUI1);
        } else if (source == statsButton) {

            try {
                dispose();
                new StatsF();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == settingsButton) {
            JOptionPane.showMessageDialog(null, "This feature is not available yet");
            // new Settings();
        } else if (source == exitButton) {
            System.exit(0);
        }

    }

}

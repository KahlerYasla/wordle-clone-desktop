package gamePackage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WordTablePanel extends JPanel
{
    public JLabel[][] charBoxs = new JLabel[6][5];

    Border borderF = BorderFactory.createLineBorder(Color.ORANGE, 5);
    Border boxBorder = BorderFactory.createLineBorder(Color.BLACK);
    public void highlight(int a, int b)
    {
        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                charBoxs[i][j].setBorder(boxBorder);
            }
        }
        charBoxs[a][b].setBorder(borderF);
    }

    public WordTablePanel()
    {

        this.setBackground(new Color(20,20,30));

        setLayout(new GridLayout(6, 5));

        for(int row=0;row<6;row++)
        {
            for(int column=0;column<5;column++)
            {
                charBoxs[row][column] = new JLabel();
                charBoxs[row][column].setHorizontalAlignment(JLabel.CENTER);
                charBoxs[row][column].setOpaque(true);
                charBoxs[row][column].setBackground(Color.WHITE);
                charBoxs[row][column].setBorder(boxBorder);
                charBoxs[row][column].setFont(new Font("", Font.PLAIN, 25));
                charBoxs[row][column].setForeground(Color.BLACK);
                charBoxs[row][column].setText("");
                add(charBoxs[row][column]);
            }
        }
        setBounds(450,100,600,400);
        setVisible(true);

    }



}
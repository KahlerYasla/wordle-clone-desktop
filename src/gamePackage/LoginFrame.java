package gamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public  class LoginFrame extends JFrame implements ActionListener {

    JCheckBox termsAndConditions = new JCheckBox("I accept terms and conditions");
    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JTextField userTextField = new JTextField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");

    public static String loginName;


    LoginFrame()
    {

        this.setTitle("Login Form");
        this.setVisible(true);
        this.setBounds(750, 150, 370, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager()
    {
        container.setLayout(null);
    }

    public void setLocationAndSize()
    {
        userLabel.setBounds(50, 150, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);
        termsAndConditions.setBounds(10, 350,300,100);
        userLabel.setBackground(Color.green);
        userTextField.setBackground(Color.white);
        userTextField.setForeground (Color.black);
        userLabel.setForeground (Color.black);


    }

    public void addComponentsToContainer()
    {
        container.add(userLabel);
        container.add(userTextField);
        container.add(loginButton);
        container.add(resetButton);
        container.add(termsAndConditions);
    }

    public void addActionEvent()
    {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton)
        {
            JOptionPane.showMessageDialog(this, "Login Successful!  " + "Welcome to the NERDLE " + userTextField.getText());
            loginName = userTextField.getText();
            setVisible(false);
            new MainMenu();

        }
        //Coding Part of RESET button
        if (e.getSource() == resetButton)
        {
            userTextField.setText("");
        }

    }

}




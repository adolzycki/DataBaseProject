package Frames;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JPanel mainPanel;

    public LoginFrame() {
        super("Login Frame");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public void hideWindow() {
        setVisible(false);
    }
}

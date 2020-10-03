package com.wodder.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class LoginDialog extends JDialog implements KeyListener {

    private JTextField userName;
    private JPasswordField password;
    private JButton ok;

    public LoginDialog() {
        super((Dialog) null, "Login");
        someMethod();
    }

    private void someMethod() {
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.add(userName(), GuiUtils.createConstraints(0,0,1,1, GridBagConstraints.BOTH, GridBagConstraints.EAST));
        jPanel.add(password(), GuiUtils.createConstraints(0,1,1,1, GridBagConstraints.BOTH, GridBagConstraints.EAST));
        jPanel.add(okBtn(), GuiUtils.createConstraints(0,2,1,1, GridBagConstraints.BOTH, GridBagConstraints.CENTER));

        this.addKeyListener(this);
        this.setModal(true);
        this.add(jPanel);
    }

    private JPanel userName() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(new JLabel("Username:"));
        userName = new JTextField(30);
        userName.addKeyListener(this);
        userName.setFocusable(true);
        jPanel.add(userName);
        return jPanel;
    }

    private JPanel password() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(new JLabel("Password:"));
        password = new JPasswordField(30);
        password.addKeyListener(this);
        password.setFocusable(true);
        jPanel.add(password);
        return jPanel;
    }

    private JPanel okBtn() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ok = new JButton("OK");
        ok.addActionListener(btnListener);
        ok.setFocusable(true);
        jPanel.add(ok);
        return jPanel;
    }

    public void display() {
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public String getUserName() {
        return userName.getText();
    }

    public char[] getPassword() {
        return password.getPassword();
    }

    ActionListener btnListener = (e) -> {
        if (userName.getText().length() == 0 || password.getPassword().length == 0) {
            this.userName.setToolTipText("Enter username");
            ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(userName,0,0,0,0,0, 0,false));
        } else {
            this.setVisible(false);
        }
    };

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
           ok.doClick();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

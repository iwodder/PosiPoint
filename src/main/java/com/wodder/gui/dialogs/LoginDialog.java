package com.wodder.gui.dialogs;

import com.wodder.authentication.*;
import com.wodder.controllers.*;
import com.wodder.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog implements KeyListener {

    private JTextField userName;
    private JPasswordField password;
    private JButton ok;
    private Frame owner;
    private SystemController controller;

    public LoginDialog(Frame owner, SystemController controller) {
        super(owner, "Login");
        this.owner = owner;
        this.controller = controller;
        someMethod();
    }

    private void someMethod() {
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.add(userName(), GuiUtils.createConstraints(0,0,1,1, GridBagConstraints.BOTH, GridBagConstraints.EAST));
        jPanel.add(password(), GuiUtils.createConstraints(0,1,1,1, GridBagConstraints.BOTH, GridBagConstraints.EAST));
        jPanel.add(okBtn(), GuiUtils.createConstraints(0,2,1,1, GridBagConstraints.BOTH, GridBagConstraints.CENTER));

        this.setModal(false);
        this.addKeyListener(this);
        this.setLayout(new BorderLayout());
        this.add(jPanel, BorderLayout.CENTER);
        this.pack();
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
        ok.addKeyListener(this);
        ok.setFocusable(true);
        jPanel.add(ok);
        return jPanel;
    }

    public void display() {
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
        userName.grabFocus();
    }

    public String getUserName() {
        String result = userName.getText();
        userName.setText(null);
        return result;
    }

    public char[] getPassword() {
        char[] pw = password.getPassword();
        password.setText(null);
        return pw;
    }

    ActionListener btnListener = (e) -> {
        if (userName.getText().length() == 0 ) {
            userName.setToolTipText("Enter username");
            GuiUtils.displayToolTip(userName);
        } else if (password.getPassword().length == 0) {
            password.setToolTipText("Enter password");
            GuiUtils.displayToolTip(password);
        } else {
            controller.login(new Credentials(getUserName(), getPassword()));
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

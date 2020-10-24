package com.wodder.gui.dialogs;

import com.wodder.controllers.*;
import com.wodder.gui.*;
import org.apache.commons.lang3.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class CreateUserDialog extends JDialog implements KeyListener, ActionListener {

    private JTextField fName;
    private JTextField lName;
    private JPasswordField password;
    private JComboBox<String> role;
    private JButton okBtn;
    private JButton cancelBtn;
    private JPanel owner;
    private final SystemController controller;

    public CreateUserDialog(SystemController controller, JPanel owner) {
        this.owner = owner;
        this.controller = controller;
        createDialog();
        this.pack();
    }

    private void createDialog() {
        this.setTitle("Add new user");
        this.setLayout(new GridBagLayout());
        this.add(createName(), GuiUtils.createConstraints(0,0,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST));
        this.add(createPw(), GuiUtils.createConstraints(0,1,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST));
        this.add(btnPanel(), GuiUtils.createConstraints(0,2,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST));
    }

    private JPanel createName() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        result.add(new JLabel(StringUtils.leftPad("First Name:", 15)));
        fName = new JTextField(30);
        fName.setFocusable(true);
        result.add(fName);
        result.add(new JLabel(StringUtils.leftPad("Last Name:",15)));
        lName = new JTextField(30);
        lName.setFocusable(true);
        result.add(lName);
        return result;
    }

    private JPanel createPw() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        result.add(new JLabel(StringUtils.leftPad("Password:", 15)));
        password = new JPasswordField(30);
        password.setFocusable(true);
        result.add(password);
        result.add(new JLabel(StringUtils.leftPad("User Role:",15)));
        role = new JComboBox<>(createRoles());
        role.setFocusable(true);
        result.add(role);
        return result;
    }

    private String[] createRoles() {
        List<String> result = new ArrayList<>();
        controller.getUserRoles().forEachRemaining(result::add);
        return result.toArray(new String[]{});
    }

    public void display() {
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okBtn) {
            if (fName.getText().length() < 1) {
                fName.setToolTipText("Enter first name");
                GuiUtils.displayToolTip(fName);
            }
            if (lName.getText().length() < 1) {
                lName.setToolTipText("Enter last name");
                GuiUtils.displayToolTip(lName);
            }
            if (password.getPassword().length < 1) {
                password.setToolTipText("Enter a password");
                GuiUtils.displayToolTip(password);
            }
            controller.addUser(fName.getText(), lName.getText(), new String(password.getPassword()), (String) role.getSelectedItem());
        }
    }

    private JPanel btnPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okBtn = new JButton("OK");
        okBtn.addKeyListener(this);
        okBtn.addActionListener(this);
        jPanel.add(okBtn);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> this.setVisible(false));
        cancelBtn.addKeyListener(this);
        jPanel.add(cancelBtn);
        return jPanel;
    }

    @Override
    public void setVisible(boolean b) {
        fName.setText(null);
        lName.setText(null);
        password.setText(null);
        super.setVisible(b);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            if (cancelBtn.hasFocus()) {
                cancelBtn.doClick();
            } else {
                okBtn.doClick();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

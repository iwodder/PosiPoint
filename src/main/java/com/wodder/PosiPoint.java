package com.wodder;

import com.wodder.authentication.AccessManager;
import com.wodder.authentication.InMemory;
import com.wodder.model.users.User;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;


public class PosiPoint extends JFrame {

    private User currentUser;
    private static PosiPoint posiPoint;
    private static final AccessManager accessManager = new InMemory();
    static Timer t = new Timer("Logout Task", true);

    public static void main( String[] args ) {
        posiPoint = new PosiPoint();
        start();
    }

    PosiPoint() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("PosiPoint Sales System");
        this.setLocation(new Point(0,0));
        this.pack();
        this.setVisible(true);
    }

    private static void start() {
        try {
            posiPoint.currentUser = accessManager.login();
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startTimer() {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                logout();
            }
        }, 1000 * 30L);
    }

    public static void logout() {
        posiPoint.currentUser = null;
        displayLoggedOut();
    }

    private static void displayLoggedOut() {
        JDialog jDialog = new JDialog(posiPoint);
        jDialog.setModal(true);
        jDialog.setLayout(new BorderLayout());
        jDialog.add(new JLabel("You were logged out"), BorderLayout.CENTER);
        jDialog.pack();
        jDialog.setVisible(true);
    }
}

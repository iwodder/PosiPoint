package com.wodder.gui.dialogs;

import com.wodder.controllers.*;
import com.wodder.model.system.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class OrdersDialog extends JDialog implements ActionListener, KeyListener {

    private OrderController controller;
    private TableModel tableModel;
    private SystemModel systemModel;
    private JButton okBtn;
    private JButton cancelBtn;
    private Font tableFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
    private JTable orderTable;

    public OrdersDialog(TableModel tableModel, SystemModel model, OrderController controller) {
        super((Frame) null, "Pickup Order", true);
        this.tableModel = tableModel;
        this.controller = controller;
        this.systemModel = model;
    }

    public void display() {
        setLayout(new BorderLayout());
        orderTable = new JTable(tableModel);
        orderTable.setFont(tableFont);
        orderTable.setFillsViewportHeight(true);
        centerAllCells(orderTable);
        JScrollPane scrollPane = new JScrollPane(
                orderTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel(), BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void close() {
        setVisible(false);
        dispose();
    }

    private void centerAllCells(JTable jTable) {
        DefaultTableCellRenderer result = new DefaultTableCellRenderer();
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setHorizontalTextPosition(SwingConstants.CENTER);
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(result);
        }
    }

    private JPanel btnPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okBtn = new JButton("OK");
        okBtn.addKeyListener(this);
        okBtn.addActionListener(this);
        jPanel.add(okBtn);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener((e) -> close());
        cancelBtn.addKeyListener(this);
        jPanel.add(cancelBtn);
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okBtn) {
            int row = orderTable.getSelectedRow();
            Object data = orderTable.getModel().getValueAt(row, 0);
            if (data instanceof Integer) {
                int orderId = (Integer) data;
                controller.lookupOrder(systemModel.currentUser(), orderId);
                close();
            }
        }
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

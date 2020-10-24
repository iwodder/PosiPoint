package com.wodder.gui.order;

import com.wodder.controllers.*;
import com.wodder.gui.dialogs.*;
import com.wodder.gui.observers.*;
import com.wodder.model.order.*;
import com.wodder.model.system.*;
import org.apache.commons.lang3.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class OrderDisplay extends JPanel implements OrderObserver, MouseListener, ActionListener {

    private JLabel orderNum;
    private final OrderModel orderModel;
    private final OrderController controller;
    private JLabel total;
    private JList<String> orderList;
    private JButton createOrder;
    private JButton deleteOrder;
    private JButton saveOrder;
    private JButton getOrders;
    private final Font orderFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private SystemModel systemModel;

    public OrderDisplay(Controllers controllers) {
        super(new BorderLayout());
        controller = controllers.getOrderController();
        controller.setOrderDisplay(this);
        orderModel = controller.getOrderModel();
        orderModel.registerOrderObs(this);
        systemModel = controllers.getSystemController().getSystemModel();
    }

    public void createDisplay() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        add(createOrderLabel(), BorderLayout.NORTH);
        add(createOrderPanel(), BorderLayout.CENTER);
        add(orderTotalPanel(), BorderLayout.SOUTH);
    }

    private JPanel createOrderPanel() {
        JPanel result = new JPanel(new BorderLayout());
        orderList = new JList<>();
        orderList.setFont(orderFont);
        orderList.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        result.add(createOrderHeader(), BorderLayout.NORTH);
        result.add(orderList, BorderLayout.CENTER);
        return result;
    }

    private JTextArea createOrderHeader() {
        JTextArea orderHeader = new JTextArea(2, 75);
        orderHeader.setFont(orderFont);
        orderHeader.setEditable(false);
        addOrderHeader(orderHeader);
        return orderHeader;
    }

    private void addOrderHeader(JTextArea area) {
        area.append(StringUtils.center("Quantity", 15));
        area.append(StringUtils.center("Items", 60));
        area.append("\n");
        area.append(StringUtils.repeat("=", 75));
    }

    private JPanel createOrderLabel() {
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        JLabel orderLabel = new JLabel("ORDER", JLabel.CENTER);
        orderLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        result.add(orderLabel);
        result.add(currentOrderLabel());
        return result;
    }

    private JPanel currentOrderLabel() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.CENTER));
        result.add(new JLabel("Current Order: "));
        orderNum = new JLabel("");
        result.add(orderNum);
        return result;
    }

    private JPanel orderTotalPanel() {
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        result.setAlignmentX(Component.LEFT_ALIGNMENT);
        result.add(total());
        return result;
    }

    private JPanel total() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        result.add(new JLabel("Order total: "));
        result.setFont(orderFont);
        total = new JLabel();
        total.setFont(orderFont);
        result.add(total);
        return result;
    }

    public JPanel orderControls() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createOrder = new JButton("New Order");
        createOrder.addActionListener(this);
        result.add(createOrder);
        saveOrder = new JButton("Save Order");
        saveOrder.addActionListener(this);
        result.add(saveOrder);
        deleteOrder = new JButton("Delete Order");
        deleteOrder.addActionListener(this);
        result.add(deleteOrder);
        getOrders = new JButton("Pickup Order");
        getOrders.addActionListener(this);
        result.add(getOrders);
        disableDeleteOrderBtn();
        disableSaveOrderBtn();
        return result;
    }

    @Override
    public void orderCreated() {
        orderNum.setText(String.valueOf(orderModel.getCurrentOrderId()));
        orderList.setListData(orderModel.getOrderList());
    }

    @Override
    public void itemAdded() {
        orderList.setListData(orderModel.getOrderList());
        total.setText(createTotalText(orderModel.getOrderTotal()));
    }

    private String createTotalText(String price) {
        return "$" + price;
    }

    public void resetDisplay() {
        orderNum.setText(null);
        orderList.setListData(new String[]{});
        total.setText(null);
    }

    public void showOrders(TableModel model) {
        new OrdersDialog( model, systemModel, controller).display();
    }

    @Override
    public void orderDeleted() {
        resetDisplay();
    }

    @Override
    public void activeOrderChanged() {
        orderNum.setText(String.valueOf(orderModel.getCurrentOrderId()));
        orderList.setListData(orderModel.getOrderList());
        total.setText(orderModel.getOrderTotal());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteOrder) {
            controller.deleteOrder(systemModel.currentUser(), Integer.parseInt(orderNum.getText()));
        }
        if (e.getSource() == createOrder) {
            controller.createOrder(systemModel.currentUser());
        }
        if (e.getSource() == saveOrder) {
            controller.saveOrder();
        }
        if (e.getSource() == getOrders) {
            controller.getOrders(systemModel.currentUser());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == orderList) {
            for (MouseListener m : this.getMouseListeners()) {
                m.mouseClicked(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void disableSaveOrderBtn() {
        saveOrder.setEnabled(false);
    }

    public void enableSaveOrderBtn() {
        saveOrder.setEnabled(true);
    }

    public void disableCreateOrderBtn() {
        createOrder.setEnabled(false);
    }

    public void enableCreateOrderBtn() {
        createOrder.setEnabled(true);
    }

    public void disableDeleteOrderBtn() {
        deleteOrder.setEnabled(false);
    }

    public void enableDeleteOrderBtn() {
        deleteOrder.setEnabled(true);
    }
}

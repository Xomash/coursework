package com.khomandiak.courseWork;

import java.awt.*;
import javax.swing.*;

public class SimpleGUI extends JFrame {
    private JTextArea label;
    private Container container;
    private JScrollPane scrollPane;

    public SimpleGUI() {
        super("References");
        this.setBounds(100,100,500,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container = this.getContentPane();
        container.setLayout(new GridLayout(1,1));

    }

    public static void main(String[] args) {
        SimpleGUI app = new SimpleGUI();
        app.setVisible(true);
    }

    public void setLabel(JTextArea label) {
        this.label = label;
        this.label.setEnabled(false);
        this.label.setDisabledTextColor(Color.BLACK);
        scrollPane = new JScrollPane(label);
        container.add(scrollPane, BorderLayout.CENTER);
    }
}
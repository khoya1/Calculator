package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GreedyCalculator {

    private JFrame frame;
    private JTextField display;
    private DefaultListModel<String> historyModel;

    private double first = 0;
    private String operator = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GreedyCalculator().createUI());
    }

    private void createUI() {

        frame = new JFrame("Advanced Calculator");
        frame.setBackground(new Color(251, 182, 170));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        //DISPLAY 
        display = new JTextField();
        display.setBackground(new Color(254, 244, 205));
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setPreferredSize(new Dimension(300, 50));
        frame.getContentPane().add(display, BorderLayout.NORTH);

        //BUTTON PANEL
        JPanel panel = new JPanel(new GridLayout(5, 4, 3, 3));
        panel.setBackground(new Color(254, 244, 205));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] buttons = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0",".","=","+",
                "C","BKSP","%",""
        };

        for (String text : buttons) {
            if (text.equals("")) continue;

            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));

            btn.addActionListener(e -> handleInput(text));
            panel.add(btn);
        }

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // HISTORY PANEL
        historyModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(historyModel);
        historyList.setBackground(new Color(254, 244, 205));
        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setPreferredSize(new Dimension(150, 0));
        frame.getContentPane().add(scroll, BorderLayout.EAST);

        // KEYBOARD SUPPORT (attach to display)
        display.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKey(e);
            }
        });

        // FINAL FRAME SETUP
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        display.requestFocusInWindow();
    }

    // INPUT HANDLING (BUTTONS)
    private void handleInput(String input) {

        if ("0123456789.".contains(input)) {
            display.setText(display.getText() + input);
        }

        else if ("+-*/%".contains(input)) {
            if (display.getText().isEmpty()) return;

            first = Double.parseDouble(display.getText());
            operator = input;
            display.setText("");
        }

        else if (input.equals("=")) {
            if (display.getText().isEmpty()) return;

            double second = Double.parseDouble(display.getText());
            double result = calculate(first, second);

            historyModel.addElement(first + " " + operator + " " + second + " = " + result);
            display.setText(String.valueOf(result));
        }

        else if (input.equals("C")) {
            display.setText("");
        }

        else if (input.equals("BKSP")) {
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    // KEYBOARD SUPPORT
    private void handleKey(KeyEvent e) {

        char key = e.getKeyChar();

        if (Character.isDigit(key) || key == '.') {
            handleInput(String.valueOf(key));
        }

        else if ("+-*/%".indexOf(key) >= 0) {
            handleInput(String.valueOf(key));
        }

        else if (key == '\n') {
            handleInput("=");
        }

        else if (key == '\b') {
            handleInput("BKSP");
        }
    }

    // CALCULATION LOGIC
    private double calculate(double a, double b) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "%": return a % b;
            default: return b;
        }
    }
}
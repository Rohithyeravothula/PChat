package ui;

import javax.swing.*;

public class gui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("client: anon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JButton button = new JButton("press");
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}

package university.management.system;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    About() {
        setTitle("About Us");
        setSize(700, 450); // Reduced height to minimize white space
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 240, 255)); // Light blue background
        setLayout(null);

        // Adding Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/about.jpg"));
        Image i2 = i1.getImage().getScaledInstance(270, 200, Image.SCALE_SMOOTH); // Proper scaling
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 40, 270, 200); // Adjusted position
        add(image);

        // Heading
        JLabel heading = new JLabel("<html><center>University<br>Management System</center></html>", SwingConstants.CENTER);
        heading.setBounds(50, 20, 350, 80);
        heading.setFont(new Font("Serif", Font.BOLD, 28));
        heading.setForeground(new Color(15, 50, 100));
        add(heading);

        // Developer Names Section
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 5, 5));
        panel.setBounds(50, 130, 320, 160);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Developed By", 0, 0, new Font("Serif", Font.BOLD, 20)));

        JLabel name1 = new JLabel("Aaryan", SwingConstants.CENTER);
        JLabel name2 = new JLabel("Sakshi", SwingConstants.CENTER);
        JLabel name3 = new JLabel("Prejval Yadav", SwingConstants.CENTER);
        JLabel name4 = new JLabel("Yaswanth Reddy", SwingConstants.CENTER);

        Font nameFont = new Font("Arial", Font.BOLD, 18);
        name1.setFont(nameFont);
        name2.setFont(nameFont);
        name3.setFont(nameFont);
        name4.setFont(nameFont);

        panel.add(name1);
        panel.add(name2);
        panel.add(name3);
        panel.add(name4);
        
        add(panel);

        // Footer (Thank You Note)
        JLabel thankYou = new JLabel("JAVA Project | Thank You", SwingConstants.CENTER);
        thankYou.setBounds(100, 370, 500, 30);
        thankYou.setFont(new Font("Arial", Font.BOLD, 14));
        thankYou.setForeground(Color.DARK_GRAY);
        add(thankYou);

        setVisible(true);
    }

    public static void main(String[] args) {
        new About();
    }
}

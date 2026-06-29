package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel;
    JTextField tfusername;
    JPasswordField tfpassword;

    Login() {
        setTitle("Login - University Management System");
        getContentPane().setBackground(new Color(230, 240, 250)); // Light Blue Background
        setLayout(null);

        JLabel heading = new JLabel("Admin Login");
        heading.setBounds(120, 10, 300, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(50, 80, 100, 25);
        lblusername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(160, 80, 200, 30);
        tfusername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(50, 130, 100, 25);
        lblpassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(160, 130, 200, 30);
        tfpassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(tfpassword);

        login = new JButton("Login");
        login.setBounds(80, 200, 120, 35);
        login.setBackground(new Color(50, 150, 50)); // Green Button
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addActionListener(this);
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(220, 200, 120, 35);
        cancel.setBackground(new Color(200, 50, 50)); // Red Button
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);
        add(cancel);

        // Adding image on the right side
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(400, 50, 200, 200);
        add(image);

        setSize(650, 320);
        setLocation(500, 250);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            String query = "SELECT * FROM login WHERE username='" + username + "' AND password='" + password + "'";

            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    setVisible(false);
                    new Project();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                c.s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

package university.management.system;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class StudentDetails extends JFrame implements ActionListener {

    private Choice crollno;
    private JTable table;
    private JButton search, print, update, add, cancel;

    StudentDetails() {
        setTitle("Student Details");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE); // Simple white background

        // Heading
        JLabel heading = new JLabel("Search by Roll Number:");
        heading.setBounds(30, 20, 200, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(heading);

        // Dropdown for roll numbers
        crollno = new Choice();
        crollno.setBounds(220, 20, 150, 25);
        add(crollno);
        loadRollNumbers();

        // Table Setup
        table = new JTable();
        updateTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(30, 80, 820, 400);
        add(jsp);

        // Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 13));
        header.setBackground(new Color(200, 200, 200)); // Light gray header
        header.setForeground(Color.BLACK);

        // Buttons
        search = createButton("Search", 30, 500);
        print = createButton("Print", 150, 500);
        add = createButton("Add", 270, 500);
        update = createButton("Update", 390, 500);
        cancel = createButton("Cancel", 510, 500);

        setSize(900, 600);
        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 30);
        button.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button.setBackground(new Color(220, 220, 220)); // Light gray buttons
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.addActionListener(this);
        add(button);
        return button;
    }

    private void loadRollNumbers() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            crollno.removeAll();
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading roll numbers!");
            e.printStackTrace();
        }
    }

    private void updateTable() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM student");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating table!");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM student WHERE rollno = '" + crollno.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error in Search!");
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while printing!");
                e.printStackTrace();
            }
        } else if (ae.getSource() == add) {
            new AddStudent();
            loadRollNumbers();
            updateTable();
        } else if (ae.getSource() == update) {
            new UpdateStudent();
            loadRollNumbers();
            updateTable();
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}

package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class StudentLeaveDetails extends JFrame implements ActionListener {

    private Choice crollno;
    private JTable table;
    private JButton search, print, cancel;

    public StudentLeaveDetails() {
        setTitle("Student Leave Details");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel with Title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 100, 180));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel headerLabel = new JLabel("Student Leave Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Control Panel for Search and Action Buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        controlPanel.setBackground(new Color(245, 245, 250));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel searchLabel = new JLabel("Roll Number:");
        searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        controlPanel.add(searchLabel);

        crollno = new Choice();
        crollno.setFont(new Font("Tahoma", Font.PLAIN, 16));
        crollno.setPreferredSize(new Dimension(150, 30));
        controlPanel.add(crollno);

        // Load roll numbers from the database
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
            rs.close();
            c.s.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        search = new JButton("Search");
        search.setPreferredSize(new Dimension(120, 30));
        search.setFont(new Font("Tahoma", Font.BOLD, 14));
        search.setBackground(new Color(0, 153, 0));
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        controlPanel.add(search);

        print = new JButton("Print");
        print.setPreferredSize(new Dimension(120, 30));
        print.setFont(new Font("Tahoma", Font.BOLD, 14));
        print.setBackground(new Color(0, 102, 204));
        print.setForeground(Color.WHITE);
        print.addActionListener(this);
        controlPanel.add(print);

        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(100, 30));
        cancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        cancel.setBackground(new Color(204, 0, 0));
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        controlPanel.add(cancel);

        add(controlPanel, BorderLayout.CENTER);

        // Table Panel
        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(new Color(100, 150, 255));
        header.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scrollPane.setPreferredSize(new Dimension(900, 500));
        add(scrollPane, BorderLayout.SOUTH);

        // Initially load attendance data
        loadAttendanceData();

        setVisible(true);
    }

    // Function to Load Attendance Data from Database
    private void loadAttendanceData() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM studentleave";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
            c.s.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM studentleave WHERE rollno = '" + crollno.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
                rs.close();
                c.s.close();
                c.c.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Printing Error!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeaveDetails();
    }
}

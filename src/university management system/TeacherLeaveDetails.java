package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class TeacherLeaveDetails extends JFrame implements ActionListener {

    private Choice cEmpId;
    private JTable table;
    private JButton search, print, cancel;

    public TeacherLeaveDetails() {
        setTitle("Teacher Leave Details");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 100, 180));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel headerLabel = new JLabel("Search Teacher Leave Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Control Panel (for search and buttons)
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(245, 245, 250));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        
        JLabel searchLabel = new JLabel("Employee ID:");
        searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        controlPanel.add(searchLabel);

        cEmpId = new Choice();
        cEmpId.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cEmpId.setPreferredSize(new Dimension(150, 30));
        controlPanel.add(cEmpId);

        // Load Employee IDs from database
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empId FROM teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
            rs.close();
            c.s.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching Employee IDs!", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        search = new JButton("Search");
        search.setPreferredSize(new Dimension(100, 30));
        search.setFont(new Font("Tahoma", Font.BOLD, 14));
        search.setBackground(new Color(0, 153, 0));
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        controlPanel.add(search);

        print = new JButton("Print");
        print.setPreferredSize(new Dimension(100, 30));
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
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        tableHeader.setBackground(new Color(100, 150, 255));
        tableHeader.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 2));
        scrollPane.setPreferredSize(new Dimension(900, 500));
        add(scrollPane, BorderLayout.SOUTH);

        // Initially load data into table
        loadAttendanceData();

        setVisible(true);
    }

    // Function to Load Attendance Data
    private void loadAttendanceData() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM teacherleave";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
            c.s.close();
            c.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM teacherleave WHERE empId = '" + cEmpId.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
                rs.close();
                c.s.close();
                c.c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeaveDetails();
    }
}

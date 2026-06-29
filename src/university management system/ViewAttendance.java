package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewAttendance extends JFrame {

    JTable table;
    DefaultTableModel model;

    ViewAttendance() {
        setTitle("View Attendance Records");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with padding and background
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header label
        JLabel headerLabel = new JLabel("Attendance Records", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 150));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table columns and model
        String[] columnNames = {"ID", "Person ID", "Person Type", "Course", "Branch", "Date", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);

        // Customize header appearance
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(new Color(100, 150, 255));
        header.setForeground(Color.WHITE);

        // Alternate row coloring
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 230, 250) : Color.WHITE);
                }
                return c;
            }
        });

        // Scroll pane with a border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Footer panel with Close button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 245, 250));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setBackground(new Color(220, 50, 50));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> setVisible(false));
        footerPanel.add(closeButton);
        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);

        // Load attendance data
        loadAttendanceData();

        setVisible(true);
    }

    // Function to load attendance data from the database
    private void loadAttendanceData() {
        try {
            Conn con = new Conn();
            String query = "SELECT * FROM attendance";
            ResultSet rs = con.s.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String person_id = rs.getString("person_id");
                String person_type = rs.getString("person_type");
                String course = rs.getString("course");
                String branch = rs.getString("branch");
                String date = rs.getString("date");
                String status = rs.getString("status");

                model.addRow(new Object[]{id, person_id, person_type, course, branch, date, status});
            }
            rs.close();
            con.s.close();
            con.c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewAttendance();
    }
}
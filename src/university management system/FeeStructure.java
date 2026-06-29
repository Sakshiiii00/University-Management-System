package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FeeStructure extends JFrame {

    FeeStructure() {
        setTitle("Fee Structure");
        setSize(900, 500);
        setLocation(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(240, 245, 250)); // Light Background
        
        // Heading Panel
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(50, 50, 150));
        headingPanel.setPreferredSize(new Dimension(900, 60));
        
        JLabel heading = new JLabel("Fee Structure");
        heading.setFont(new Font("Serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        headingPanel.add(heading);
        
        add(headingPanel, BorderLayout.NORTH);
        
        // Table Setup
        JTable table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(100, 150, 255)); // Soft Blue Header
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(173, 216, 230)); // Light Blue Selection
        
        // Fetch Data
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM fee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Auto-adjust column width
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Scroll Pane for Table
        JScrollPane jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jsp, BorderLayout.CENTER);
        
        // Footer Panel with Close Button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 245, 250));
        footerPanel.setPreferredSize(new Dimension(900, 50));
        
        JButton close = new JButton("Close");
        close.setFont(new Font("Tahoma", Font.BOLD, 15));
        close.setBackground(new Color(220, 50, 50)); // Red Button
        close.setForeground(Color.WHITE);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> dispose());
        
        footerPanel.add(close);
        add(footerPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new FeeStructure();
    }
}

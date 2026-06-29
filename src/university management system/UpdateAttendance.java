package university.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateAttendance extends JFrame implements ActionListener {

    private JComboBox<String> userType;
    private JTextField tfId;
    // Display details (non-editable) - now showing person name instead of ID
    private JLabel lblPersonName, lblPersonType, lblCourse, lblBranch, lblRecordDate;
    // Editable field for status update
    private JRadioButton rbPresent, rbAbsent;
    private ButtonGroup statusGroup;
    private JButton btnSearch, btnUpdate, btnCancel;
    
    public UpdateAttendance() {
        setTitle("Update Attendance");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50,100,180));
        headerPanel.setPreferredSize(new Dimension(500,60));
        JLabel headerLabel = new JLabel("Update Attendance", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel using GridBagLayout for a clean layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // User Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Select Type:"), gbc);
        gbc.gridx = 1;
        userType = new JComboBox<>(new String[]{"Student", "Teacher"});
        formPanel.add(userType, gbc);
        
        // ID Field (Roll No or Emp ID)
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Enter ID:"), gbc);
        gbc.gridx = 1;
        tfId = new JTextField();
        formPanel.add(tfId, gbc);
        
        // Search Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        formPanel.add(btnSearch, gbc);
        
        // Display record details (non-editable)
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        lblPersonName = new JLabel("-");
        formPanel.add(lblPersonName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        lblPersonType = new JLabel("-");
        formPanel.add(lblPersonType, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        lblCourse = new JLabel("-");
        formPanel.add(lblCourse, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Branch:"), gbc);
        gbc.gridx = 1;
        lblBranch = new JLabel("-");
        formPanel.add(lblBranch, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        // Set current date as record date
        lblRecordDate = new JLabel(java.time.LocalDate.now().toString());
        formPanel.add(lblRecordDate, gbc);
        
        // Editable field for status update
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        rbPresent = new JRadioButton("Present");
        rbAbsent = new JRadioButton("Absent");
        statusGroup = new ButtonGroup();
        statusGroup.add(rbPresent);
        statusGroup.add(rbAbsent);
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(rbPresent);
        statusPanel.add(rbAbsent);
        formPanel.add(statusPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnUpdate.setBackground(new Color(0,153,0));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(this);
        buttonPanel.add(btnUpdate);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancel.setBackground(new Color(204,0,0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnSearch) {
            String id = tfId.getText().trim();
            String type = (String) userType.getSelectedItem();
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter an ID.");
                return;
            }
            try {
                Conn con = new Conn();
                String query;
                if(type.equals("Student")){
                    // Now retrieving rollno, name, course, and branch
                    query = "SELECT rollno, name, course, branch FROM student WHERE rollno = ?";
                } else {
                    // For teacher, retrieve empid, name, course, and branch
                    query = "SELECT empid, name, course, branch FROM teacher WHERE empid = ?";
                }
                PreparedStatement pstmt = con.c.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    // Show person's name instead of ID in the label
                    lblPersonName.setText(rs.getString("name"));
                    lblPersonType.setText(type);
                    lblCourse.setText(rs.getString("course"));
                    lblBranch.setText(rs.getString("branch"));
                    // Optionally, set the record date to today
                    lblRecordDate.setText(java.time.LocalDate.now().toString());
                } else {
                    JOptionPane.showMessageDialog(this, "No " + type + " found with this ID.");
                }
                rs.close();
                pstmt.close();
                con.c.close();
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            }
        } else if(ae.getSource() == btnUpdate) {
            String id = tfId.getText().trim();
            String newStatus = rbPresent.isSelected() ? "Present" : rbAbsent.isSelected() ? "Absent" : "";
            if(id.isEmpty() || newStatus.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter an ID and select a new status.");
                return;
            }
            try {
                Conn con = new Conn();
                String query = "UPDATE attendance SET status = ? WHERE person_id = ? AND date = CURDATE()";
                PreparedStatement pstmt = con.c.prepareStatement(query);
                pstmt.setString(1, newStatus);
                pstmt.setString(2, id);
                int rowsUpdated = pstmt.executeUpdate();
                pstmt.close();
                con.c.close();
                if(rowsUpdated > 0){
                    JOptionPane.showMessageDialog(this, "Attendance updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed. Ensure an attendance record exists for today.");
                }
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating attendance: " + e.getMessage());
            }
        } else if(ae.getSource() == btnCancel) {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new UpdateAttendance();
    }
}

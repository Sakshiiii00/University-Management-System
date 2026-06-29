package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TeacherLeave extends JFrame implements ActionListener {
    Choice cEmpId;
    JDateChooser dcFromDate, dcToDate;
    JTextField tfReason;
    JButton submit, cancel;

    TeacherLeave() {
        setTitle("Teacher Leave Application");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Heading
        JLabel heading = new JLabel("Apply Leave (Teacher)", JLabel.CENTER);
        heading.setBounds(50, 20, 350, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(new Color(0, 102, 204));
        add(heading);

        // Employee ID Label
        JLabel lblEmpId = new JLabel("Employee ID:");
        lblEmpId.setBounds(50, 80, 120, 25);
        lblEmpId.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblEmpId);

        // Employee ID Dropdown
        cEmpId = new Choice();
        cEmpId.setBounds(200, 80, 180, 25);
        add(cEmpId);

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

        // Leave From Date Label and Picker
        JLabel lblFrom = new JLabel("Leave From:");
        lblFrom.setBounds(50, 130, 120, 25);
        lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblFrom);

        dcFromDate = new JDateChooser();
        dcFromDate.setBounds(200, 130, 180, 25);
        dcFromDate.setDateFormatString("yyyy-MM-dd");
        dcFromDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(dcFromDate);

        // Leave Upto Date Label and Picker
        JLabel lblTo = new JLabel("Leave To:");
        lblTo.setBounds(50, 180, 120, 25);
        lblTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblTo);

        dcToDate = new JDateChooser();
        dcToDate.setBounds(200, 180, 180, 25);
        dcToDate.setDateFormatString("yyyy-MM-dd");
        dcToDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(dcToDate);

        // Reason Label and Text Field
        JLabel lblReason = new JLabel("Reason:");
        lblReason.setBounds(50, 230, 120, 25);
        lblReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblReason);

        tfReason = new JTextField();
        tfReason.setBounds(200, 230, 180, 25);
        add(tfReason);

        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(90, 320, 120, 35);
        submit.setBackground(new Color(0, 153, 0));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 16));
        submit.addActionListener(this);
        add(submit);

        // Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds(230, 320, 120, 35);
        cancel.setBackground(new Color(204, 0, 0));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String empId = cEmpId.getSelectedItem();
            String fromDate = ((JTextField) dcFromDate.getDateEditor().getUiComponent()).getText();
            String toDate = ((JTextField) dcToDate.getDateEditor().getUiComponent()).getText();
            String reason = tfReason.getText().trim();
            String duration = "";

            if (fromDate.isEmpty() || toDate.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If from and to dates are the same, ask for full day or half day.
            if (fromDate.equals(toDate)) {
                Object[] options = {"Full Day", "Half Day"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Select leave type for the day:",
                        "Leave Type",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (choice == 0) {
                    duration = "Full Day";
                } else if (choice == 1) {
                    duration = "Half Day";
                } else {
                    return;
                }
            } else {
                duration = "Multiple Days";
            }

            try {
                Conn c = new Conn();
                String query = "INSERT INTO teacherleave (empId, from_date, to_date, duration, reason) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = c.c.prepareStatement(query);
                pstmt.setString(1, empId);
                pstmt.setString(2, fromDate);
                pstmt.setString(3, toDate);
                pstmt.setString(4, duration);
                pstmt.setString(5, reason);
                pstmt.executeUpdate();
                pstmt.close();
                c.c.close();

                JOptionPane.showMessageDialog(this, "Leave Applied Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Optionally clear fields for new entry:
                tfReason.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error submitting leave request!", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeave();
    }
}

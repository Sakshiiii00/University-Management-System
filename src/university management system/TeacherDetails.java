package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class TeacherDetails extends JFrame implements ActionListener {

    Choice cEmpId;
    JTable table;
    JButton search, print, update, add, cancel;

    TeacherDetails() {
        setTitle("Teacher Details");
        setSize(900, 700);
        setLocation(300, 100);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Heading Label
        JLabel heading = new JLabel("Search by Employee ID:");
        heading.setBounds(50, 20, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(heading);

        // Employee ID Choice Box
        cEmpId = new Choice();
        cEmpId.setBounds(250, 20, 200, 25);
        add(cEmpId);

        // Fetch Employee IDs from Database
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empId FROM teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching Employee IDs!", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Buttons Section
        search = new JButton("Search");
        search.setBounds(500, 18, 100, 30);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(620, 18, 100, 30);
        print.addActionListener(this);
        add(print);

        add = new JButton("Add");
        add.setBounds(50, 70, 120, 30);
        add.addActionListener(this);
        add(add);

        update = new JButton("Update");
        update.setBounds(190, 70, 120, 30);
        update.addActionListener(this);
        add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(330, 70, 120, 30);
        cancel.addActionListener(this);
        add(cancel);

        // Table Section
        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);

        // Fetch Data into Table
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM teacher");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data!", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Scroll Pane for Table
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(30, 120, 840, 500);
        add(jsp);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM teacher WHERE empId = '" + cEmpId.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
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
        } else if (ae.getSource() == add) {
            setVisible(false);
            new AddTeacher();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateTeacher();
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherDetails();
    }
}

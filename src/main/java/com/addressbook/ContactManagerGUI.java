package com.addressbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ContactManagerGUI extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTable contactTable;
    private DefaultTableModel tableModel;
    private ContactDAO contactDAO;
    private int selectedContactId = -1;

    public ContactManagerGUI() {
        super("Adressbuch");
        contactDAO = new ContactDAO();

        // Eingabefelder
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("E-Mail:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Telefon:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        JButton saveButton = new JButton("Speichern");
        JButton deleteButton = new JButton("Löschen");
        inputPanel.add(saveButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // Tabelle
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "E-Mail", "Telefon"}, 0);
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.getSelectionModel().addListSelectionListener(e -> fillFieldsFromTable());
        add(new JScrollPane(contactTable), BorderLayout.CENTER);

        // Buttons Aktionen
        saveButton.addActionListener(e -> saveContact());
        deleteButton.addActionListener(e -> deleteContact());

        // Fenster-Einstellungen
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Contact> contacts = contactDAO.getAllContacts();
        for (Contact c : contacts) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getEmail(), c.getPhone()});
        }
    }

    private void fillFieldsFromTable() {
        int row = contactTable.getSelectedRow();
        if (row >= 0) {
            selectedContactId = (int) tableModel.getValueAt(row, 0);
            nameField.setText((String) tableModel.getValueAt(row, 1));
            emailField.setText((String) tableModel.getValueAt(row, 2));
            phoneField.setText((String) tableModel.getValueAt(row, 3));
        }
    }

    private void saveContact() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name darf nicht leer sein.");
            return;
        }

        if (selectedContactId == -1) {
            contactDAO.addContact(new Contact(name, email, phone));
        } else {
            Contact updated = new Contact(selectedContactId, name, email, phone);
            contactDAO.updateContact(updated);
        }

        clearFields();
        refreshTable();
    }

    private void deleteContact() {
        if (selectedContactId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Kontakt wirklich löschen?", "Bestätigen", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                contactDAO.deleteContact(selectedContactId);
                clearFields();
                refreshTable();
            }
        }
    }

    private void clearFields() {
        selectedContactId = -1;
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        contactTable.clearSelection();
    }
}

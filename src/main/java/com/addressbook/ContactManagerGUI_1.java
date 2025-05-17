package com.addressbook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactManagerGUI_1 extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTextArea outputArea;
    private ContactDAO contactDAO;

    public ContactManagerGUI_1() {
        super("Addressbook Manager");
        contactDAO = new ContactDAO();

        // Layout
        setLayout(new BorderLayout());

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

        JButton addButton = new JButton("Hinzufügen");
        inputPanel.add(addButton);

        JButton loadButton = new JButton("Alle anzeigen");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        // Ausgabe
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Event-Handler
        addButton.addActionListener(e -> addContact());
        loadButton.addActionListener(e -> loadContacts());

        // Fenster-Einstellungen
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name darf nicht leer sein.");
            return;
        }

        Contact contact = new Contact(name, email, phone);
        contactDAO.addContact(contact);

        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");

        loadContacts();
    }

    private void loadContacts() {
        List<Contact> contacts = contactDAO.getAllContacts();
        outputArea.setText("");
        for (Contact c : contacts) {
            outputArea.append(c.toString() + "\n");
        }
    }
    
}

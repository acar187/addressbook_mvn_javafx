package com.addressbook;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println("Classpath: " + System.getProperty("java.class.path"));
        DBUtil.initializeDatabase();
        //Contact contact = new Contact("Paul Anchor", "paul@gmail.com","6123456789");
        // Print the contact details    
        //System.out.println(contact);
        // Create a ContactDAO instance
        ContactDAO contactDAO = new ContactDAO();
        // Add the contact to the database
        //contactDAO.addContact(contact);
        // Retrieve all contacts from the database
        List<Contact> contacts = contactDAO.getAllContacts();
        // Print all contacts
        System.out.println("All contacts:");
        for (Contact c : contacts) {
            System.out.println(c);
        }

        // Update a contact
        //contactDAO.updateContact(new Contact(3, "John Doe3-Updated", "Doe3@gmail.com", "3876543210")); 
        // Retrieve the updated contact
        //Contact updatedContact = contactDAO.getContact(3);
        // Print the updated contact
        //System.out.println("Updated contact:");
        //System.out.println(updatedContact);
        
        // Delete a contact
        //contactDAO.deleteContact(4);
        // Retrieve all contacts after deletion
        contacts = contactDAO.getAllContacts();
        // Print all contacts after deletion
        System.out.println("All contacts after deletion:");
        for (Contact c : contacts) {
            System.out.println(c);
        }
        
        //new ContactManagerGUI_1();
        //new ContactManagerGUI();
        ContactManagerFX.launch(ContactManagerFX.class, args);
    }
}

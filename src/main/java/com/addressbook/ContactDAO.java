package com.addressbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public void addContact(Contact contact) {
        // Implementation to add a contact to the database
        String sql = "INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)";
        try (Connection connection = DBUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getEmail());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.executeUpdate();
            System.out.println("Contact added successfully: " + contact);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding contact: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<Contact> getAllContacts() {
        // Implementation to retrieve all contacts from the database
        String sql = "SELECT * FROM contacts";
        try (Connection connection = DBUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
            return contacts;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving contacts: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Contact getContact(int id) {
        // Implementation to retrieve a contact from the database
        String sql = "SELECT * FROM contacts WHERE id = ?";
        try (Connection connection = DBUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving contact: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateContact(Contact contact) {
        // Implementation to update a contact in the database
        String sql = "UPDATE contacts SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection connection = DBUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getEmail());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.setInt(4, contact.getId());
            preparedStatement.executeUpdate();
            System.out.println("Contact updated successfully: " + contact);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error updating contact: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        // Implementation to delete a contact from the database
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection connection = DBUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Contact deleted successfully with ID: " + id);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

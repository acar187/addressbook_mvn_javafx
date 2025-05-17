package com.addressbook;

//import javax.naming.ConfigurationException;

//import com.google.protobuf.Empty;

import javafx.application.Application;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ContactManagerFX extends Application{
    private TableView<Contact> table;
    private TextField nameField, emailField, phoneField, searchField;
    private ContactDAO contactDAO;
    private ObservableList<Contact> contactList;

    @Override
    public void start(Stage primaryStage){
        contactDAO = new ContactDAO();
        contactList = FXCollections.observableArrayList(contactDAO.getAllContacts());
        
        // FilteredList für die Suche
        FilteredList<Contact> filteredList = new FilteredList<>(contactList, p -> true);

    VBox root = new VBox(10);
    root.setPadding(new Insets(10));

    searchField = new TextField();
    searchField.setPromptText("Search for Name,  E-Mail or Phone");

        // Filter-Logik
    searchField.textProperty().addListener((obs, oldVal, newVal) -> {
        String filter = newVal.toLowerCase();
        filteredList.setPredicate(contact -> {
            if (filter.isEmpty()) return true;
            return contact.getName().toLowerCase().contains(filter)
                || contact.getEmail().toLowerCase().contains(filter)
                || contact.getPhone().toLowerCase().contains(filter);
        });
    });

    table = new TableView<>();
    
    TableColumn<Contact, Integer> idCol = new TableColumn<>("ID");
    idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
    TableColumn<Contact, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
    TableColumn<Contact, String> emailCol = new TableColumn<>("E-Mail");
    emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
    TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone");
    phoneCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));
    table.getColumns().addAll(idCol, nameCol, emailCol, phoneCol);
    table.setItems(contactList);
    table.setItems(filteredList);

    nameField = new TextField();
    nameField.setPromptText("Name");
    emailField = new TextField();
    emailField.setPromptText("E-Mail");
    phoneField = new TextField();
    phoneField.setPromptText("Phone");

    HBox fieldBox = new HBox(10, nameField, emailField, phoneField);

    Button addButton = new Button("Add");
    Button updateButton = new Button("Update");
    Button deleteButton = new Button("Delete");

    HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);

    root.getChildren().addAll(searchField,table, fieldBox, buttonBox);
    

    addButton.setOnAction(e -> addContact());
    updateButton.setOnAction(e -> updateContact());
    deleteButton.setOnAction(e -> deleteContact());

    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
        if (newSel != null) {
            nameField.setText(newSel.getName());
            emailField.setText(newSel.getEmail());
            phoneField.setText(newSel.getPhone());
        }
    });

    primaryStage.setTitle("Adressbuch (JavaFX)");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
    root.requestFocus();
  }

  private void refreshTable(){
    contactList.setAll(contactDAO.getAllContacts());
  }

  private void clearFields(){
    nameField.clear();
    emailField.clear();
    phoneField.clear();
    table.getSelectionModel().clearSelection();
  }

  private void addContact(){
    if (nameField.getText().isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warnung");
        alert.setHeaderText(null);
        alert.setContentText("Das Namensfeld darf nicht leer sein.");
        alert.showAndWait();
        return;
    }
        Contact c = new Contact(nameField.getText(), emailField.getText(), phoneField.getText());
        contactDAO.addContact(c);
        refreshTable();
        clearFields();
    
    
  }

   private void deleteContact(){
        Contact selected = table.getSelectionModel().getSelectedItem();
        if (selected != null){
            contactDAO.deleteContact(selected.getId());
            refreshTable();
            clearFields();
        }
   }

    private void updateContact(){
        Contact selected = table.getSelectionModel().getSelectedItem();
        if(selected != null){
            selected.setName(nameField.getText());
            selected.setEmail(emailField.getText());
            selected.setPhone(phoneField.getText());
            contactDAO.updateContact(selected);
            refreshTable();
            clearFields();
        }

    }


}

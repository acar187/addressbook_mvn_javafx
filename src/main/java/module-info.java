module com.addressbook {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.addressbook to javafx.fxml;
    exports com.addressbook;
}

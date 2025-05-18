module com.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires java.desktop; // Für Swing und AWT
    // ggf. weitere Module, falls benötigt

    opens com.addressbook to javafx.fxml;
    exports com.addressbook;
}

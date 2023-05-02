module com.example.labb5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.datatransfer;


    opens com.example.labb5 to javafx.fxml;
    exports com.example.labb5;
}
module com.example.progsp5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progsp5 to javafx.fxml;
    exports com.example.progsp5;
    exports com.example.progsp5.Controller;
    opens com.example.progsp5.Controller to javafx.fxml;
}
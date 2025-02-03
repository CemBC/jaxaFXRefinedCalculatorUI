module com.example.refinedcalculatorui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.refinedcalculatorui to javafx.fxml;
    exports com.example.refinedcalculatorui;
}
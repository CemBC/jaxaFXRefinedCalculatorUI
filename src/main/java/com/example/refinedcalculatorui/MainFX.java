package com.example.refinedcalculatorui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFX extends Application {
    protected static int REMAINING_RAW;
    protected static ObservableList<String> historyModel = FXCollections.observableArrayList();
    protected static Label dynamicReturnRateLabel = new Label("Return Rate: %15");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Refined Calculator");


        TabPane tabbedPane = new TabPane();
        tabbedPane.setStyle("-fx-background-color: #2E3440;");


        Tab calculatorTab = new Tab("1-Needed Raw Calculator", Tabs.createNeededRawCalculator());
        Tab craftTab = new Tab("2-Refine Result Calculator", Tabs.createRefineResult());
        Tab refineForStoneTab = new Tab("3-Refine for Stone", Tabs.createRefineResultForStone());
        Tab neededRawForStoneTab = new Tab("4-Needed Raw for Stone", Tabs.createNeededRawForStone());
        Tab historyTab = new Tab("5-Refined Calculator History", Tabs.createHistoryPanel());

        for (Tab tab : tabbedPane.getTabs()) {
            tab.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");
        }


        tabbedPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (oldTab != null) {
                oldTab.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");
            }
            if (newTab != null) {
                newTab.setStyle("-fx-background-color: #5E81AC; -fx-text-fill: #ECEFF4;");
            }
        });

        tabbedPane.getTabs().addAll(calculatorTab, craftTab, refineForStoneTab, neededRawForStoneTab, historyTab);

        Scene scene = new Scene(tabbedPane, 850, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
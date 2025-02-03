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
    private static int REMAINING_RAW;
    private static ObservableList<String> historyModel = FXCollections.observableArrayList();
    private static Label dynamicReturnRateLabel = new Label("Return Rate: %15");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Refined Calculator");


        TabPane tabbedPane = new TabPane();
        tabbedPane.setStyle("-fx-background-color: #2E3440;"); // Koyu arka plan


        Tab calculatorTab = new Tab("1-Needed Raw Calculator", createNeededRawCalculator());
        Tab craftTab = new Tab("2-Refine Result Calculator", createRefineResult());
        Tab refineForStoneTab = new Tab("3-Refine for Stone", createRefineResultForStone());
        Tab neededRawForStoneTab = new Tab("4-Needed Raw for Stone", createNeededRawForStone());
        Tab historyTab = new Tab("5-Refined Calculator History", createHistoryPanel());

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

    private GridPane createNeededRawCalculator() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #3B4252;");

        Label labelIstenilenDeger = new Label("Wanted Refined Value:");
        labelIstenilenDeger.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textIstenilenDeger = new TextField();
        textIstenilenDeger.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelReturnRate = new Label("Return Rate (%):");
        labelReturnRate.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textReturnRate = new TextField("15");
        textReturnRate.setEditable(false);
        textReturnRate.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelTier = new Label("Tier:");
        labelTier.setStyle("-fx-text-fill: #ECEFF4;");

        ComboBox<String> comboTier = new ComboBox<>(FXCollections.observableArrayList("1", "3", "4", "5", "6", "7", "8"));
        comboTier.setValue("1");
        comboTier.setStyle("-fx-background-color: #4C566A; -fx-font-weight: bold; -fx-text-fill: #ECEFF4;");

        CheckBox royalBuffCheck = new CheckBox("Royal Buff");
        royalBuffCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox focusCheck = new CheckBox("Focus");
        focusCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity10Check = new CheckBox("%10 Activity");
        activity10Check.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity20Check = new CheckBox("%20 Activity");
        activity20Check.setStyle("-fx-text-fill: #ECEFF4;");

        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #e09d16; -fx-text-fill: #ECEFF4;");

        Label resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-text-fill: #ECEFF4;");

        royalBuffCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int istenilenDeger = Integer.parseInt(textIstenilenDeger.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int bolunecekSayi = getBolunecekSayi(selectedTier);

                int elimizdekiRaw = istenilenDeger * bolunecekSayi;
                int sonuc = 0;

                for (int i = elimizdekiRaw; i >= bolunecekSayi; i--) {
                    int testValue = mainFunction(i, returnRate, bolunecekSayi);
                    if (selectedTier.equals("1")) {
                        if (testValue < istenilenDeger) {
                            sonuc = i + 1;
                            break;
                        }
                    } else {
                        if (testValue == istenilenDeger) {
                            sonuc = i;
                            break;
                        }
                    }
                }
                String sonucLabel = selectedTier.equals("1") ? "Alt Tier" : "Raw";
                resultLabel.setText("Result: Needed " + sonucLabel + " = " + sonuc);

            } catch (NumberFormatException ex) {
                showAlert("Please enter valid value", "Error");
            } catch (IllegalArgumentException ex) {
                showAlert(ex.getMessage(), "Error");
            }
        });

        grid.add(labelIstenilenDeger, 0, 0);
        grid.add(textIstenilenDeger, 1, 0);
        grid.add(labelReturnRate, 0, 1);
        grid.add(textReturnRate, 1, 1);
        grid.add(labelTier, 0, 2);
        grid.add(comboTier, 1, 2);
        grid.add(royalBuffCheck, 0, 3);
        grid.add(focusCheck, 1, 3);
        grid.add(activity10Check, 0, 4);
        grid.add(activity20Check, 1, 4);
        grid.add(calculateButton, 0, 5);
        grid.add(resultLabel, 1, 5);

        return grid;
    }

    private GridPane createRefineResultForStone() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #3B4252;");

        Label labelMaterial = new Label("Raw Material Number for Craft");
        labelMaterial.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textMaterial = new TextField();
        textMaterial.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelReturnRate = new Label("Return Rate (%):");
        labelReturnRate.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textReturnRate = new TextField("15");
        textReturnRate.setEditable(false);
        textReturnRate.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelTier = new Label("Tier:");
        labelTier.setStyle("-fx-text-fill: #ECEFF4;");

        ComboBox<String> comboTier = new ComboBox<>(FXCollections.observableArrayList(
                "3", "4", "4.1", "4.2", "4.3", "5", "5.1", "5.2", "5.3",
                "6", "6.1", "6.2", "6.3", "7", "7.1", "7.2", "7.3",
                "8", "8.1", "8.2", "8.3"));
        comboTier.setValue("3");
        comboTier.setStyle("-fx-background-color: #4C566A; -fx-font-weight: bold; -fx-text-fill: #ECEFF4;");

        CheckBox royalBuffCheck = new CheckBox("Royal Buff");
        royalBuffCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox focusCheck = new CheckBox("Focus");
        focusCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity10Check = new CheckBox("%10 Activity");
        activity10Check.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity20Check = new CheckBox("%20 Activity");
        activity20Check.setStyle("-fx-text-fill: #ECEFF4;");

        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #e09d16; -fx-text-fill: #ECEFF4;");

        Label resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-text-fill: #ECEFF4;");

        Label eldeKalanLabel = new Label("Remaining Raw: ");
        eldeKalanLabel.setStyle("-fx-text-fill: #ECEFF4;");

        royalBuffCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int material = Integer.parseInt(textMaterial.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int[] values = getBolunecekSayiAndCarpilacakSayi(selectedTier);
                int bolunecekSayi = values[0];
                int carpilacakSayi = values[1];

                int sonuc = mainFunction(material, returnRate, bolunecekSayi);
                resultLabel.setText("Result: Result of refine = " + sonuc * carpilacakSayi);
                eldeKalanLabel.setText("Remaining raw: " + REMAINING_RAW);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s");
                String timestamp = LocalDateTime.now().format(formatter);
                historyModel.add("3-> " +
                        "Material: " + material +
                        " / Tier: " + selectedTier +
                        " / Result: " + sonuc * carpilacakSayi +
                        " / Remaining: " + REMAINING_RAW +
                        " / Return Rate: %" + (int) (returnRate * 100) +
                        "         ////" + timestamp);
            } catch (NumberFormatException ex) {
                showAlert("Please Enter Valid Value", "Error");
            } catch (IllegalArgumentException ex) {
                showAlert(ex.getMessage(), "Error");
            }
        });

        grid.add(labelMaterial, 0, 0);
        grid.add(textMaterial, 1, 0);
        grid.add(labelReturnRate, 0, 1);
        grid.add(textReturnRate, 1, 1);
        grid.add(labelTier, 0, 2);
        grid.add(comboTier, 1, 2);
        grid.add(royalBuffCheck, 0, 3);
        grid.add(focusCheck, 1, 3);
        grid.add(activity10Check, 0, 4);
        grid.add(activity20Check, 1, 4);
        grid.add(calculateButton, 0, 5);
        grid.add(resultLabel, 1, 5);
        grid.add(eldeKalanLabel, 0, 6);

        return grid;
    }

    private GridPane createNeededRawForStone() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #3B4252;");

        Label labelIstenilenDeger = new Label("Wanted Refined Value:");
        labelIstenilenDeger.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textIstenilenDeger = new TextField();
        textIstenilenDeger.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelReturnRate = new Label("Return Rate (%):");
        labelReturnRate.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textReturnRate = new TextField("15");
        textReturnRate.setEditable(false);
        textReturnRate.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelTier = new Label("Tier:");
        labelTier.setStyle("-fx-text-fill: #ECEFF4;");

        ComboBox<String> comboTier = new ComboBox<>(FXCollections.observableArrayList(
                "1", "3", "4", "4.1", "4.2", "4.3", "5", "5.1", "5.2", "5.3",
                "6", "6.1", "6.2", "6.3", "7", "7.1", "7.2", "7.3",
                "8", "8.1", "8.2", "8.3"));
        comboTier.setValue("1");
        comboTier.setStyle("-fx-background-color: #4C566A; -fx-font-weight: bold; -fx-text-fill: #ECEFF4;");

        CheckBox royalBuffCheck = new CheckBox("Royal Buff");
        royalBuffCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox focusCheck = new CheckBox("Focus");
        focusCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity10Check = new CheckBox("%10 Activity");
        activity10Check.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity20Check = new CheckBox("%20 Activity");
        activity20Check.setStyle("-fx-text-fill: #ECEFF4;");

        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #e09d16; -fx-text-fill: #ECEFF4;");

        Label resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-text-fill: #ECEFF4;");

        royalBuffCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int istenilenDeger = Integer.parseInt(textIstenilenDeger.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int[] values = getBolunecekSayiAndCarpilacakSayi(selectedTier);
                int bolunecekSayi = values[0];
                int bolunecekSayi2 = values[1];

                int elimizdekiRaw = istenilenDeger * bolunecekSayi;
                int sonuc = 0;

                for (int i = elimizdekiRaw; i >= bolunecekSayi; i--) {
                    int testValue = mainFunction(i, returnRate, bolunecekSayi);
                    if (selectedTier.equals("1")) {
                        if (testValue < istenilenDeger) {
                            sonuc = i + 1;
                            break;
                        }
                    } else {
                        if (testValue == istenilenDeger) {
                            sonuc = i;
                            break;
                        }
                    }
                }
                String sonucLabel = selectedTier.equals("1") ? "Alt Tier" : "Raw";
                resultLabel.setText("Result: Needed " + sonucLabel + " = " + sonuc / bolunecekSayi2);

            } catch (NumberFormatException ex) {
                showAlert("Please enter valid value", "Error");
            } catch (IllegalArgumentException ex) {
                showAlert(ex.getMessage(), "Error");
            }
        });

        grid.add(labelIstenilenDeger, 0, 0);
        grid.add(textIstenilenDeger, 1, 0);
        grid.add(labelReturnRate, 0, 1);
        grid.add(textReturnRate, 1, 1);
        grid.add(labelTier, 0, 2);
        grid.add(comboTier, 1, 2);
        grid.add(royalBuffCheck, 0, 3);
        grid.add(focusCheck, 1, 3);
        grid.add(activity10Check, 0, 4);
        grid.add(activity20Check, 1, 4);
        grid.add(calculateButton, 0, 5);
        grid.add(resultLabel, 1, 5);

        return grid;
    }

    private GridPane createRefineResult() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #3B4252;");

        Label labelMaterial = new Label("Raw Material Number for Craft");
        labelMaterial.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textMaterial = new TextField();
        textMaterial.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelReturnRate = new Label("Return Rate (%):");
        labelReturnRate.setStyle("-fx-text-fill: #ECEFF4;");

        TextField textReturnRate = new TextField("15");
        textReturnRate.setEditable(false);
        textReturnRate.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Label labelTier = new Label("Tier:");
        labelTier.setStyle("-fx-text-fill: #ECEFF4;");

        ComboBox<String> comboTier = new ComboBox<>(FXCollections.observableArrayList("3", "4", "5", "6", "7", "8"));
        comboTier.setValue("3");
        comboTier.setStyle("-fx-background-color: #4C566A; -fx-font-weight: bold; -fx-text-fill: #ECEFF4;");

        CheckBox royalBuffCheck = new CheckBox("Royal Buff");
        royalBuffCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox focusCheck = new CheckBox("Focus");
        focusCheck.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity10Check = new CheckBox("%10 Activity");
        activity10Check.setStyle("-fx-text-fill: #ECEFF4;");

        CheckBox activity20Check = new CheckBox("%20 Activity");
        activity20Check.setStyle("-fx-text-fill: #ECEFF4;");

        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #e09d16; -fx-text-fill: #ECEFF4;");

        Label resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-text-fill: #ECEFF4;");

        Label eldeKalanLabel = new Label("Remaining Raw: ");
        eldeKalanLabel.setStyle("-fx-text-fill: #ECEFF4;");

        royalBuffCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int material = Integer.parseInt(textMaterial.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int bolunecekSayi = getBolunecekSayi(selectedTier);

                int sonuc = mainFunction(material, returnRate, bolunecekSayi);
                resultLabel.setText("Result: Result of refine = " + sonuc);
                eldeKalanLabel.setText("Remaining raw: " + REMAINING_RAW);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s");
                String timestamp = LocalDateTime.now().format(formatter);
                historyModel.add("2-> " +
                        "Material: " + material +
                        " / Tier: " + selectedTier +
                        " / Result: " + sonuc +
                        " / Remaining: " + REMAINING_RAW +
                        " / Return Rate: %" + (int) (returnRate * 100) +
                        "         ////" + timestamp);

            } catch (NumberFormatException ex) {
                showAlert("Please Enter Valid Value", "Error");
            } catch (IllegalArgumentException ex) {
                showAlert(ex.getMessage(), "Error");
            }
        });

        grid.add(labelMaterial, 0, 0);
        grid.add(textMaterial, 1, 0);
        grid.add(labelReturnRate, 0, 1);
        grid.add(textReturnRate, 1, 1);
        grid.add(labelTier, 0, 2);
        grid.add(comboTier, 1, 2);
        grid.add(royalBuffCheck, 0, 3);
        grid.add(focusCheck, 1, 3);
        grid.add(activity10Check, 0, 4);
        grid.add(activity20Check, 1, 4);
        grid.add(calculateButton, 0, 5);
        grid.add(resultLabel, 1, 5);
        grid.add(eldeKalanLabel, 0, 6);

        return grid;
    }

    private VBox createHistoryPanel() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #3B4252;");

        ListView<String> historyList = new ListView<>(historyModel);
        historyList.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #5E81AC; -fx-text-fill: #ECEFF4;");
        clearButton.setOnAction(e -> historyModel.clear());

        vbox.getChildren().addAll(historyList, clearButton);
        return vbox;
    }

    private void updateReturnRate(TextField textReturnRate, CheckBox royalBuffCheck, CheckBox focusCheck, CheckBox activity10Check, CheckBox activity20Check) {
        int baseRate = 15;

        if (royalBuffCheck.isSelected()) {
            baseRate = 36;
        }

        if (focusCheck.isSelected()) {
            if (royalBuffCheck.isSelected()) {
                baseRate = 53;
            } else {
                baseRate = 43;
            }
        }

        if (activity10Check.isSelected() && !focusCheck.isSelected()) {
            baseRate = royalBuffCheck.isSelected() ? 40 : 21;
        } else if (activity20Check.isSelected() && !focusCheck.isSelected()) {
            baseRate = royalBuffCheck.isSelected() ? 43 : 27;
        } else if (activity10Check.isSelected() && focusCheck.isSelected()) {
            baseRate = royalBuffCheck.isSelected() ? 55 : 46;
        } else if (activity20Check.isSelected() && focusCheck.isSelected()) {
            baseRate = royalBuffCheck.isSelected() ? 57 : 49;
        }

        textReturnRate.setText(String.valueOf(baseRate));
        dynamicReturnRateLabel.setText("Return Rate: %" + baseRate);
    }

    private int getBolunecekSayi(String tier) {
        switch (tier) {
            case "1":
                return 1;
            case "3":
            case "4":
                return 2;
            case "5":
                return 3;
            case "6":
                return 4;
            case "7":
            case "8":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid Tier Value!");
        }
    }

    private int[] getBolunecekSayiAndCarpilacakSayi(String tier) {
        int bolunecekSayi = 0;
        int carpilacakSayi = 1;

        switch (tier) {
            case "1":
                bolunecekSayi = 1;
                break;
            case "4.1":
                bolunecekSayi = 2;
                carpilacakSayi = 2;
                break;
            case "5.1":
                bolunecekSayi = 3;
                carpilacakSayi = 2;
                break;
            case "6.1":
                bolunecekSayi = 4;
                carpilacakSayi = 2;
                break;
            case "7.1":
            case "8.1":
                bolunecekSayi = 5;
                carpilacakSayi = 2;
                break;
            case "4.2":
                bolunecekSayi = 2;
                carpilacakSayi = 4;
                break;
            case "5.2":
                bolunecekSayi = 3;
                carpilacakSayi = 4;
                break;
            case "6.2":
                bolunecekSayi = 4;
                carpilacakSayi = 4;
                break;
            case "7.2":
            case "8.2":
                bolunecekSayi = 5;
                carpilacakSayi = 4;
                break;
            case "4.3":
                bolunecekSayi = 2;
                carpilacakSayi = 8;
                break;
            case "5.3":
                bolunecekSayi = 3;
                carpilacakSayi = 8;
                break;
            case "6.3":
                bolunecekSayi = 4;
                carpilacakSayi = 8;
                break;
            case "7.3":
            case "8.3":
                bolunecekSayi = 5;
                carpilacakSayi = 8;
                break;
            case "3":
            case "4":
                bolunecekSayi = 2;
                break;
            case "5":
                bolunecekSayi = 3;
                break;
            case "6":
                bolunecekSayi = 4;
                break;
            case "7":
            case "8":
                bolunecekSayi = 5;
                break;
            default:
                throw new IllegalArgumentException("Invalid Tier Value!");
        }

        return new int[]{bolunecekSayi, carpilacakSayi};
    }

    private void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int mainFunction(int elimizdekiRaw, double returnRate, int bolunecekSayi) {
        if (elimizdekiRaw < bolunecekSayi) {
            REMAINING_RAW = elimizdekiRaw;
            return 0;
        }
        int sonuc1 = elimizdekiRaw / bolunecekSayi;
        double geriDonen = sonuc1 * bolunecekSayi * returnRate;
        geriDonen = (int) geriDonen;
        elimizdekiRaw = (int) (elimizdekiRaw - (sonuc1 * bolunecekSayi) + geriDonen);
        return sonuc1 + mainFunction(elimizdekiRaw, returnRate, bolunecekSayi);
    }
}
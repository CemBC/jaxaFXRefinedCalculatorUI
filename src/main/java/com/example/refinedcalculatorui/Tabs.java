package com.example.refinedcalculatorui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tabs {



    public static GridPane createNeededRawCalculator() {
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

        royalBuffCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int istenilenDeger = Integer.parseInt(textIstenilenDeger.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int bolunecekSayi =  Utility.getBolunecekSayi(selectedTier);

                int elimizdekiRaw = istenilenDeger * bolunecekSayi;
                int sonuc = 0;

                for (int i = elimizdekiRaw; i >= bolunecekSayi; i--) {
                    int testValue = Utility.mainFunction(i, returnRate, bolunecekSayi);
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
                Utility.showAlert("Please enter valid value", "Error");
            } catch (IllegalArgumentException ex) {
                Utility.showAlert(ex.getMessage(), "Error");
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

    public static GridPane createRefineResultForStone() {
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

        royalBuffCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int material = Integer.parseInt(textMaterial.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int[] values = Utility.getBolunecekSayiAndCarpilacakSayi(selectedTier);
                int bolunecekSayi = values[0];
                int carpilacakSayi = values[1];

                int sonuc = Utility.mainFunction(material, returnRate, bolunecekSayi);
                resultLabel.setText("Result: Result of refine = " + sonuc * carpilacakSayi);
                eldeKalanLabel.setText("Remaining raw: " + MainFX.REMAINING_RAW);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s");
                String timestamp = LocalDateTime.now().format(formatter);
                MainFX.historyModel.add("3-> " +
                        "Material: " + material +
                        " / Tier: " + selectedTier +
                        " / Result: " + sonuc * carpilacakSayi +
                        " / Remaining: " + MainFX.REMAINING_RAW +
                        " / Return Rate: %" + (int) (returnRate * 100) +
                        "         ////" + timestamp);
            } catch (NumberFormatException ex) {
                Utility.showAlert("Please Enter Valid Value", "Error");
            } catch (IllegalArgumentException ex) {
                Utility.showAlert(ex.getMessage(), "Error");
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

    public static GridPane createNeededRawForStone() {
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

        royalBuffCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int istenilenDeger = Integer.parseInt(textIstenilenDeger.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int[] values = Utility.getBolunecekSayiAndCarpilacakSayi(selectedTier);
                int bolunecekSayi = values[0];
                int bolunecekSayi2 = values[1];

                int elimizdekiRaw = istenilenDeger * bolunecekSayi;
                int sonuc = 0;

                for (int i = elimizdekiRaw; i >= bolunecekSayi; i--) {
                    int testValue = Utility.mainFunction(i, returnRate, bolunecekSayi);
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
                Utility.showAlert("Please enter valid value", "Error");
            } catch (IllegalArgumentException ex) {
                Utility.showAlert(ex.getMessage(), "Error");
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

    public static GridPane createRefineResult() {
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

        royalBuffCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        focusCheck.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity10Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));
        activity20Check.setOnAction(e -> Utility.updateReturnRate(textReturnRate, royalBuffCheck, focusCheck, activity10Check, activity20Check));

        calculateButton.setOnAction(e -> {
            try {
                int material = Integer.parseInt(textMaterial.getText());
                double returnRate = Double.parseDouble(textReturnRate.getText()) / 100.0;
                String selectedTier = comboTier.getValue();

                int bolunecekSayi = Utility.getBolunecekSayi(selectedTier);

                int sonuc = Utility.mainFunction(material, returnRate, bolunecekSayi);
                resultLabel.setText("Result: Result of refine = " + sonuc);
                eldeKalanLabel.setText("Remaining raw: " + MainFX.REMAINING_RAW);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s");
                String timestamp = LocalDateTime.now().format(formatter);
                MainFX.historyModel.add("2-> " +
                        "Material: " + material +
                        " / Tier: " + selectedTier +
                        " / Result: " + sonuc +
                        " / Remaining: " + MainFX.REMAINING_RAW +
                        " / Return Rate: %" + (int) (returnRate * 100) +
                        "         ////" + timestamp);

            } catch (NumberFormatException ex) {
                Utility.showAlert("Please Enter Valid Value", "Error");
            } catch (IllegalArgumentException ex) {
                Utility.showAlert(ex.getMessage(), "Error");
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

    public static VBox createHistoryPanel() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #3B4252;");

        ListView<String> historyList = new ListView<>(MainFX.historyModel);
        historyList.setStyle("-fx-background-color: #4C566A; -fx-text-fill: #ECEFF4;");

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #5E81AC; -fx-text-fill: #ECEFF4;");
        clearButton.setOnAction(e -> MainFX.historyModel.clear());

        vbox.getChildren().addAll(historyList, clearButton);
        return vbox;
    }
}

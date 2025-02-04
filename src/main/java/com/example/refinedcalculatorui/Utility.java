package com.example.refinedcalculatorui;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Utility {
    public static void updateReturnRate(TextField textReturnRate, CheckBox royalBuffCheck, CheckBox focusCheck, CheckBox activity10Check, CheckBox activity20Check) {
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
        MainFX.dynamicReturnRateLabel.setText("Return Rate: %" + baseRate);
    }

    public static int getBolunecekSayi(String tier) {
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

    public static int[] getBolunecekSayiAndCarpilacakSayi(String tier) {
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

    public static void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int mainFunction(int elimizdekiRaw, double returnRate, int bolunecekSayi) {
        if (elimizdekiRaw < bolunecekSayi) {
            MainFX.REMAINING_RAW = elimizdekiRaw;
            return 0;
        }
        int sonuc1 = elimizdekiRaw / bolunecekSayi;
        double geriDonen = sonuc1 * bolunecekSayi * returnRate;
        geriDonen = (int) geriDonen;
        elimizdekiRaw = (int) (elimizdekiRaw - (sonuc1 * bolunecekSayi) + geriDonen);
        return sonuc1 + mainFunction(elimizdekiRaw, returnRate, bolunecekSayi);
    }
}

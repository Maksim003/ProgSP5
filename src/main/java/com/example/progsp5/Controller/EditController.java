package com.example.progsp5.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.progsp5.Model.DateUtil;
import com.example.progsp5.Model.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField category;

    @FXML
    private TextField date;

    @FXML
    private Button editButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField name;

    @FXML
    private TextField number;

    @FXML
    private TextField status;

    @FXML
    private TextField type;

    private Stage dialogStage;
    private Document document;
    private boolean okClicked = false;

    @FXML
    void initialize() {
    }


    private boolean isInputValid() {
        String errorMessage = "";
        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Некорректно введено имя!\n";
        }
        if (number.getText() == null || number.getText().length() == 0) {
            errorMessage += "Некорректно введен номер!\n";
        } else {
            try {
                Integer.parseInt(number.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Некорректно введен номер(должен быть целочисленным) !\n ";
            }
        }
        if (category.getText() == null || category.getText().length() == 0) {
            errorMessage += "Некорректно введена категория!\n";
        }
        if (type.getText() == null || type.getText().length() == 0) {
            errorMessage += "Некорректно введен тип!\n";
        }
        if (status.getText() == null || status.getText().length() == 0) {
            errorMessage += "Некорректно введен статус!\n";
        }
        if (date.getText() == null || date.getText().length() == 0) {
            errorMessage += "Некорректно введена дата создания!\n";
        } else {
            if (!DateUtil.validDate(date.getText())) {
                errorMessage += "Неверный формат даты. Используйте дд.мм.ггг";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Введите корректные значения полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDoc(Document document) {
        this.document = document;
        name.setText(document.getName());
        number.setText(Integer.toString(document.getNumberOfDoc()));
        category.setText(document.getCategory());
        type.setText(document.getType());
        status.setText(document.getStatus());
        date.setText(DateUtil.format(document.getDate()));
        date.setPromptText("dd.mm.yyyy");
    }

    public void addDoc(Document document) {
        this.document = document;
        date.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void ok() {
        if (isInputValid()) {
            document.setName(name.getText());
            document.setNumberOfDoc(Integer.parseInt(number.getText()));
            document.setCategory(category.getText());
            document.setType(type.getText());
            document.setStatus(status.getText());
            document.setDate(DateUtil.parse(date.getText()));
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void cancel() {
        dialogStage.close();
    }
}

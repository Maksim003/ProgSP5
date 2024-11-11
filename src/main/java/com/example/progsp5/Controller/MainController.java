package com.example.progsp5.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.progsp5.Main;
import com.example.progsp5.Model.DateUtil;
import com.example.progsp5.Model.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Label categoryLabel;

    @FXML
    private Button changeButton;

    @FXML
    private TableColumn<Document, String> columnName;

    @FXML
    private TableColumn<Document, Integer> columnNumber;

    @FXML
    private Label dateLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label labelInfo;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Document> tableView;

    @FXML
    private Label typeLabel;

    private Main main = new Main();


    public MainController() {
    }

    @FXML
    void initialize() {
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().numberOfDocProperty().asObject());
        showDocDetails(null);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDocDetails(newValue));
    }

    public void setMain(Main main) {
        this.main = main;
        tableView.setItems(main.getDocumentsData());
    }

    private void showDocDetails(Document document) {
        if (document != null) {
            nameLabel.setText(document.getName());
            numberLabel.setText(Integer.toString(document.getNumberOfDoc()));
            categoryLabel.setText(document.getCategory());
            typeLabel.setText(document.getType());
            statusLabel.setText(document.getStatus());
            dateLabel.setText(DateUtil.format(document.getDate()));
        } else {
            nameLabel.setText("");
            numberLabel.setText("");
            categoryLabel.setText("");
            typeLabel.setText("");
            statusLabel.setText("");
            dateLabel.setText("");
        }
    }

    @FXML
    void deleteDocument(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаление");
            alert.setHeaderText("Удаление аккаунта");
            alert.setContentText("Вы действительно хотите удалить аккаунт?");
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == ButtonType.OK) {
                tableView.getItems().remove(selectedIndex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Не выбрана строка для удаления!");
            alert.setHeaderText("Предупреждение!");
            alert.setContentText("Выберите, пожалуйста, строку в таблице");
            alert.showAndWait();
        }
    }

    @FXML
    private void addDocument() {
        Document document = new Document();
        boolean okClicked = main.showDocAddDialog(document);
        if (okClicked) main.getDocumentsData().add(document);
    }

    @FXML
    private void editDocument() {
        Document selectedDoc = tableView.getSelectionModel().getSelectedItem();
        if (selectedDoc != null) {
            boolean okClicked = main.showDocEditDialog(selectedDoc);
            if (okClicked) showDocDetails(selectedDoc);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText("Не выбрана запись");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }


}



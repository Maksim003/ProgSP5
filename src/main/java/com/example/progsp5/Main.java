package com.example.progsp5;

import com.example.progsp5.Controller.EditController;
import com.example.progsp5.Controller.MainController;
import com.example.progsp5.Model.DateUtil;
import com.example.progsp5.Model.Document;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private static ObservableList<Document> documentsData = FXCollections.observableArrayList();
    static File file = new File("documents.txt");

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Документооборот");
        try {
            if (!file.exists()) file.createNewFile();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(" ");
                String name = data[0];
                int number = Integer.parseInt(data[1]);
                String category = data[2];
                String type = data[3];
                String status = data[4];
                LocalDate date = DateUtil.parse(data[5]);
                documentsData.add(new Document(name, number, category, type, status, date));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        initRootLayout();
        showDocumentOverview();
    }

    @Override
    public void stop() {
        try {
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < documentsData.size(); i++) {
                pw.println(documentsData.get(i).getName() + " " + documentsData.get(i).getNumberOfDoc() + " " +
                        documentsData.get(i).getCategory() + " " + documentsData.get(i).getType() + " " +
                        documentsData.get(i).getStatus() + " " + DateUtil.format(documentsData.get(i).getDate()));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("rootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDocumentOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("document.fxml"));
            AnchorPane documentOverview = loader.load();
            rootLayout.setCenter(documentOverview);
            MainController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showDocEditDialog(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("editDocument.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            EditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDoc(document);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showDocAddDialog(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("editDocument.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            EditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.addDoc(document);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Document> getDocumentsData() {
        return documentsData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

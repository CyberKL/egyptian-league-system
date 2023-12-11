package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Manager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    protected Button backBtn;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nationalityField;

    @FXML
    private Spinner<Integer> trophiesSpinner;

    @FXML
    private ToggleGroup wasPlayer;



    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
    public void blueBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }
    public void switchManagerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    private void removeNodeById(AnchorPane container, String nodeId) {
        container.getChildren().stream()
                .filter(node -> node.getId() != null && node.getId().equals(nodeId))
                .findFirst()
                .ifPresent(node -> node.setVisible(false));
    }


    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");
    Alert managerNotFound = new Alert(Alert.AlertType.WARNING, "Manager not found!");

    int currentValue;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        trophiesSpinner.setValueFactory(valueFactory);
        currentValue = trophiesSpinner.getValue();
        trophiesSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = trophiesSpinner.getValue();
            }
        });
    }

    private boolean isAnyFieldBlank() {
        return nameField.getText().isBlank() ||
                idField.getText().isBlank() ||
                dobPicker.getValue() == null || dobPicker.getValue().toString().isBlank() ||
                nationalityField.getText().isBlank();
    }
    public void createManager(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Manager created successfully!");
        if (isAnyFieldBlank() || wasPlayer.getSelectedToggle() == null){
            missingDataAlert.show();
        }
        else {
            String name = nameField.getText();
            int id = Integer.parseInt(idField.getText());
            LocalDate dateOfBirth = dobPicker.getValue();
            String nationality = nationalityField.getText();
            int numOfTrophies = currentValue;
            RadioButton selectedRadioButton = (RadioButton) wasPlayer.getSelectedToggle();
            boolean formerPlayer = selectedRadioButton.getText().equalsIgnoreCase("yes");
            if (validateData(id, dateOfBirth)){
                Manager manager = new Manager(name, dateOfBirth, nationality, id, numOfTrophies, formerPlayer);
                Logic.addManager(manager);
                success.show();
            }
        }
    }
    public boolean validateData(int id , LocalDate dateOfBirth){
        boolean duplicateId = false;
        boolean validDob = true;
        boolean valid = false;

        for (Manager manager : Logic.getManagers()){
            if (manager.getManagerId()==id){
                duplicateId = true;
                Alert alert = new Alert(Alert.AlertType.WARNING, "There is already a manager with this ID");
                alert.show();
            }
        }
        Period period = Period.between(dateOfBirth, LocalDate.now());
        if (period.getYears()<30){
            validDob = false;
            Alert alert = new Alert(Alert.AlertType.WARNING, "Manager Can't be under 30 years old!");
            alert.show();
        }
        if (!duplicateId && validDob){
            valid = true;
        }
        return valid;
    }




}

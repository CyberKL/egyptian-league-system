package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Manager;
import edu.ainshams.egyptianleaguesystem.model.Team;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();


    @FXML
    private MFXDatePicker dobPicker;

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

    @FXML
    private AnchorPane subMenuRoot;

    @FXML
    private MFXComboBox<String> teamChoice;

    private MFXButton createButton(String icon, String text, EventHandler action) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXButton button = new MFXButton(text, wrapper);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(action);
        return button;
    }



    public void switchManagerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");

    int currentValue;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXButton back = createButton("fas-left-long", "Back", event -> {
            try {
                switchManagerMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
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
        for (Team team : Logic.getTeams()){
            teamChoice.getItems().add(team.getName());
        }
    }

    private boolean isAnyFieldBlank() {
        return nameField.getText().isBlank() ||
                idField.getText().isBlank() ||
                dobPicker.getValue() == null || dobPicker.getValue().toString().isBlank() ||
                nationalityField.getText().isBlank() ||
                wasPlayer.getSelectedToggle() == null;
    }
    public void createManager(ActionEvent event){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Manager created successfully!");
        if (isAnyFieldBlank()){
            missingDataAlert.show();
        }
        else {
            try {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                LocalDate dateOfBirth = dobPicker.getValue();
                String nationality = nationalityField.getText();
                int numOfTrophies = currentValue;
                String teamName = teamChoice.getValue();
                RadioButton selectedRadioButton = (RadioButton) wasPlayer.getSelectedToggle();
                boolean formerPlayer = selectedRadioButton.getText().equalsIgnoreCase("yes");
                if (validateData(id, dateOfBirth)) {
                    Team currentTeam = null;
                    for (Team team : Logic.getTeams()) {
                        if (team.getName().equalsIgnoreCase(teamName)) {
                            currentTeam = team;
                            break;
                        }
                    }
                    if (currentTeam == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a team, if there is no team available please add a team before adding a new player!");
                        alert.show();
                    }
                    else {
                        Manager manager = new Manager(name, dateOfBirth, nationality, id, numOfTrophies, formerPlayer, currentTeam);
                        Logic.addManager(manager);
                        if (currentTeam.getManager()!=null) {
                            currentTeam.getManager().setTeam(null);
                        }
                        currentTeam.setManager(manager);
                        success.showAndWait();
                        switchManagerMenu(event);
                    }
                }
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in the id field");
                alert.show();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred");
                alert.show();
            }
        }
    }
    private boolean validateData(int id , LocalDate dateOfBirth){
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
            Alert alert = new Alert(Alert.AlertType.WARNING, "Manager Can't be under 30 years!");
            alert.show();
        }
        if (!duplicateId && validDob){
            valid = true;
        }
        return valid;
    }




}

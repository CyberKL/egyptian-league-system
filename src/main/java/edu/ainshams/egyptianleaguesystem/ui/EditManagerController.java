package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Manager;
import edu.ainshams.egyptianleaguesystem.model.Team;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EditManagerController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private ToggleGroup choice;

    @FXML
    private Label currentInfo;

    @FXML
    private Label currentInfoLabel;

    @FXML
    private Label managerNameLabel;

    @FXML
    private MFXDatePicker newDate;

    @FXML
    private TextField newInfoField;

    @FXML
    private Label newInfoLabel;

    @FXML
    private HBox wasPlayerBox;

    @FXML
    private ToggleGroup wasPlayerGroup;

    private Manager currentManager;
    @FXML
    private AnchorPane subMenuRoot;
    @FXML
    private BorderPane editForm;

    private MFXButton createButton(String icon, String text, EventHandler action) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXButton button = new MFXButton(text, wrapper);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(action);
        return button;
    }
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
        if(choice!=null) {
            choice.selectedToggleProperty().addListener(((observableValue, toggle, t1) -> {
                if (t1 == null) {
                    editForm.setVisible(false);
                }
            }));
        }
    }


    public void switchManagerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return null;
        }
    }
    public void initialize(Manager manager) {
        managerNameLabel.setText(manager.getName());
        choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showNodes(manager);
                currentManager = manager;
            }
        });
    }
    private void showNodes(Manager manager){
        String editing = getSelectedButtonText();
        if (manager != null && editing!=null){
            if (editing.equalsIgnoreCase("Name")){
                currentInfoLabel.setText("Current "+editing+":");
                currentInfo.setText(manager.getName());
                newInfoLabel.setText("New "+editing+":");
            }
            else if (editing.equalsIgnoreCase("date of birth")){
                currentInfoLabel.setText("Current "+editing+":");
                currentInfo.setText(manager.getDateOfBirth().toString());
                newInfoLabel.setText("New "+editing+":");
            }
            else if (editing.equalsIgnoreCase("team")){
                currentInfoLabel.setText("Current "+editing+":");
                if (manager.getTeam() != null) {
                    currentInfo.setText(manager.getTeam().getName());
                }
                else {
                    currentInfo.setText("N/A");
                }
                newInfoLabel.setText("New "+editing+":");
            }
            else if (editing.equalsIgnoreCase("Trophies")) {
                currentInfoLabel.setText("Current number of "+editing+":");
                currentInfo.setText(String.valueOf(manager.getTrophies()));
                newInfoLabel.setText("New number of "+editing+":");
            }
            else if (editing.equalsIgnoreCase("Nationality")){
                currentInfoLabel.setText("Current "+editing+":");
                currentInfo.setText(manager.getNationality());
                newInfoLabel.setText("New "+editing+":");
            }
            else if (editing.equalsIgnoreCase("Former Player")){
                currentInfoLabel.setText("Was a "+editing+":");
                currentInfo.setText(Boolean.toString(manager.isWasPlayer()));
                newInfoLabel.setText("Change "+editing+":");
            }
            else if (editing.equalsIgnoreCase("yellow cards")){
                currentInfoLabel.setText("Current number of "+editing+":");
                currentInfo.setText(String.valueOf(manager.getYellowCards()));
                newInfoLabel.setText("New number of "+editing+":");
            }
            else {
                currentInfoLabel.setText("Current number of "+editing+":");
                currentInfo.setText(String.valueOf(manager.getRedCards()));
                newInfoLabel.setText("New number of "+editing+":");
            }

            if (editing.equalsIgnoreCase("date of birth")){
                newDate.setVisible(true);
                newInfoField.setVisible(false);
                wasPlayerBox.setVisible(false);
            }
            else if(editing.equalsIgnoreCase("former player")){
                wasPlayerBox.setVisible(true);
                newDate.setVisible(false);
                newInfoField.setVisible(false);
            }
            else {
                newInfoField.setVisible(true);
                newDate.setVisible(false);
                wasPlayerBox.setVisible(false);
            }
            editForm.setVisible(true);
        }
    }

    public void editManagerInfo(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Manager updated successfully!");
        Manager manager = currentManager;
        String editing = getSelectedButtonText();
        if (manager != null && editing != null) {
            try {
                if (editing.equalsIgnoreCase("name")) {
                    String name = newInfoField.getText();
                    manager.setName(name);
                    managerNameLabel.setText(manager.getName());
                    success.show();
                } else if (editing.equalsIgnoreCase("date of birth")) {
                    LocalDate dob = newDate.getValue();
                    Period period = Period.between(dob, LocalDate.now());
                    if (period.getYears() < 30) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Manager can't be younger than 30 years");
                        alert.show();
                    } else {
                        manager.setDateOfBirth(dob);
                        success.show();
                    }
                } else if (editing.equalsIgnoreCase("team")) {
                    String teamName = newInfoField.getText();
                    boolean teamFound = false;
                    for (Team team : Logic.getTeams()) {
                        if (team.getName().equalsIgnoreCase(teamName)) {
                            teamFound = true;
                            if (manager.getTeam() != null) {
                                manager.getTeam().setManager(null);
                            }
                            if (team.getManager() != null){
                                team.getManager().setTeam(null);
                            }
                            manager.setTeam(team);
                            success.show();
                        }
                    }
                    if (!teamFound){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Team not found!");
                        alert.show();
                    }
                } else if (editing.equalsIgnoreCase("trophies")) {
                    int trophies = Integer.parseInt(newInfoField.getText());
                    manager.setTrophies(trophies);
                    success.show();
                } else if (editing.equalsIgnoreCase("nationality")) {
                    String nationality = newInfoField.getText();
                    manager.setNationality(nationality);
                    success.show();
                } else if (editing.equalsIgnoreCase("former player")) {
                    RadioButton selectedRadioButton = (RadioButton) wasPlayerGroup.getSelectedToggle();
                    boolean wasPlayer = selectedRadioButton.getText().equalsIgnoreCase("yes");
                    manager.setWasPlayer(wasPlayer);
                    success.show();
                } else if (editing.equalsIgnoreCase("yellow cards")) {
                    int numOfYellowCards = Integer.parseInt(newInfoField.getText());
                    manager.setYellowCards(numOfYellowCards);
                    success.show();
                } else {
                    int numOfRedCards = Integer.parseInt(newInfoField.getText());
                    manager.setRedCards(numOfRedCards);
                    success.show();
                }
                newInfoField.setText("");
                choice.selectToggle(null);
                editForm.setVisible(false);
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input, please try again");
                alert.show();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error has occurred");
                alert.show();
            }
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred please try again");
            error.show();
        }
    }



}

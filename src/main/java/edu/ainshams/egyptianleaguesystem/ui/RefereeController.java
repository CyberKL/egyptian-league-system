package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Referee;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class RefereeController implements Initializable {
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
    private ToggleGroup choice;

    @FXML
    private GridPane infoGrid;

    @FXML
    private Label currentInfo;

    @FXML
    private Label currentInfoLabel;

    @FXML
    private BorderPane editForm;

    @FXML
    private TextField newInfoField;

    @FXML
    private Label newInfoLabel;

    @FXML
    private Label refereeNameLabel;
    @FXML
    private MFXDatePicker newDate;
    @FXML
    private AnchorPane subMenuRoot;
    private Referee currentReferee;

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
                switchRefereesMenu((ActionEvent) event);
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

    public void switchRefereesMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("refereesMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");

    private boolean isAnyFieldBlank() {
        return nameField.getText().isBlank() ||
                idField.getText().isBlank() ||
                dobPicker.getValue() == null || dobPicker.getValue().toString().isBlank() ||
                nationalityField.getText().isBlank();
    }
    public boolean validateData(int id , LocalDate dateOfBirth){
        boolean duplicateId = false;
        boolean validDob = true;
        boolean valid = false;

        for (Referee referee : Logic.getReferees()){
            if (referee.getRefereeId()==id){
                duplicateId = true;
                Alert alert = new Alert(Alert.AlertType.WARNING, "There is already a referee with this ID");
                alert.show();
            }
        }
        Period period = Period.between(dateOfBirth, LocalDate.now());
        if (period.getYears()<25){
            validDob = false;
            Alert alert = new Alert(Alert.AlertType.WARNING, "Referees Can't be under 25 years!");
            alert.show();
        }
        if (!duplicateId && validDob){
            valid = true;
        }
        return valid;
    }
    public void createReferee(ActionEvent event){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Referee created successfully!");
        if (isAnyFieldBlank()){
            missingDataAlert.show();
        }
        else {
            try {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                LocalDate dateOfBirth = dobPicker.getValue();
                String nationality = nationalityField.getText();
                if (validateData(id, dateOfBirth)) {
                    Referee referee = new Referee(name, dateOfBirth, nationality, id);
                    Logic.addReferee(referee);
                    success.showAndWait();
                    switchRefereesMenu(event);
                }
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id field");
                alert.show();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred");
                alert.show();
            }
        }
    }


    public void initialize(Referee referee) {
        refereeNameLabel.setText(referee.getName());
        choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showNodes(referee);
                currentReferee = referee;
            }
        });
    }
    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return null;
        }
    }
    private void showNodes(Referee referee) {
        String editing = getSelectedButtonText();
        if (referee != null && editing != null){
            if (editing.equalsIgnoreCase("name")){
                currentInfoLabel.setText("Current name:");
                currentInfo.setText(referee.getName());
                newInfoLabel.setText("New name:");
            }
            else if (editing.equalsIgnoreCase("date of birth")){
                currentInfoLabel.setText("Current date of birth:");
                currentInfo.setText(referee.getDateOfBirth().toString());
                newInfoLabel.setText("New date of birth");
            }
            else if (editing.equalsIgnoreCase("nationality")){
                currentInfoLabel.setText("Current nationality:");
                currentInfo.setText(referee.getNationality());
                newInfoLabel.setText("New nationality");
            }
            else if (editing.equalsIgnoreCase("matches refereed")){
                currentInfoLabel.setText("Current number of matches refereed:");
                currentInfo.setText(Integer.toString(referee.getMatchesRefereed()));
                newInfoLabel.setText("New number of matches refereed");
            }
            else if (editing.equalsIgnoreCase("yellow cards")){
                currentInfoLabel.setText("Current number of yellow cards issued:");
                currentInfo.setText(Integer.toString(referee.getYellowCards()));
                newInfoLabel.setText("New number of yellow cards issued");
            }
            else {
                currentInfoLabel.setText("Current number of red cards issued:");
                currentInfo.setText(Integer.toString(referee.getRedCards()));
                newInfoLabel.setText("New number of red cards issued");
            }
            if (editing.equalsIgnoreCase("date of birth")){
                newInfoField.setVisible(false);
                newDate.setVisible(true);
            }
            else {
                newInfoField.setVisible(true);
                newDate.setVisible(false);
            }
            editForm.setVisible(true);
        }
    }
    public void editRefereeInfo(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Referee updated successfully!");
        Referee referee = currentReferee;
        String editing = getSelectedButtonText();
        if (referee != null && editing != null){
            try {
                if (editing.equalsIgnoreCase("name")) {
                    String name = newInfoField.getText();
                    referee.setName(name);
                    success.show();
                    refereeNameLabel.setText(referee.getName());
                } else if (editing.equalsIgnoreCase("date of birth")) {
                    LocalDate dob = newDate.getValue();
                    Period period = Period.between(dob, LocalDate.now());
                    if (period.getYears() < 25) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Referee can't be younger than 25 years");
                        alert.show();
                    } else {
                        referee.setDateOfBirth(dob);
                        success.show();
                    }
                } else if (editing.equalsIgnoreCase("nationality")) {
                    String nationality = newInfoField.getText();
                    referee.setNationality(nationality);
                    success.show();
                } else if (editing.equalsIgnoreCase("matches refereed")) {
                    int matchesRefereed = Integer.parseInt(newInfoField.getText());
                    referee.setMatchesRefereed(matchesRefereed);
                    success.show();
                } else if (editing.equalsIgnoreCase("yellow cards")) {
                    int yellowCards = Integer.parseInt(newInfoField.getText());
                    referee.setYellowCards(yellowCards);
                    success.show();
                } else {
                    int redCards = Integer.parseInt(newInfoField.getText());
                    referee.setRedCards(redCards);
                    success.show();
                }
                newInfoField.setText("");
                choice.selectToggle(null);
                editForm.setVisible(false);
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id field");
                alert.show();
            }
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred please try again");
            error.show();
        }
    }

    public void refereeInfo(Referee referee){
        Label nameLabel = new Label(referee.getName());
        nameLabel.getStyleClass().add("info-label");

        Label idLabel = new Label(Integer.toString(referee.getRefereeId()));
        idLabel.getStyleClass().add("info-label");

        Label ageLabel = new Label(Integer.toString(referee.getAge()));
        ageLabel.getStyleClass().add("info-label");

        Label nationalityLabel = new Label(referee.getNationality());
        nationalityLabel.getStyleClass().add("info-label");

        Label matchesRefereedLabel = new Label(Integer.toString(referee.getMatchesRefereed()));
        matchesRefereedLabel.getStyleClass().add("info-label");

        Label yellowCardLabel = new Label(Integer.toString(referee.getYellowCards()));
        yellowCardLabel.getStyleClass().add("info-label");

        Label redCardLabel = new Label(Integer.toString(referee.getRedCards()));
        redCardLabel.getStyleClass().add("info-label");
        VBox labelsBox = new VBox(nameLabel, idLabel, ageLabel, nationalityLabel, matchesRefereedLabel, yellowCardLabel, redCardLabel);
        labelsBox.setAlignment(Pos.CENTER);
        infoGrid.add(labelsBox, 1, 0);
    }
}

package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import io.github.palexdev.materialfx.controls.*;
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

public class NewPlayerController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private MFXDatePicker dobPicker;

    @FXML
    private ToggleGroup foot;

    @FXML
    private TextField heightField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nationalityField;

    @FXML
    private Spinner<Integer> numSpinner;

    @FXML
    private MFXComboBox<String> positionChoice;

    @FXML
    private MFXComboBox<String> teamChoice;

    @FXML
    private TextField weightField;
    @FXML
    private AnchorPane subMenuRoot;


    public void switchPlayersMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");

    private final String[] positions = {"Forward","Midfielder","Defender","Goalkeeper"};
    int currentValue;

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
                switchPlayersMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
        for (Team team : Logic.getTeams()){
            teamChoice.getItems().add(team.getName());
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
        valueFactory.setValue(1);
        numSpinner.setValueFactory(valueFactory);
        currentValue = numSpinner.getValue();
        numSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = numSpinner.getValue();
            }
        });
        positionChoice.getItems().addAll(positions);
    }

    private boolean isAnyFieldBlank() {
        return nameField.getText().isBlank() ||
                idField.getText().isBlank() ||
                dobPicker.getValue() == null || dobPicker.getValue().toString().isBlank() ||
                nationalityField.getText().isBlank() ||
                heightField.getText().isBlank() ||
                weightField.getText().isBlank() ||
                teamChoice.getValue() == null ||
                positionChoice.getValue() == null ||
                foot.getSelectedToggle() == null;
    }

    public boolean validateData(int id , LocalDate dateOfBirth){
        boolean duplicateId = false;
        boolean validDob = true;
        boolean valid = false;

        for (Player player : Logic.getPlayers()){
            if (player.getPlayerId()==id){
                duplicateId = true;
                Alert alert = new Alert(Alert.AlertType.WARNING, "There is already a player with this ID");
                alert.show();
            }
        }
        Period period = Period.between(dateOfBirth, LocalDate.now());
        if (period.getYears()<16){
            validDob = false;
            Alert alert = new Alert(Alert.AlertType.WARNING, "Player Can't be under 16 years!");
            alert.show();
        }
        if (!duplicateId && validDob){
            valid = true;
        }
        return valid;
    }
    private String getSelectedButtonText() {
        Toggle selectedToggle = foot.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return null;
        }
    }

    public void createPlayer(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Player created successfully!");
        if (isAnyFieldBlank()){
            missingDataAlert.show();
        }
        else {
            try {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                LocalDate dateOfBirth = dobPicker.getValue();
                String nationality = nationalityField.getText();
                int number = numSpinner.getValue();
                String teamName = teamChoice.getValue();
                int height = Integer.parseInt(heightField.getText());
                int weight = Integer.parseInt(weightField.getText());
                String preferredFoot = getSelectedButtonText();
                String position = positionChoice.getValue();
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
                        Player player = null;
                        switch (position) {
                            case "Forward": {
                                player = new Forward(name, dateOfBirth, nationality, id, number, currentTeam, height, weight, preferredFoot);
                                break;
                            }
                            case "Midfielder": {
                                player = new Midfielder(name, dateOfBirth, nationality, id, number, currentTeam, height, weight, preferredFoot);
                                break;
                            }
                            case "Defender": {
                                player = new Defender(name, dateOfBirth, nationality, id, number, currentTeam, height, weight, preferredFoot);
                                break;
                            }
                            case "Goalkeeper": {
                                player = new Goalkeeper(name, dateOfBirth, nationality, id, number, currentTeam, height, weight, preferredFoot);
                                break;
                            }
                        }
                        Logic.addPlayer(player);
                        currentTeam.addPlayer(player);
                        success.show();
                        nameField.clear();
                        idField.clear();
                        nationalityField.clear();
                        heightField.clear();
                        weightField.clear();
                        dobPicker.setValue(null);
                        numSpinner.getValueFactory().setValue(numSpinner.getValueFactory().getConverter().fromString("1"));
                        foot.selectToggle(null);
                        teamChoice.setValue(null);
                        positionChoice.setValue(null);
                    }
                }
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input, please try again");
                alert.show();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred");
                alert.show();
            }
        }
    }
}

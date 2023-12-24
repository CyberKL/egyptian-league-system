package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Logic;
import edu.ainshams.egyptianleaguesystem.model.Match;
import edu.ainshams.egyptianleaguesystem.model.Stadium;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StadiumController implements Initializable {
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private TextField nameField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField cityField;
    @FXML
    private GridPane infoGrid;
    @FXML
    private TextField idField;
    @FXML
    private AnchorPane subMenuRoot;
    @FXML
    private VBox infoBox;

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
                switchStadiumMenu((ActionEvent) event);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        });
        subMenuRoot.getChildren().add(back);
        AnchorPane.setRightAnchor(back, 0.5);
        AnchorPane.setTopAnchor(back, 25.0);
    }

    public void switchStadiumMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stadiumsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    Alert missingDataAlert = new Alert(Alert.AlertType.WARNING, "Please fill in the required data!");

    public void createStadium(ActionEvent event){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Stadium created successfully!");
        if (nameField.getText().isBlank() || idField.getText().isBlank() || capacityField.getText().isBlank() || cityField.getText().isBlank()){
            missingDataAlert.show();
        }
        else {
            try {
                boolean duplicateStadium = false;
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                int capacity = Integer.parseInt(capacityField.getText());
                String city = cityField.getText();
                for (Stadium stadium : Logic.getStadiums()) {
                    if (stadium.getName().equalsIgnoreCase(name)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("There is already a stadium with this name!");
                        alert.show();
                        duplicateStadium = true;
                        break;
                    }
                }
                for (Stadium stadium : Logic.getStadiums()) {
                    if (stadium.getId() == id) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("There is already a stadium with this name!");
                        alert.show();
                        duplicateStadium = true;
                        break;
                    }
                }
                if (!duplicateStadium) {
                    Stadium stadium = new Stadium(name, id, capacity, city);
                    Logic.addStadium(stadium);
                    success.showAndWait();
                    switchStadiumMenu(event);
                }
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id and capacity field");
                alert.show();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred");
                alert.show();
            }
        }
    }

    public void stadiumInfo(Stadium stadium){
        Label stadiumName = new Label(stadium.getName());
        stadiumName.getStyleClass().add("info-label");

        Label stadiumId = new Label(Integer.toString(stadium.getId()));
        stadiumId.getStyleClass().add("info-label");

        Label stadiumCity = new Label(stadium.getCity());
        stadiumCity.getStyleClass().add("info-label");

        Label stadiumCapacity = new Label(Integer.toString(stadium.getCapacity()));
        stadiumCapacity.getStyleClass().add("info-label");

        Label stadiumNumOfMatches = new Label(Integer.toString(stadium.getMatchesPlayedOn()));
        stadiumNumOfMatches.getStyleClass().add("info-label");
        VBox infoList = new VBox(stadiumName, stadiumId, stadiumCity, stadiumCapacity, stadiumNumOfMatches);
        infoList.setAlignment(Pos.CENTER);
        infoGrid.add(infoList, 1, 0);
        infoGrid.setVisible(true);
    }

    public void stadiumUpcomingMatches(Stadium stadium){
        for (Match match : stadium.getUpcomingMatches()){
            Label label = new Label(match.matchHeader());
            label.setTextFill(Color.WHITE);
            label.setFont(new Font(30));
            HBox hBox = new HBox(label);
            hBox.setPrefWidth(565);
            infoBox.getChildren().add(hBox);
        }
    }

}

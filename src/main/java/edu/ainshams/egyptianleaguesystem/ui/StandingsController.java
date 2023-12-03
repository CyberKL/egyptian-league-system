package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.Team;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StandingsController implements Initializable {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    private Stage stage;

    @FXML
    private Button backBtn;
    @FXML
    private TableView<Team> standingsTable;
    @FXML
    private TableColumn<Team, String> clubCol;
    @FXML
    private TableColumn<Team, Integer> mpCol;
    @FXML
    private TableColumn<Team, Integer> winsCol;
    @FXML
    private TableColumn<Team, Integer> drawsCol;
    @FXML
    private TableColumn<Team, Integer> lossesCol;
    @FXML
    private TableColumn<Team, Integer> gfCol;
    @FXML
    private TableColumn<Team, Integer> gaCol;
    @FXML
    private TableColumn<Team, Integer> gdCol;
    @FXML
    private TableColumn<Team, Integer> ptsCol;

    ObservableList<Team> list = FXCollections.observableArrayList(new Team("Ahly",12));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clubCol.setCellValueFactory(new PropertyValueFactory<Team,String>("name"));
        standingsTable.setItems(list);
    }

    public void blueQBack(MouseEvent event){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }
    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }
}

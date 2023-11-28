module edu.ainshams.egyptianleaguesystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ainshams.egyptianleaguesystem.ui to javafx.fxml;
    exports edu.ainshams.egyptianleaguesystem.ui;
}
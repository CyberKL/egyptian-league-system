module edu.ainshams.egyptianleaguesystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ainshams.egyptianleaguesystem.model to javafx.fxml;
    exports edu.ainshams.egyptianleaguesystem.model;
}
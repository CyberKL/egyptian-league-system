module edu.ainshams.egyptianleaguesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.datatype.jdk8;


    opens edu.ainshams.egyptianleaguesystem.ui to com.fasterxml.jackson.databind, javafx.fxml;
    opens edu.ainshams.egyptianleaguesystem.model to com.fasterxml.jackson.databind, javafx.fxml;
    exports edu.ainshams.egyptianleaguesystem.ui;
    exports edu.ainshams.egyptianleaguesystem.model;
}
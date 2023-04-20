module com.crude_engine {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.tasklist to javafx.fxml;
    opens com.model;


    exports com.tasklist;
    exports com.model;


}

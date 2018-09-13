package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sql.sqlConnectToDatabase;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectEditController implements Initializable {
    private String nazwa;
    private int currentProjectIndex;
    //@FXML private Label myLab;
    @FXML private AnchorPane main;
    @FXML private GridPane canvas;
    @FXML private Label projectName,grafik,desktop,mobile,webap,scrum,website,database,peon,boss,client_care;
    private sqlConnectToDatabase conn;

    @FXML
    protected void setMyLab()
    {
        projectName.setText(nazwa);
    }

    @FXML
    public void button_click(ActionEvent e) {
        Button b1 = (Button)e.getSource();
        String s = b1.getText();
        switch (s)
        {
            case "Dołącz Grafika":
                conn.addCrew("grafik",currentProjectIndex);
                break;
            case "Dołącz Desktopowca":
                conn.addCrew("desktop_developer",currentProjectIndex);
                break;
            case "Dołącz Aplikacje Mobilne":
                conn.addCrew("mobile_developwe",currentProjectIndex);
                break;
            case "Dołącz Aplikacje Webowe":
                conn.addCrew("web_application",currentProjectIndex);
                break;
            case "Dołącz Scrummastera":
                conn.addCrew("scrummaster",currentProjectIndex);
                break;
            case "Dołącz Webmastera":
                conn.addCrew("webmaster",currentProjectIndex);
                break;
            case "Dołącz Bazodanowca":
                conn.addCrew("database_expert",currentProjectIndex);
                break;
            case "Dołącz Analityka":
                conn.addCrew("programmer_analyst",currentProjectIndex);
                break;
            case "Dołącz Menedżera":
                conn.addCrew("project_manager",currentProjectIndex);
                break;
            case "Dołącz Obsługę Klienta":
                conn.addCrew("customer_advisitor",currentProjectIndex);
                break;
                default:
                    break;
        }
        fetchData();
    }

    @FXML
    private void fetchData()
    {
        grafik.setText("\t"+conn.projectCrew("grafik",currentProjectIndex));
        desktop.setText("\t"+conn.projectCrew("desktop_developer",currentProjectIndex));
        mobile.setText("\t"+conn.projectCrew("mobile_developwe",currentProjectIndex));
        webap.setText("\t"+conn.projectCrew("web_application",currentProjectIndex));
        scrum.setText("\t"+conn.projectCrew("scrummaster",currentProjectIndex));
        website.setText("\t"+conn.projectCrew("webmaster",currentProjectIndex));
        database.setText("\t"+conn.projectCrew("database_expert",currentProjectIndex));
        peon.setText("\t"+conn.projectCrew("programmer_analyst",currentProjectIndex));
        boss.setText("\t"+conn.projectCrew("project_manager",currentProjectIndex));
        client_care.setText("\t"+conn.projectCrew("customer_advisitor",currentProjectIndex));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwa = (String) resources.getObject("");
        setMyLab();
        String[] a = nazwa.split("-");
        currentProjectIndex = Integer.parseInt(a[1]);
        try {
            conn = new sqlConnectToDatabase();
            fetchData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

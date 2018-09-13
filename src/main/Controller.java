package main;

import client.clientInformation;
import employee.workingPerson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import project.projectView;
import sql.sqlConnectToDatabase;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML private AnchorPane content_scroll;
    @FXML private Label content,footer;
    @FXML private javafx.scene.control.TableView<workingPerson> employeeView;
    @FXML private javafx.scene.control.TableView<clientInformation> clientView;
    @FXML private HBox projectCrewView;
    @FXML private VBox projectName;
    @FXML private VBox projectDetails;
    @FXML private VBox projectDelete;
    @FXML private GridPane addEmployee,addClient,addProject;
    @FXML private TextField e_name,e_surname,e_mail,e_payment,c_name,c_surname,c_contact;
    @FXML private ChoiceBox e_rola,year,month,day,choose_cli;

    private sqlConnectToDatabase conn;

    @FXML
    protected void buttonAddEmployee(ActionEvent e)
    {
        if(e_name.getText().trim().isEmpty() || e_surname.getText().trim().isEmpty() || e_mail.getText().trim().isEmpty() || e_payment.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Wypełnij cały formularz");
            alert.showAndWait();
        }
        else
        {
            conn.executeQuery("INSERT INTO PROGRAMISTA VALUES((SELECT MAX(P_ID) FROM PROGRAMISTA)+1,'"+e_name.getText()+"','"+e_surname.getText()+"','"+e_mail.getText()+"',"+e_payment.getText()+",(select s_id from stanowisko where nazwa = '"+e_rola.getValue().toString()+"'))");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding Complete");
            alert.setHeaderText("Dodano nowego klienta");
            alert.showAndWait();
        }
    }

    @FXML
    public void buttonAddProject(ActionEvent actionEvent) {
        DateFormat rok = new SimpleDateFormat("yyyy");
        DateFormat miesiac = new SimpleDateFormat("MM");
        DateFormat dzien = new SimpleDateFormat("dd");
        Date date = new Date();
        if((((int)year.getValue()==Integer.parseInt(rok.format(date)))&&((int)month.getValue() < Integer.parseInt(miesiac.format(date)))) || (((int)year.getValue()==Integer.parseInt(rok.format(date)))&&((int)month.getValue() == Integer.parseInt(miesiac.format(date)))&&((int)day.getValue() < Integer.parseInt(dzien.format(date)))))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Data");
            alert.setHeaderText("Podałeś datę z przeszłości");
            alert.showAndWait();
        }
        else
        {
            String[] c_id = ((String)choose_cli.getValue()).split(" ");
            String q = "INSERT INTO PROJEKT VALUES((SELECT MAX(PROJEKT.PR_ID)+1 FROM PROJEKT) , "+c_id[0]+" , '"+String.valueOf(year.getValue())+"/"+String.valueOf(month.getValue())+"/"+String.valueOf(day.getValue())+"')";
            System.out.print(q);
            //footer.setText(q);

            conn.executeQuery(q);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ask Complete");
            alert.setHeaderText("Dodano nowy projekt");
            alert.showAndWait();
        }
    }

    @FXML
    protected void buttonAddClient(ActionEvent e)
    {
        if(c_contact.getText().trim().isEmpty() || c_name.getText().trim().isEmpty() || c_surname.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Wypełnij cały formularz");
            alert.showAndWait();
        }
        else {
            conn.executeQuery("INSERT into klient values((select MAX(k_id) from klient)+1,'"+c_name.getText()+"','"+c_surname.getText()+"','"+c_contact.getText()+"')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding Complete");
            alert.setHeaderText("Dodano nowego klienta");
            alert.showAndWait();
        }
    }

    @FXML
    protected void button_click(ActionEvent event)
    {
        Button clickedButton = (Button)event.getSource();
        String button = clickedButton.getText();
        switch (button){
            case "Wyświetl projekty":
                viewProjects();
                break;
            case "Wyświetl pracowników":
                viewEmployees();
                break;
            case "Zarządzaj projektami":
                showCrew();
                break;
            case "Wyświetl klientów":
                viewClients();
                break;
            case "Nowy pracownik":
                newEmployee();
                break;
            case "Nowy projekt":
                newProject();
                break;
            case "Nowy klient":
                newClient();
                break;
        }
    }

    @FXML
    protected void projectToSet(ActionEvent event,int id)
    {

    }

    @FXML
    protected void mainContent(){
        content.setText("Witaj\n\n \tAplikacja służy do obsługi firmy wykonującej zlecenia programistyczne z różnych dziedzin.\n" +
                "Pozwoli ci dobrać odpowiednich ludzi do projektów oraz automatycznie zwiększy ich wynagrodzenie\nza udział w projekcie");
    }

    @FXML
    protected void viewProjects(){
        String[] query = conn.listProjects();
        setInvisible();
        content.setVisible(true);
        content_scroll.setPrefHeight(150);
        content.setPrefHeight(150);
        content.setText("Nazwy projektów są w formie: \n\t null-ID_projektu-Imie_klienta-Nazwisko_klienta-Telefon_kontaktowy-Data_zakończenia\n");
        for(int i=0;i<query.length;i++)
        {
            if(query[i]!=null) {

                content.setText(content.getText() + '\n' + query[i]);
                content.setPrefHeight(content.getPrefHeight()+18);
                content_scroll.setPrefHeight(content_scroll.getPrefHeight()+18);
            }
        }
    }

    @FXML
    protected void viewEmployees(){
        setInvisible();
        employeeView.setVisible(true);
        ObservableList<workingPerson> query = conn.listEmployees();
        content_scroll.setPrefHeight(150);
        content.setPrefHeight(150);
        employeeView.setItems(query);
        //employeeView.getColumns().
    }

    @FXML
    protected void viewClients()
    {
        setInvisible();
        clientView.setVisible(true);
        ObservableList<clientInformation> query = conn.listClients();
        content_scroll.setPrefHeight(150);
        content.setPrefHeight(150);
        clientView.setItems(query);
    }

    @FXML
    protected void showCrew()
    {
        setInvisible();
        projectCrewView.setVisible(true);
        Button edit[];
        Button end[];
        Label projects[];
        String[] query = conn.listProjects();
        projectName.getChildren().remove(0,projectName.getChildren().size());
        projectDetails.getChildren().remove(0,projectDetails.getChildren().size());
        projectDelete.getChildren().remove(0,projectDelete.getChildren().size());
        projects = new Label[query.length];
        edit = new Button[query.length];
        end = new Button[query.length];
        for(int i=0;i<query.length;i++)
        {
            if(query[i]!=null) {
                String pr_id[] = query[i].split("-");
                projects[i] = new Label(query[i]);
                edit[i] = new Button("Modyfikuj");
                end[i] = new Button("Zakończ");
                projects[i].setStyle("" +
                        "-fx-pref-height: 27; " +
                        "-fx-padding: 0,0,0,0; " +
                        "-fx-background-color: #ffffff; " +
                        "-fx-pref-width: 550; " +
                        "-fx-border-style: solid; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-color: black;");
                edit[i].setStyle("-fx-pref-height: 25;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-color: black;" +
                        "-fx-text-fill: #006E16;" +
                        "-fx-pref-width: 90;");
                end[i].setStyle("-fx-pref-height: 25;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-style: solid;"+
                        "-fx-border-color: black;" +
                        "-fx-text-fill: #6E0002;" +
                        "-fx-pref-width:90;");
                projectName.getChildren().add(projects[i]);
                projectDetails.getChildren().add(edit[i]);
                projectDelete.getChildren().add(end[i]);
                edit[i].setUserData(query[i]);
                end[i].setUserData(pr_id[0]);
                edit[i].setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Button pressed = (Button) event.getSource();
                                String currentProject = (String)pressed.getUserData();

                                projectView poka = new projectView(currentProject);
                            }
                        }
                );
                end[i].setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Button pressed = (Button) event.getSource();
                                Label newLabel = new Label((String)pressed.getUserData());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Potwierdzenie usunięcia projektu");
                                alert.setHeaderText("Czy na pewno chcesz zakończyć ten projekt ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK){
                                    conn.executeQuery("DELETE FROM PROJEKT WHERE PROJEKT.PR_ID = "+(String)pressed.getUserData());
                                } else {
                                    // ... user chose CANCEL closed the dialog
                                }
                            }
                        }
                );
            }
        }
    }

    @FXML
    protected void newEmployee()
    {
        e_rola.getItems().add("grafik");
        e_rola.getItems().add("desktop_developer");
        e_rola.getItems().add("mobile_developwe");
        e_rola.getItems().add("web_application");
        e_rola.getItems().add("scrummaster");
        e_rola.getItems().add("webmaster");
        e_rola.getItems().add("database_expert");
        e_rola.getItems().add("programmer_analyst");
        e_rola.getItems().add("project_manager");
        e_rola.getItems().add("customer_advisitor");
        e_rola.setValue("programmer_analyst");
        setInvisible();
        addEmployee.setVisible(true);
    }

    @FXML
    protected void newProject()
    {
        DateFormat rok = new SimpleDateFormat("yyyy");
        DateFormat miesiac = new SimpleDateFormat("MM");
        DateFormat dzien = new SimpleDateFormat("dd");
        Date date = new Date();
        setInvisible();
        for(int i = Integer.parseInt(rok.format(date));i<Integer.parseInt(rok.format(date))+10;i++)
        {
            year.getItems().add(i);
        }
        for(int i=1;i<=12;i++)
        {
            month.getItems().add(i);
        }
        for(int i=1;i<=31;i++)
        {
            day.getItems().add(i);
        }
        year.setValue(Integer.parseInt(rok.format(date)));
        month.setValue(Integer.parseInt(miesiac.format(date)));
        day.setValue(Integer.parseInt(dzien.format(date)));
        String[] s = conn.getClients();
        choose_cli.getItems().addAll(s);
        choose_cli.setValue(s[0]);
        addProject.setVisible(true);

    }

    @FXML
    protected void newClient()
    {
        setInvisible();
        addClient.setVisible(true);
    }


    @FXML
    protected void setInvisible()
    {
        content.setVisible(false);
        employeeView.setVisible(false);
        projectCrewView.setVisible(false);
        clientView.setVisible(false);
        addEmployee.setVisible(false);
        addClient.setVisible(false);
        addProject.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainContent();
        try {
            conn = new sqlConnectToDatabase();
            content.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
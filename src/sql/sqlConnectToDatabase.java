package sql;

import client.clientInformation;
import employee.workingPerson;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class sqlConnectToDatabase {
        private Connection conn;
        private Statement st;
        private ResultSet rs;
        public sqlConnectToDatabase() throws SQLException,ClassNotFoundException{
            conn = null;
            st = null;
            rs = null;

            String dbURL = "jdbc:oracle:thin:@localhost:1521:ora2017";
            String user = "G1_96102601298";
            String pw = "Pbkamil";

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(dbURL, user, pw);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public String[] listProjects()
        {
            String[] qResult = new String[1000];
            int i=0;
            try {
                st = conn.createStatement();
                rs = st.executeQuery("SELECT PROJEKT.PR_ID,KLIENT.IMIE,KLIENT.NAZWISKO,KLIENT.KONTAKT,PROJEKT.ROLLOUT FROM PROJEKT JOIN KLIENT ON KLIENT.K_ID=projekt.KLIENT_K_ID ORDER BY ROLLOUT");
                while(rs.next())
                {
                    qResult[i] += "-";
                    qResult[i] += rs.getString("PR_ID");
                    qResult[i] += "-";
                    qResult[i] += rs.getString("IMIE");
                    qResult[i] += "-";
                    qResult[i] += rs.getString("NAZWISKO");
                    qResult[i] += "-";
                    qResult[i] += rs.getString("KONTAKT");
                    qResult[i] += "-";
                    qResult[i] += rs.getString("ROLLOUT");
                    qResult[i] += "-";
                    i++;
                }
                return qResult;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return qResult;
        }

        public void executeQuery(String q)
        {
            try {
                st = conn.createStatement();
                rs = st.executeQuery(q);
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }

        public void addCrew(String role,int project)
        {
            try{
                st = conn.createStatement();
                rs=st.executeQuery("select  programista.p_id,count(PRZYPISANIE_DO_PROJEKTOW.PROGRAMISTA_P_ID) as projekty from programista left join PRZYPISANIE_DO_PROJEKTOW on PRZYPISANIE_DO_PROJEKTOW.PROGRAMISTA_P_ID = programista.p_id join STANOWISKO on STANOWISKO.S_ID = programista.STANOWISKO_S_ID where stanowisko.nazwa='"+role+"' group by programista.p_id order by projekty");
                rs.next();
                String toAdd = rs.getString("P_ID");
                rs=st.executeQuery("INSERT into PRZYPISANIE_DO_PROJEKTOW values("+project+","+toAdd+")");

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        public String projectCrew(String role,int project)
        {
            String qRes = "";
            try {
                st = conn.createStatement();
                rs = st.executeQuery("select programista.imie,programista.nazwisko from programista join PRZYPISANIE_DO_PROJEKTOW on PRZYPISANIE_DO_PROJEKTOW.PROGRAMISTA_P_ID = programista.P_ID join STANOWISKO on programista.STANOWISKO_S_ID = STANOWISKO.S_ID where PRZYPISANIE_DO_PROJEKTOW.PROJEKT_PR_ID = "+project+" AND STANOWISKO.NAZWA = '"+role+"'");
                while(rs.next())
                {
                    qRes += rs.getString("IMIE")+"_"+rs.getString("NAZWISKO")+" | ";
                }
                return qRes;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return "xd";
        }

        public ObservableList<workingPerson> listEmployees(){
            ObservableList<workingPerson> qResult = FXCollections.observableArrayList();
            int i=0;
            try {
                st = conn.createStatement();
                rs = st.executeQuery("SELECT IMIE,NAZWISKO,ADRES,PENSJA,STANOWISKO.NAZWA FROM PROGRAMISTA JOIN STANOWISKO ON PROGRAMISTA.STANOWISKO_S_ID = STANOWISKO.S_ID ORDER BY PENSJA");
                while(rs.next())
                {
                    qResult.add(new workingPerson(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
                return qResult;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return qResult;
        }

    public ObservableList<clientInformation> listClients(){
        ObservableList<clientInformation> qResult = FXCollections.observableArrayList();
        int i=0;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT IMIE,NAZWISKO,KONTAKT FROM KLIENT ORDER BY IMIE");
            while(rs.next())
            {
                qResult.add(new clientInformation(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            return qResult;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return qResult;
    }

    public String[] getClients() {
            String[] qRes;
            String[] a =  new String[1];
            int i=0;
            try{
                st = conn.createStatement();
                rs = st.executeQuery("select K_ID from klient");
                int size = 0;
                while(rs.next())
                {
                    size++;
                }
                qRes = new String[size];
                rs = st.executeQuery("select k_id,imie,nazwisko from klient order by k_id");
                while(rs.next())
                {
                    qRes[i] = rs.getString("K_ID")+" "+rs.getString("IMIE")+" "+rs.getString("NAZWISKO");
                    i++;
                }
                return qRes;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return a;
    }
}


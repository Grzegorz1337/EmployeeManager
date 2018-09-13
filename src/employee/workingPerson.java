package employee;

import java.awt.*;

public class workingPerson {
    private String firstName;
    private String lastName;
    private String email;
    private String payment;
    private String rank;

    public workingPerson(String firstName, String lastName, String email, String payment, String rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.payment = payment;
        this.rank = rank;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

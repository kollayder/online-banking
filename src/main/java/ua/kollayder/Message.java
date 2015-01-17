package ua.kollayder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static ua.kollayder.BankBean.roundCost;


@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue
    private int id;
    @Lob
    private String description;
    private Double sum;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "accountNum")
    @NotFound(action = NotFoundAction.IGNORE)
    private Person person;

    public Message() {
    }

    public Message(String description, Double sum, Date date, Person person) {
        this.description = description;
        this.sum = sum;
        this.date = date;
        this.person = person;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        sum = roundCost(sum);
        this.sum = sum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

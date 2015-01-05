package ua.kollayder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static ua.kollayder.BankBean.roundCost;

/**
 * Created by Nastya_G on 01.12.2014.
 */
@Entity
public class Person implements Comparable<Person>, Comparator<Person>, Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    private String accountNum;
    private String surname;
    private String name;
    private String password;
    private Double sum;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Collection<Message> messages = new ArrayList<Message>();

    public Person() {
    }

    public Person(String accountNum, String surname, String name, String password, Double sum) {
        this.accountNum = accountNum;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.sum = sum;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        sum = roundCost(sum);
        this.sum = sum;
    }


    @Override
    public int compareTo(Person p) {
        return this.getSurname().compareTo(p.getSurname());
    }

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getSurname().compareTo(o1.getSurname());
    }
}

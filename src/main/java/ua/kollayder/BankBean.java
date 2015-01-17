package ua.kollayder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "bank")
@SessionScoped
public class BankBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNum;
    private String surname;
    private String name;
    private String password;
    private String pass2;
    private Double sum;
    private Double subtractSum;
    private Double addSum;
    private Person person;
    private String toPerson = null;
    private List<Person> persons;
    private List<Message> messages;
    private Message message;
    private String info;
    @ManagedProperty(value = "#{personImpl}")
    private PersonDAO personService;

    public String loginCheck() {
        Person p = personService.getPerson(accountNum);
        if (p != null) {
            if (p.getPassword().equals(password)) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("client", p);
                person = p;
                message = new Message();
                clear(accountNum, password);
                return "clientpage?faces-redirect=true";
            } else {
                clear(accountNum, password);
                return "login?faces-redirect=true";
            }
        }
        clear(accountNum, password);
        return "login?faces-redirect=true";
    }

    public String getAuthCheck() {
        if (password != null) {
            Person p = personService.getPerson(accountNum);
            if (p != null) {
                if (!p.getPassword().equals(password)) {
                    return "Невірний рахунок чи пароль";
                } else return null;
            } else return null;
        } else
            return null;
    }

    public String logout() {
        person = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public String registrated() {
        if (personService.getPerson(accountNum) == null) {
            person = new Person(accountNum, surname, name, password, sum);
            personService.addPerson(person);
            clear(accountNum, surname, name, password, sum);
            return "registrated?faces-redirect=false";
        } else return "login";
    }

    public String getNumbCheck() {
        if (accountNum != null) {
            if (personService.getPerson(accountNum) != null) {
                return "Такий номер рахунку уже зареєстровано!";
            } else return null;
        } else return null;
    }

    public void clear(Object... o) {
        for (Object obj : o) {
            obj = null;
        }

    }

    public String moneyTransfer() {
        if (toPerson != null) {
            Double add = subtractSum;
            String descr = "Здійснено переказ на рахунок № ";
            String descr2 = "Отримано переказ від ";
            if (person.getSum() >= subtractSum) {
                person.setSum(substraction(person, subtractSum));
                personService.updatePerson(person);
                subtractSum = null;
                Person p = personService.getPerson(toPerson);
                p.setSum(addition(p, add));
                personService.updatePerson(p);
                personService.addMessage(new Message(descr + p.getAccountNum()
                        + ", " + p.getSurname() + " " + p.getName(),
                        add, new Date(), person));
                personService.addMessage(new Message(descr2 + person.getSurname() + " " + person.getName()
                        + ", " + person.getAccountNum(),
                        add, new Date(), p));
                setInfo("Переказ успішний");
                return "clientpage";
            } else {
                setInfo("Недостатньо коштів на рахунку");
                subtractSum = null;
                return "clientpage";
            }
        } else {
            subtractSum = null;
            return null;
        }

    }

    public String income() {
        if (addSum != null) {
            person.setSum(addition(person, addSum));
            personService.updatePerson(person);
            personService.addMessage(new Message("Поповнення поточного рахунку",
                    addSum, new Date(), person));
            addSum = null;
            setInfo("Операція успішна");
            return "clientpage";
        } else {
            return "";
        }
    }


    public String writeOff() {
        if (subtractSum != null) {
            if (person.getSum() >= subtractSum) {
                person.setSum(substraction(person, subtractSum));
                personService.updatePerson(person);
                personService.addMessage(new Message(message.getDescription(), subtractSum, new Date(), person));
                message.setDescription(null);
                subtractSum = null;
                setInfo("Операція успішна");
                return "clientpage";
            } else {
                setInfo("Недостатньо коштів на рахунку");
                subtractSum = null;
                return "clientpage";
            }
        }
        subtractSum = null;
        return "";
    }

    private Double addition(Person p, Double amount) {
        Double result;
        result = p.getSum() + amount;
        return result;

    }

    private Double substraction(Person p, Double amount) {
        Double result;
        if (p.getSum() >= amount) {
            result = p.getSum() - amount;
            return result;
        } else return amount;
    }

    public static double roundCost(double cost) {
        return ((double) Math.round(cost * 100)) / 100;
    }


//    @PreDestroy
//    private void destr(){
//        PersonDAOImpl.getSession().close();
//    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Message> getMessages() {
        messages = personService.getAllMessages(person);
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
        this.sum = roundCost(sum);
    }

    public Double getSubtractSum() {
        return subtractSum;
    }

    public void setSubtractSum(Double subtractSum) {
        this.subtractSum = roundCost(subtractSum);
    }

    public Double getAddSum() {
        return addSum;
    }

    public void setAddSum(Double addSum) {
        this.addSum = roundCost(addSum);
    }

    public Person getPerson() {

        return person;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    public List<Person> getPersons() {
        persons = personService.getAllPersons();
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public PersonDAO getPersonService() {
        return personService;
    }

    public void setPersonService(PersonDAO personService) {
        this.personService = personService;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getToPerson() {
        return toPerson;
    }

    public void setToPerson(String toPerson) {
        this.toPerson = toPerson;
    }
}

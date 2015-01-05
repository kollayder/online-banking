package ua.kollayder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nastya_G on 01.12.2014.
 */
public class PersonDaoMock implements PersonDAO, Serializable {
    private static final long serialVersionUID = 1L;
    private List<Person> personList;
    private List<Message> messages;

    {
        messages = new ArrayList<Message>();
        personList = new ArrayList<Person>();
        personList.add(new Person("1234340090", "Petrov", "Ivan", "111111", 500d));
        personList.add(new Person("8764340090", "Gadov", "Ibragim", "111111", 5600d));
        personList.add(new Person("1234376590", "Badoeva", "Janna", "111111", 5800d));
        personList.add(new Person("0000000001", "Герман", "Настя", "111111", 1000d));
    }

    @Override
    public Person getPerson(String id) {
        Person person = null;
        for (Person p : personList) {
            if (p.getAccountNum().equals(id)) {
                person = p;
            }
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        Collections.sort(personList);
        return personList;
    }

    @Override
    public void addPerson(Person person) {
        personList.add(person);
    }

    @Override
    public void updatePerson(Person person) {
        Person del = getPerson(person.getAccountNum());
        personList.remove(del);
        personList.add(person);
    }

    @Override
    public List<Message> getAllMessages(Person p) {
        List<Message> temp = new ArrayList<>();
        for (Message m : messages) {
            if (m.getPerson().equals(p)) {
                temp.add(m);
            }
        }
        Collections.reverse(temp);
        return temp;
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }

}

package ua.kollayder;

import java.util.List;

/**
 * Created by Nastya_G on 01.12.2014.
 */
public interface PersonDAO  {
    public Person getPerson(String id);
    public List<Person> getAllPersons();
    public void addPerson(Person person);
    public void updatePerson (Person person);
    public List<Message> getAllMessages(Person p);
    public void addMessage(Message message);
}

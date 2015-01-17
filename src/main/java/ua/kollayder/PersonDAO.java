package ua.kollayder;

import java.util.List;

public interface PersonDAO  {
    public Person getPerson(String id);
    public List<Person> getAllPersons();
    public void addPerson(Person person);
    public void updatePerson (Person person);
    public List<Message> getAllMessages(Person p);
    public void addMessage(Message message);
}

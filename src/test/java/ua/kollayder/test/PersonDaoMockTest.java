package ua.kollayder.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.kollayder.Person;
import ua.kollayder.PersonDaoMock;

import java.util.List;

import static org.junit.Assert.*;

public class PersonDaoMockTest extends Assert {
    PersonDaoMock pd;
    Person p;

    @Before
    public void setUp() throws Exception {
        pd = new PersonDaoMock();
        p = new Person("0000000001", "Герман", "Настя", "111111", 1000d);
    }

    @Test
    public void testGetPerson() throws Exception {
        Person p2 = pd.getPerson("0000000001");
        assertEquals(p, p2);
    }

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> personList = pd.getAllPersons();
        assertTrue(personList.contains(p));
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person("1111111111", "Dane", "Jack", "888888", null);
        pd.addPerson(person);
        List<Person> personList = pd.getAllPersons();
        assertTrue(personList.contains(person));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = new Person("0000000001", "Бугай", "Бабай", "888888", 234d);
        pd.updatePerson(person);
        person = pd.getPerson("0000000001");
        assertEquals("Бугай", person.getSurname());
        assertEquals("Бабай", person.getName());
        assertEquals("888888", person.getPassword());
        assertEquals(new Double(234), person.getSum());

    }


}
package ua.kollayder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class PersonDAOImpl implements PersonDAO {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {

            return new AnnotationConfiguration()
                    .addPackage("ua.kollayder")
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(Message.class)
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    @Override
    public Person getPerson(String id) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        Person p = (Person) session.get(Person.class, id);
        session.close();
        return p;

    }

    @Override
    public List<Person> getAllPersons() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<Person> list = (List<Person>) session.createQuery("from ua.kollayder.Person").list();
        session.close();

        return list;

    }

    @Override
    public void addPerson(Person person) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updatePerson(Person person) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        session.update(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Message> getAllMessages(Person p) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<Message> list = session.createCriteria(Message.class)
                .add(Restrictions.like("person", p))
                .addOrder(Order.desc("date")).list();
        session.close();
        return list;
    }

    @Override
    public void addMessage(Message message) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        session.save(message);
        session.getTransaction().commit();
        session.close();

    }
}

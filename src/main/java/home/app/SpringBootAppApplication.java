package home.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SpringBootApplication
public class SpringBootAppApplication
        implements CommandLineRunner {
    @Autowired PersonRepository ob;
    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(PersonJPA.class);
        try (SessionFactory sessionFactory
                     = configuration.buildSessionFactory()) {

        Session session = sessionFactory.openSession();

        // Inserting the data in the mysql table.
        PersonJPA first = new PersonJPA(1, "Igor",30);
        // ob.save() method
        ob.save(first);

        first.setId(1);
        first.setName("Vova");
        first.setAge(51);

        session.beginTransaction();

        // Here we have used
        // persist() method of JPA
        session.persist(first);

        session.getTransaction().commit();

        ob.deleteById(1);
        }
    }
}
package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.List;


public class Db {
    /*private static final String url = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "159159";*/

    public static void con(){

        /** Для работы без метода Conntcnor
        /*try(Connection con = DriverManager.getConnection(url,USER,PASSWORD)){
            Statement statment = con.createStatement();
            ResultSet set = statment.executeQuery("Select * from person.t_person;");
            while (set.next()){
                System.out.println(set.getString(3)+" " + set.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;*/

        Connector connector = new Connector();

        try(Session session = connector.getSession()){


            Person person = new Person("Коля", 43);
            session.beginTransaction();
            session.save(person);

            person = new Person("Дима", 18);
            session.save(person);

            person = new Person("Олеся", 25);
            session.save(person);

            session.getTransaction().commit();

            List<Person> list = session.createQuery("FROM Person", Person.class).getResultList();
            list.forEach(System.out::println);
            } catch (Exception e) {
            throw new RuntimeException(e);
        };
        }

    }


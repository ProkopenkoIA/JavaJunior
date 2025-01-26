package home.app;

public interface PersonService {

    PersonJPA savePerson(PersonJPA person);

    PersonJPA updatePerson(PersonJPA person, Integer id);
    // delete operation
    void deletePersonById(Integer id);
}

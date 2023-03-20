package ru.derendyaev.CRUD.DAO;

import org.springframework.stereotype.Component;
import ru.derendyaev.CRUD.models.Person;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person((++PEOPLE_COUNT), "Tom", 18, "mail"));
        people.add(new Person((++PEOPLE_COUNT), "Bob", 25, "gmail"));
        people.add(new Person((++PEOPLE_COUNT), "Mike", 10, "yandex"));
        people.add(new Person((++PEOPLE_COUNT), "Katy", 100, "Yahoo"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id){
        return  people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}

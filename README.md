# CRUD Application
## lesson 21 - CRUD, REST, Паттерн DAO (Data Access Object)

- Разработана иерархия каталогов, объединяющих отдельные модули:
  - config - конфигурация Spring Application
  - controllers - расположение контроллеров, принимающих запросы
  - DAO(Data Access Object) - классы, определяющие доступ к данным, использующие модели `models` из соответствующего каталога
  - models - классы, определяющеие то, как выглядят объекты данных, в дальнейшем сущностей БД

- Через конструктор внедрена зависимость `PersonDAO`:
```java
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
```
- Которая в свою очередь является сущностью класса PersonDAO и обладает методами, при вызове которого происхдит возврат данных для загрузки в `Model` для передачи во `View`
```java
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        // Получим всех людей из DAO и передадим на отображение в представление
        return "people/index";
    }
```
- Сам Data Access Object хранит в себе пока список с данными по пользователях, содержащий в себе объекты класса `Person`. В классе также есть методы, возвращающие объекты или строчное представление данных:
```java
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person((++PEOPLE_COUNT), "Tom"));
        people.add(new Person((++PEOPLE_COUNT), "Bob"));
        people.add(new Person((++PEOPLE_COUNT), "Mike"));
        people.add(new Person((++PEOPLE_COUNT), "Katy"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id){
        return  people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
```

- Для вывода происходит обращение к шаблонизатору Thymeleaf, который использует следующую конструкцию:  
```html
<div th:each="person: ${people}">
  <a th:href="@{/people/{id}(id=${person.getId()})}"  th:text="${person.getName()}"> User</a>
</div>
```
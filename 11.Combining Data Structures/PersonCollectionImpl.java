import java.util.*;

public class PersonCollectionImpl implements PersonCollection {

    private Map<String, Person> byEmail;
    private Map<String, Set<Person>> byDomain;
    private Map<String, Set<Person>> byNameAndTown;
    private TreeMap<Integer, Set<Person>> byAge;
    private TreeMap<Integer, TreeMap<String, Set<Person>>> byAgeAndTown;


    public PersonCollectionImpl() {
        this.byEmail = new HashMap<>();
        this.byDomain = new HashMap<>();
        this.byNameAndTown = new HashMap<>();
        this.byAge = new TreeMap<>();
        this.byAgeAndTown = new TreeMap<>();
    }

    @Override
    public boolean addPerson(String email, String name, int age, String town) {
        if (this.byEmail.containsKey(email)) {
            return false;
        }
        Person person = new Person(email, name, age, town);

        this.byEmail.put(email, person);

        String domain = email.split("@")[1];
        this.byDomain.putIfAbsent(domain, new TreeSet<>());
        this.byDomain.get(domain).add(person);

        String key = name.concat(town);
        this.byNameAndTown.putIfAbsent(key, new TreeSet<>());
        this.byNameAndTown.get(key).add(person);


        this.byAge.putIfAbsent(age, new TreeSet<>());
        this.byAge.get(age).add(person);

        this.byAgeAndTown.putIfAbsent(age, new TreeMap<>());
        this.byAgeAndTown.get(age).putIfAbsent(town, new TreeSet<>());
        this.byAgeAndTown.get(age).get(town).add(person);
        return true;
    }

    @Override
    public int getCount() {
        return this.byEmail.size();
    }

    @Override
    public Person findPerson(String email) {
        return this.byEmail.getOrDefault(email, null);
    }

    @Override
    public boolean deletePerson(String email) {
        if (!this.byEmail.containsKey(email)) {
            return false;
        }
        String domain = email.split("@")[1];
        Person person = this.byEmail.get(email);
        this.byDomain.get(domain).remove(person);

        String key = person.getName().concat(person.getTown());
        this.byNameAndTown.get(key).remove(person);

        this.byAge.remove(person.getAge());

        this.byEmail.remove(email);

        this.byAgeAndTown.get(person.getAge()).get(person.getTown()).remove(person);
        return true;
    }

    @Override
    public Iterable<Person> findPersons(String emailDomain) {
        if (!this.byDomain.containsKey(emailDomain)) {
            return new TreeSet<>();
        }
        return this.byDomain.get(emailDomain);
    }

    @Override
    public Iterable<Person> findPersons(String name, String town) {
        if (town == null || !this.byNameAndTown.containsKey(name.concat(town))) {
            return new TreeSet<>();
        }
        return this.byNameAndTown.get(name.concat(town));
    }

    @Override
    public Iterable<Person> findPersons(int startAge, int endAge) {
        Set<Person> result = new LinkedHashSet<>();
        this.byAge.subMap(startAge, true, endAge, true)
                .forEach((key, value) -> result.addAll(value));
        return result;
    }

    @Override
    public Iterable<Person> findPersons(int startAge, int endAge, String town) {
        List<Person> result = new ArrayList<>();
        this.byAgeAndTown.subMap(startAge, true, endAge, true)
                .forEach((key, value) -> {
            if (value.containsKey(town)) {
                result.addAll(value.get(town));
            }
        });
        return result;
    }
}

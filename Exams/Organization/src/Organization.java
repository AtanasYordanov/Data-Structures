import sun.awt.image.ImageWatched;

import java.util.*;

public class Organization implements IOrganization {

    private int count;
    private List<Person> employees;
    private Set<Person> employeesSet;
    private Map<String, List<Person>> employeesByName;
    private TreeMap<Integer, List<Person>> employeesByNameLength;


    public Organization() {
        this.count = 0;
        this.employees = new ArrayList<>();
        this.employeesSet = new HashSet<>();
        this.employeesByName = new LinkedHashMap<>();
        this.employeesByNameLength = new TreeMap<>();
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean contains(Person person) {
        return this.employeesSet.contains(person);
    }

    @Override
    public boolean containsByName(String name) {
        return this.employeesByName.containsKey(name);
    }

    @Override
    public void add(Person person) {
        this.employees.add(person);
        this.employeesSet.add(person);

        String name = person.getName();
        this.employeesByName.putIfAbsent(name, new ArrayList<>());
        this.employeesByName.get(name).add(person);

        int nameLength = name.length();
        this.employeesByNameLength.putIfAbsent(nameLength, new ArrayList<>());
        this.employeesByNameLength.get(nameLength).add(person);

        this.count++;
    }

    @Override
    public Person getAtIndex(int index) {
        if (this.count <= index){
            throw new IllegalArgumentException();
        }
        return this.employees.get(index);
    }

    @Override
    public Iterable<Person> getByName(String name) {
        this.employeesByName.putIfAbsent(name, new ArrayList<>());
        return this.employeesByName.get(name);
    }

    @Override
    public Iterable<Person> firstByInsertOrder() {
        if (this.count == 0){
            throw new IllegalArgumentException();
        }
        return this.employees.subList(0, 1);
    }

    @Override
    public Iterable<Person> firstByInsertOrder(int count) {
        if (this.count < count){
            return this.employees.subList(0, this.count);
        }
        return this.employees.subList(0, count);
    }

    @Override
    public Iterable<Person> searchWithNameSize(int minLength, int maxLength) {
        List<Person> result = new ArrayList<>();
        this.employeesByNameLength
                .subMap(minLength, true, maxLength, true)
                .forEach((key, value) -> result.addAll(value));
        return result;
    }

    @Override
    public Iterable<Person> getWithNameSize(int length) {
        if (!this.employeesByNameLength.containsKey(length)){
            throw new IllegalArgumentException();
        }
        return this.employeesByNameLength.get(length);
    }

    @Override
    public Iterable<Person> peopleByInsertOrder() {
        return this.employees;
    }

    @Override
    public Iterator<Person> iterator() {
        return this.employees.iterator();
    }
}

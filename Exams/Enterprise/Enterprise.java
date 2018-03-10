import java.util.*;

public class Enterprise implements IEnterprise {

    private int count;
    private Map<UUID, Employee> employeesByUUID;
    private Map<String, List<Employee>> employeesByPosition;
    private Map<String, List<Employee>> employeesByFirstName;
    private TreeMap<Double, List<Employee>> employeesBySalary;
    private TreeMap<Date, List<Employee>> employeesByHireDate;

    public Enterprise() {
        this.count = 0;
        this.employeesByUUID = new LinkedHashMap<>();
        this.employeesByPosition = new LinkedHashMap<>();
        this.employeesByFirstName = new LinkedHashMap<>();
        this.employeesBySalary = new TreeMap<>();
        this.employeesByHireDate = new TreeMap<>();
    }

    @Override
    public void add(Employee employee) {
        this.employeesByUUID.putIfAbsent(employee.getId(), employee);

        String position = employee.getPosition().name();
        this.employeesByPosition.putIfAbsent(position, new ArrayList<>());
        this.employeesByPosition.get(position).add(employee);

        String firstName = employee.getFirstName();
        this.employeesByFirstName.putIfAbsent(firstName, new ArrayList<>());
        this.employeesByFirstName.get(firstName).add(employee);

        double salary = employee.getSalary();
        this.employeesBySalary.putIfAbsent(salary, new ArrayList<>());
        this.employeesBySalary.get(salary).add(employee);

        Date date = employee.getHireDate();
        this.employeesByHireDate.putIfAbsent(date, new ArrayList<>());
        this.employeesByHireDate.get(date).add(employee);

        this.count++;
    }

    @Override
    public boolean contains(UUID id) {
        return this.employeesByUUID.containsKey(id);
    }

    @Override
    public boolean contains(Employee employee) {
        return this.employeesByUUID.containsKey(employee.getId());
    }

    @Override
    public boolean change(UUID id, Employee employee) {
        if (this.employeesByUUID.containsKey(id)){
            Employee currentEmployee = this.employeesByUUID.get(id);
            if (!currentEmployee.getFirstName().equals(employee.getFirstName())){
                this.employeesByFirstName.get(currentEmployee.getFirstName()).remove(currentEmployee);
                currentEmployee.setFirstName(employee.getFirstName());
                this.employeesByFirstName.putIfAbsent(currentEmployee.getFirstName(), new ArrayList<>());
                this.employeesByFirstName.get(currentEmployee.getFirstName()).add(currentEmployee);
            }
            if (!currentEmployee.getLastName().equals(employee.getLastName())){
                currentEmployee.setLastName(employee.getLastName());
            }
            if (!currentEmployee.getPosition().name().equals(employee.getPosition().name())){
                this.employeesByPosition.get(currentEmployee.getPosition().name()).remove(currentEmployee);
                currentEmployee.setPosition(employee.getPosition());
                this.employeesByPosition.putIfAbsent(currentEmployee.getPosition().name(), new ArrayList<>());
                this.employeesByPosition.get(currentEmployee.getPosition().name()).add(currentEmployee);
            }
            if (currentEmployee.getSalary() != employee.getSalary()){
                this.employeesBySalary.get(currentEmployee.getSalary()).remove(currentEmployee);
                currentEmployee.setSalary(employee.getSalary());
                this.employeesBySalary.putIfAbsent(currentEmployee.getSalary(), new ArrayList<>());
                this.employeesBySalary.get(currentEmployee.getSalary()).add(currentEmployee);
            }
            if (!currentEmployee.getHireDate().equals(employee.getHireDate())){
                this.employeesByHireDate.get(currentEmployee.getHireDate()).remove(employee);
                currentEmployee.setHireDate(employee.getHireDate());
                this.employeesByHireDate.putIfAbsent(currentEmployee.getHireDate(), new ArrayList<>());
                this.employeesByHireDate.get(currentEmployee.getHireDate()).add(employee);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean fire(UUID id) {
        if (!this.employeesByUUID.containsKey(id)){
            return false;
        }
        Employee employee = this.employeesByUUID.get(id);
        this.employeesByUUID.remove(id);
        this.employeesByPosition.get(employee.getPosition().name()).remove(employee);
        this.employeesByFirstName.get(employee.getFirstName()).remove(employee);
        this.employeesBySalary.get(employee.getSalary()).remove(employee);
        this.employeesByHireDate.get(employee.getHireDate()).remove(employee);

        this.count--;
        return true;
    }

    @Override
    public boolean raiseSalary(int months, int percent) {
        if (this.count <= 0) {
            return false;
        }
        Date fromDate = this.employeesByHireDate.firstKey();
        Date toDate = new Date(2017, 7, 15);

        this.employeesByHireDate.subMap(fromDate, true, toDate, true)
                .values().forEach(list -> list.forEach(em -> {
                    double oldSalary = em.getSalary();
                    double newSalary = oldSalary + (oldSalary*percent)/100;
                    em.setSalary(newSalary);
                    this.employeesBySalary.get(oldSalary).remove(em);
                    this.employeesBySalary.putIfAbsent(newSalary, new ArrayList<>());
                    this.employeesBySalary.get(newSalary).add(em);
        }));
        return true;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public Employee getByUUID(UUID id) {
        if (!this.employeesByUUID.containsKey(id)){
            throw new IllegalArgumentException();
        }
        return this.employeesByUUID.get(id);
    }

    @Override
    public Position positionByUUID(UUID id) {
        if (!this.employeesByUUID.containsKey(id)){
            throw new IllegalArgumentException();
        }
        return this.employeesByUUID.get(id).getPosition();
    }

    @Override
    public Iterable<Employee> getByPosition(Position position) {
        if (!this.employeesByPosition.containsKey(position.name())){
            throw new IllegalArgumentException();
        }
        return this.employeesByPosition.get(position.name());
    }

    @Override
    public Iterable<Employee> getBySalary(double minSalary) {
        if (this.count <= 0){
            throw new IllegalArgumentException();
        }
        double maxSalary = this.employeesBySalary.lastKey();
        if (minSalary <= maxSalary){
            List<Employee> result = new ArrayList<>();
            this.employeesBySalary.subMap(minSalary, true, maxSalary, true)
                    .values().forEach(result::addAll);
            if (result.isEmpty()){
                throw new IllegalArgumentException();
            }
            return result;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Iterable<Employee> getBySalaryAndPosition(double salary, Position position) {
        if (!this.employeesBySalary.containsKey(salary)){
            throw new IllegalArgumentException();
        }
        List<Employee> result = new ArrayList<>();
        this.employeesBySalary.get(salary).forEach(em -> {
            if (em.getPosition().equals(position)){
                result.add(em);
            }
        });
        if (result.isEmpty()){
            throw new IllegalArgumentException();
        }
        return result;

    }

    @Override
    public Iterable<Employee> searchBySalary(double minSalary, double maxSalary) {
        List<Employee> result = new ArrayList<>();
        this.employeesBySalary.subMap(minSalary, true, maxSalary, true)
                .values().forEach(result::addAll);
        return result;
    }

    @Override
    public Iterable<Employee> searchByPosition(Iterable<Position> positions) {
        List<Employee> result = new ArrayList<>();
        for (Position position : positions) {
            if (this.employeesByPosition.containsKey(position.name())) {
                result.addAll(this.employeesByPosition.get(position.name()));
            }
        }
        return result;
    }

    @Override
    public Iterable<Employee> allWithPositionAndMinSalary(Position position, double minSalary) {
        if (!this.employeesByPosition.containsKey(position.name())){
            return new ArrayList<>();
        }
        List<Employee> result = new ArrayList<>();
        this.employeesByPosition.get(position.name()).forEach(em -> {
            if (em.getSalary() >= minSalary){
                result.add(em);
            }
        });
        return result;
    }

    @Override
    public Iterable<Employee> searchByFirstName(String firstName) {
        return this.employeesByFirstName.getOrDefault(firstName, new ArrayList<>());
    }

    @Override
    public Iterable<Employee> searchByNameAndPosition(String firstName, String lastName, Position position) {
        if (!this.employeesByFirstName.containsKey(firstName)){
            return new ArrayList<>();
        }
        List<Employee> result = new ArrayList<>();
        this.employeesByFirstName.get(firstName).forEach(em -> {
            if (em.getLastName().equals(lastName) && em.getPosition().equals(position)){
                result.add(em);
            }
        });
        return result;
    }

    @Override
    public Iterator<Employee> iterator() {
        return this.employeesByUUID.values().iterator();
    }
}

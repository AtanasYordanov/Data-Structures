import java.util.Date;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Enterprise ent = new Enterprise();

        ent.add(new Employee("Pesho", "Mesho", 5000.00, Position.HR, new Date(2015, 6, 6)));

        ent.raiseSalary(2, 50);

        System.out.println();
    }
}

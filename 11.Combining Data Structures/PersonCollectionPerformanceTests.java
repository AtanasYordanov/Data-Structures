import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonCollectionPerformanceTests {

    private PersonCollection persons;

    @Before
    public void TestInitialize() {
        this.persons = new PersonCollectionImpl();
    }

    private void AddPersons(int count) {
        for (int i = 0; i < count; i++) {
            Person person = new Person();
            person.setEmail("pesho" + i + "@gmail" + (i % 100) + ".com");
            person.setName("Pesho" + (i % 100));
            person.setAge(i % 100);
            person.setTown("Sofia" + (i % 100));

            this.persons.addPerson(person.getEmail(), person.getName(), person.getAge(), person.getTown());
        }
    }

    @Test
    public void testPerformance_AddPerson() {
        // Act
        AddPersons(5000);
        Assert.assertEquals(5000, this.persons.getCount());
    }

    @Test
    public void TestPerformance_FindPerson() {
        // Arrange
        AddPersons(5000);

        // Act
        for (int i = 0; i < 100000; i++) {
            Person existingPerson = this.persons.findPerson("pesho1@gmail1.com");
            Assert.assertNotNull(existingPerson);
            Person nonExistingPerson = this.persons.findPerson("non-existing email");
            Assert.assertNull(nonExistingPerson);
        }
    }

    @Test
    public void TestPerformance_FindPersonsByEmailDomain() {
        // Arrange
        AddPersons(5000);

        // Act
        for (int i = 0; i < 10000; i++) {
            Iterable<Person> existingPersons =
                    this.persons.findPersons("gmail1.com");
            int count = 0;
            for (Person existingPerson : existingPersons) {
                count++;
            }

            Assert.assertEquals(50, count);

            Iterable<Person> notExistingPersons =
                    this.persons.findPersons("non-existing email");
            count = 0;
            for (Person notExistingPerson : notExistingPersons) {
                count++;
            }
            Assert.assertEquals(0, count);
        }
    }

    @Test
    public void TestPerformance_FindPersonsByAgeRange() {
        // Arrange
        AddPersons(5000);

        // Act
        for (int i = 0; i < 2000; i++) {
            Iterable<Person> existingPersons =
                    this.persons.findPersons(20, 21);
            int count = 0;
            for (Person existingPerson : existingPersons) {
                count++;
            }

            Assert.assertEquals(100, count);

            Iterable<Person> notExistingPersons =
                    this.persons.findPersons(500, 600);

            count = 0;
            for (Person notExistingPerson : notExistingPersons) {
                count++;
            }

            Assert.assertEquals(0, count);
        }
    }

    @Test
    public void TestPerformance_FindPersonsByTownAndAgeRange() {
        // Arrange
        AddPersons(5000);

        // Act
        for (int i = 0; i < 5000; i++) {
            Iterable<Person> existingPersons =
                    this.persons.findPersons(18, 22, "Sofia20");
            int count = 0;
            for (Person existingPerson : existingPersons) {
                count++;
            }

            Assert.assertEquals(50, count);

            Iterable<Person> notExistingTownPersons =
                    this.persons.findPersons(20, 30, "Missing town");
            count = 0;
            for (Person notExistingTownPerson : notExistingTownPersons) {
                count++;
            }

            Assert.assertEquals(0, count);

            Iterable<Person> notExistingAgePersons =
                    this.persons.findPersons(200, 300, "Sofia1");

            count = 0;
            for (Person notExistingAgePerson : notExistingAgePersons) {
                count++;
            }

            Assert.assertEquals(0, count);
        }
    }
}

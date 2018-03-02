import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonCollectionUnitTests {

    private PersonCollection persons;

    @Before
    public void TestInitialize() {
        //this.persons = new PersonCollectionSlow();
        this.persons = new PersonCollectionImpl();
    }

    @Test
    public void AddPerson_ShouldWorkCorrectly() {
        // Arrange

        // Act
        boolean isAdded =
                this.persons.addPerson("pesho@gmail.com", "Peter", 18, "Sofia");

        // Assert
        Assert.assertTrue(isAdded);
        Assert.assertEquals(1, this.persons.getCount());
    }

    @Test
    public void AddPerson_DuplicatedEmail_ShouldWorkCorrectly() {
        // Arrange

        // Act
        boolean isAddedFirst =
                this.persons.addPerson("pesho@gmail.com", "Peter", 18, "Sofia");
        boolean isAddedSecond =
                this.persons.addPerson("pesho@gmail.com", "Maria", 24, "Plovdiv");

        // Assert
        Assert.assertTrue(isAddedFirst);
        Assert.assertFalse(isAddedSecond);
        Assert.assertEquals(1, this.persons.getCount());
    }

    @Test
    public void FindPerson_ExistingPerson_ShouldReturnPerson() {
        // Arrange
        this.persons.addPerson("pesho@gmail.com", "Peter", 28, "Plovdiv");

        // Act
        Person person = this.persons.findPerson("pesho@gmail.com");

        // Assert
        Assert.assertNotNull(person);
    }

    @Test
    public void FindPerson_NonExistingPerson_ShouldReturnNothing() {
        // Arrange

        // Act
        Person person = this.persons.findPerson("pesho@gmail.com");

        // Assert
        Assert.assertNull(person);
    }

    @Test
    public void DeletePerson_ShouldWorkCorrectly() {
        // Arrange
        this.persons.addPerson("pesho@gmail.com", "Peter", 28, "Plovdiv");

        // Act
        boolean isDeletedExisting =
                this.persons.deletePerson("pesho@gmail.com");
        boolean isDeletedNonExisting =
                this.persons.deletePerson("pesho@gmail.com");

        // Assert
        Assert.assertTrue(isDeletedExisting);
        Assert.assertFalse(isDeletedNonExisting);
        Assert.assertEquals(0, persons.getCount());
    }

    @Test
    public void FindPersonsByEmailDomain_ShouldReturnMatchingPersons() {
        // Arrange
        this.persons.addPerson("pesho@gmail.com", "Pesho", 28, "Plovdiv");
        this.persons.addPerson("kiro@yahoo.co.uk", "Kiril", 22, "Sofia");
        this.persons.addPerson("mary@gmail.com", "Maria", 21, "Plovdiv");
        this.persons.addPerson("ani@gmail.com", "Anna", 19, "Bourgas");

        // Act
        Iterable<Person> personsGmail = this.persons.findPersons("gmail.com");
        Iterable<Person> personsYahoo = this.persons.findPersons("yahoo.co.uk");
        Iterable<Person> personsHoo = this.persons.findPersons("hoo.co.uk");

        List<String> personGmailResult = new ArrayList<>();
        personsGmail.forEach(e -> personGmailResult.add(e.getEmail()));

        List<String> personYahooResult = new ArrayList<>();
        personsYahoo.forEach(e -> personYahooResult.add(e.getEmail()));

        List<String> personHooResult = new ArrayList<>();
        personsHoo.forEach(e -> personHooResult.add(e.getEmail()));

        String[] gmailResult = new String[personGmailResult.size()];
        for (int i = 0; i < gmailResult.length; i++) {
            gmailResult[i] = personGmailResult.get(i);
        }

        String[] yahooResult = new String[personYahooResult.size()];
        for (int i = 0; i < yahooResult.length; i++) {
            yahooResult[i] = personYahooResult.get(i);
        }

        String[] hooResult = new String[personHooResult.size()];
        for (int i = 0; i < hooResult.length; i++) {
            hooResult[i] = personHooResult.get(i);
        }

        // Assert
        Assert.assertArrayEquals(
                new String[]{"ani@gmail.com", "mary@gmail.com", "pesho@gmail.com"},
                gmailResult);

        Assert.assertArrayEquals(
                new String[]{"kiro@yahoo.co.uk"},
                yahooResult);

        Assert.assertArrayEquals(
                new String[]{},
                hooResult);
    }

    @Test
    public void FindPersonsByNameAndTown_ShouldReturnMatchingPersons() {
        // Arrange
        this.persons.addPerson("pesho@gmail.com", "Pesho", 28, "Plovdiv");
        this.persons.addPerson("kiro@yahoo.co.uk", "Kiril", 22, "Sofia");
        this.persons.addPerson("pepi@gmail.com", "Pesho", 21, "Plovdiv");
        this.persons.addPerson("ani@gmail.com", "Anna", 19, "Bourgas");
        this.persons.addPerson("pepi2@yahoo.fr", "Pesho", 21, "Plovdiv");

        // Act
        Iterable<Person> personsPeshoPlovdiv = this.persons.findPersons("Pesho", "Plovdiv");
        Iterable<Person> personsLowercase = this.persons.findPersons("pesho", "plovdiv");
        Iterable<Person> personsPeshoNoTown = this.persons.findPersons("Pesho", null);
        Iterable<Person> personsAnnaBourgas = this.persons.findPersons("Anna", "Bourgas");

        List<String> personPeshoPlovdivReslt = new ArrayList<>();
        personsPeshoPlovdiv.forEach(e -> {
            personPeshoPlovdivReslt.add(e.getEmail());
        });

        List<String> personsLowercaseResult = new ArrayList<>();
        personsLowercase.forEach(e -> {
            personsLowercaseResult.add(e.getEmail());
        });

        List<String> personsPeshoNoTownResult = new ArrayList<>();
        personsPeshoNoTown.forEach(e -> {
            personsPeshoNoTownResult.add(e.getEmail());
        });

        List<String> personsAnnaBourgasResult = new ArrayList<>();
        personsAnnaBourgas.forEach(e -> {
            personsAnnaBourgasResult.add(e.getEmail());
        });


        String[] peshoPlovdivReslt = new String[personPeshoPlovdivReslt.size()];
        for (int i = 0; i < peshoPlovdivReslt.length; i++) {
            peshoPlovdivReslt[i] = personPeshoPlovdivReslt.get(i);
        }

        String[] lowercaseResult = new String[personsLowercaseResult.size()];
        for (int i = 0; i < lowercaseResult.length; i++) {
            lowercaseResult[i] = personsLowercaseResult.get(i);
        }

        String[] peshoNoTownResult = new String[personsPeshoNoTownResult.size()];
        for (int i = 0; i < peshoNoTownResult.length; i++) {
            peshoNoTownResult[i] = personsPeshoNoTownResult.get(i);
        }

        String[] annaBourgasResult = new String[personsAnnaBourgasResult.size()];
        for (int i = 0; i < annaBourgasResult.length; i++) {
            annaBourgasResult[i] = personsAnnaBourgasResult.get(i);
        }

        Arrays.sort(peshoPlovdivReslt);
        String[] expected1 = {"pepi@gmail.com", "pepi2@yahoo.fr", "pesho@gmail.com"};
        Arrays.sort(expected1);

        // Assert
        Assert.assertArrayEquals(
                expected1,
                peshoPlovdivReslt);

        Assert.assertArrayEquals(
                new String[]{},
                lowercaseResult);

        Assert.assertArrayEquals(
                new String[]{},
                peshoNoTownResult);

        Assert.assertArrayEquals(
                new String[]{"ani@gmail.com"},
                annaBourgasResult);
    }

    @Test
    public void findPersonsByAgeRange_ShouldReturnMatchingPersons() {
        // Arrange
        this.persons.addPerson("pesho@gmail.com", "Pesho", 28, "Plovdiv");
        this.persons.addPerson("kiro@yahoo.co.uk", "Kiril", 22, "Sofia");
        this.persons.addPerson("pepi@gmail.com", "Pesho", 21, "Plovdiv");
        this.persons.addPerson("ani@gmail.com", "Anna", 19, "Bourgas");
        this.persons.addPerson("pepi2@yahoo.fr", "Pesho", 21, "Plovdiv");
        this.persons.addPerson("asen@gmail.com", "Asen", 21, "Rousse");

        // Act
        Iterable<Person> personsAgedFrom21to22 = this.persons.findPersons(21, 22);
        Iterable<Person> personsAgedFrom10to11 = this.persons.findPersons(10, 11);
        Iterable<Person> personsAged21 = this.persons.findPersons(21, 21);
        Iterable<Person> personsAgedFrom0to1000 = this.persons.findPersons(0, 1000);

        List<String> personsAgedFrom21to22Result = new ArrayList<>();
        personsAgedFrom21to22.forEach(e -> personsAgedFrom21to22Result.add(e.getEmail()));

        List<String> personsAgedFrom10to11Result = new ArrayList<>();
        personsAgedFrom10to11.forEach(e -> personsAgedFrom10to11Result.add(e.getEmail()));

        List<String> personsAged21Result = new ArrayList<>();
        personsAged21.forEach(e -> personsAged21Result.add(e.getEmail()));

        List<String> personsAgedFrom0to1000Result = new ArrayList<>();
        personsAgedFrom0to1000.forEach(e -> personsAgedFrom0to1000Result.add(e.getEmail()));

        String[] from21to22Result = new String[personsAgedFrom21to22Result.size()];
        for (int i = 0; i < from21to22Result.length; i++) {
            from21to22Result[i] = personsAgedFrom21to22Result.get(i);
        }

        String[] from10to11 = new String[personsAgedFrom10to11Result.size()];
        for (int i = 0; i < from10to11.length; i++) {
            from10to11[i] = personsAgedFrom10to11Result.get(i);
        }

        String[] aged21 = new String[personsAged21Result.size()];
        for (int i = 0; i < aged21.length; i++) {
            aged21[i] = personsAged21Result.get(i);
        }

        String[] agedFrom0to1000 = new String[personsAgedFrom0to1000Result.size()];
        for (int i = 0; i < agedFrom0to1000.length; i++) {
            agedFrom0to1000[i] = personsAgedFrom0to1000Result.get(i);
        }

        Arrays.sort(from21to22Result);
        String[] expected1 = new String[]{"asen@gmail.com", "pepi@gmail.com", "pepi2@yahoo.fr", "kiro@yahoo.co.uk"};
        Arrays.sort(expected1);

        Arrays.sort(aged21);
        String[] expected2 = new String[]{"asen@gmail.com", "pepi@gmail.com", "pepi2@yahoo.fr"};
        Arrays.sort(expected2);

        Arrays.sort(agedFrom0to1000);
        String[] expected3 = new String[]{"ani@gmail.com", "asen@gmail.com", "pepi@gmail.com", "pepi2@yahoo.fr", "kiro@yahoo.co.uk", "pesho@gmail.com"};
        Arrays.sort(expected3);

        // Assert
        Assert.assertArrayEquals(
                expected1,
                from21to22Result);


        Assert.assertArrayEquals(
                new String[]{},
                from10to11);

        Assert.assertArrayEquals(
                expected2,
                aged21);

        Assert.assertArrayEquals(
                expected3,
                agedFrom0to1000);
    }
}

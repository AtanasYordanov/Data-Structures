import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CorrectnessTests {

    @Test(timeout = 200)
    public void add_One_ShouldBeAdded() {
        Computer computer = new ComputerImpl(100);
        List<Invader> invaders = new ArrayList<>();

        Invader invader = new InvaderImpl(10, 10);

        computer.addInvader(invader);
        invaders.add(invader);

        int invaderCount = 0;
        Iterable<Invader> actual = computer.invaders();
        for (Invader inv : actual) {
            invaderCount++;
        }

        Assert.assertEquals("Wrong size", invaders.size(), invaderCount);
    }

    @Test(timeout = 200)
    public void add_Many_ShouldBeAddedInCorrectOrder() {
        Computer computer = new ComputerImpl(100);
        List<Invader> invaders = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Invader invader = new InvaderImpl(i, i);
            computer.addInvader(invader);
            invaders.add(invader);
        }

        int invaderCount = 0;
        Iterable<Invader> actual = computer.invaders();
        for (Invader inv : actual) {
            invaderCount++;
        }

        Assert.assertEquals("Wrong size", invaders.size(), invaderCount);
    }

    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void Energy_InitializeWithNegativeEnergy_ShouldThrow() {
        Computer computer = new ComputerImpl(-100);
    }

    @Test(timeout = 200)
    public void Energy_DamageOnce_ShouldHaveCorrectEnergy() {
        Computer computer = new ComputerImpl(100);

        computer.addInvader(new InvaderImpl(10, 1));
        computer.skip(1);

        Assert.assertEquals("Wrong energy", 90, computer.getEnergy());
    }

    @Test(timeout = 200)
    public void Energy_DamageMultipleTimes_ShouldHaveCorrectEnergy() {
        Computer computer = new ComputerImpl(100);

        for (int i = 0; i < 50; i++) {
            computer.addInvader(new InvaderImpl(1, 1));
        }

        computer.skip(1);

        Assert.assertEquals("Wrong energy", 50, computer.getEnergy());
    }

    @Test(timeout = 200)
    public void Energy_DamageUntilNegative_ShouldHaveZeroEnergy() {
        Computer computer = new ComputerImpl(100);

        for (int i = 0; i < 200; i++) {
            computer.addInvader(new InvaderImpl(1, 1));
        }

        computer.skip(1);

        Assert.assertEquals("Wrong energy", 0, computer.getEnergy());
    }

    @Test(timeout = 200)
    public void skip_OneTurn_NoInvaders() {
        Computer computer = new ComputerImpl(100);

        computer.skip(1);

        List<Invader> actual = new ArrayList<>();
        for (Invader invader : computer.invaders()) {
            actual.add(invader);
        }

        Assert.assertEquals(100, computer.getEnergy());
        Assert.assertEquals("Collections not equal", new ArrayList<Invader>(), actual);
    }

    @Test(timeout = 200)
    public void Skip_OneTurn_OneInvader_ShouldDecreaseDistance() {
        Computer computer = new ComputerImpl(100);
        Invader invader = new InvaderImpl(10, 10);
        computer.addInvader(invader);

        computer.skip(1);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
        Assert.assertEquals("Wrong count", 1, actual.size());
    }

    @Test(timeout = 200)
    public void Skip_OneTurn_MultipleInvaders_ShouldDecreaseDistance() {
        Computer computer = new ComputerImpl(100);

        for (int i = 0; i < 100; i++) {
            Invader invader = new InvaderImpl(10, 10);
            computer.addInvader(invader);
        }

        computer.skip(1);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
        Assert.assertEquals("Wrong count", 100, actual.size());
    }

    @Test(timeout = 200)
    public void Skip_MultipleTurns_OneInvader_ShouldDoDamage() {
        Computer computer = new ComputerImpl(100);

        Invader invader = new InvaderImpl(10, 100);
        computer.addInvader(invader);

        computer.skip(10);
        computer.skip(80);
        computer.skip(10);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 90, computer.getEnergy());
        Assert.assertEquals("Wrong count", 0, actual.size());
    }

    @Test(timeout = 200)
    public void Skip_MultipleTurns_MultipleInvaders_ShouldDoDamage() {
        Computer computer = new ComputerImpl(100);

        for (int i = 0; i < 10; i++) {
            Invader invader = new InvaderImpl(10, 10 + i);
            computer.addInvader(invader);
        }

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
        Assert.assertEquals("Wrong count", 10, actual.size());

        computer.skip(5);
        actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
        Assert.assertEquals("Wrong count", 10, actual.size());

        computer.skip(5);
        actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 90, computer.getEnergy());
        Assert.assertEquals("Wrong count", 9, actual.size());

        computer.skip(5);
        actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 40, computer.getEnergy());
        Assert.assertEquals("Wrong count", 4, actual.size());

        computer.skip(5);
        actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Wrong energy", 0, computer.getEnergy());
        Assert.assertEquals("Wrong count", 0, actual.size());
    }


    @Test(timeout = 200)
    public void DestroyInRadius_NoTargets() {
        Computer computer = new ComputerImpl(100);

        computer.destroyTargetsInRadius(100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", new ArrayList<>(), actual);
    }

    @Test(timeout = 200)
    public void DestroyInRadius_OneTarget() {
        Computer computer = new ComputerImpl(100);

        computer.addInvader(new InvaderImpl(1, 1));

        computer.destroyTargetsInRadius(100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", new ArrayList<>(), actual);
    }

    @Test(timeout = 200)
    public void DestroyInRadius_AllTargets() {
        Computer computer = new ComputerImpl(100);

        for (int i = 0; i < 100; i++) {
            computer.addInvader(new InvaderImpl(i, i));
        }

        computer.destroyTargetsInRadius(100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", new ArrayList<>(), actual);
    }

    @Test(timeout = 200)
    public void DestroyInRadius_TargetOutOfReach_ShouldNotDestroy() {
        Computer computer = new ComputerImpl(100);
        List<Invader> invaders = new ArrayList<>();
        Invader invader = new InvaderImpl(10, 10);

        computer.addInvader(invader);
        invaders.add(invader);

        computer.destroyTargetsInRadius(9);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", invaders, actual);
    }

    @Test(timeout = 200)
    public void DestroyInRadius_MultipleTargets_ShouldDestroyInRadius() {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Invader invader = new InvaderImpl(i, i);
            computer.addInvader(invader);
            if (i > 50) {
                expected.add(invader);
            }
        }

        computer.destroyTargetsInRadius(50);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }

    @Test(timeout = 200)
    public void DestroyInRadius_MultipleRandomTargets_ShouldDestroyInRadius() {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            int distance = ThreadLocalRandom.current().nextInt(1, 100);
            Invader invader = new InvaderImpl(0, distance);
            computer.addInvader(invader);
            if (distance > 50) {
                expected.add(invader);
            }
        }

        computer.destroyTargetsInRadius(50);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }


    @Test(timeout = 200)
    public void DestroyHighestPriority_NoTargets()
    {
        Computer computer = new ComputerImpl(100);

        computer.destroyHighestPriorityTargets(100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", new ArrayList<Invader>(), actual);
    }

    @Test(timeout = 200)
    public void DestroyHighestPriority_AllTargets()
    {
        Computer computer = new ComputerImpl(100);

        for (int i = 1; i <= 100; i++)
        {
            computer.addInvader(new InvaderImpl(i, i));
        }

        computer.destroyHighestPriorityTargets(100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", new ArrayList<Invader>(), actual);
    }

    @Test(timeout = 200)
    public void DestroyHighestPriority_MultipleTargets()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 100; i++)
        {
            int damage = ThreadLocalRandom.current().nextInt(1, 50);
            int distance = ThreadLocalRandom.current().nextInt(1, 50);
            Invader invader = new InvaderImpl(damage, distance);
            computer.addInvader(invader);
            expected.add(invader);
        }

        computer.destroyHighestPriorityTargets(50);

        Comparator<Invader> byDistance = (i1, i2) ->
                Integer.compare(i1.getDistance(), i2.getDistance());

        Comparator<Invader> byDamage = (i1, i2) ->
                Integer.compare(i2.getDamage(), i1.getDamage());

        List<Invader> toRemove = expected.stream()
                .sorted(byDistance.thenComparing(byDamage))
                .limit(50)
                .collect(Collectors.toList());

        expected.removeAll(toRemove);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }
}

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PerformanceTests {

    @Test(timeout = 2000)
    public void Performance_Add_ShouldBeAddedInCorrectOrder() {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<Invader>();

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 20000; i++) {
            Invader invader = new InvaderImpl(i, i);
            computer.addInvader(invader);
            expected.add(invader);
        }

        long end = System.currentTimeMillis();
        Assert.assertTrue(end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }


    @Test(timeout = 2000)
    public void Performance_Skip_Contact()
    {
        Computer computer = new ComputerImpl(20000);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 20000; i++)
        {
            Invader invader = new InvaderImpl(1, i);
            computer.addInvader(invader);

            if (i > 10000)
            {
                expected.add(invader);
            }
        }

        long start = System.currentTimeMillis();

        computer.skip(10000);

        long end = System.currentTimeMillis();
        Assert.assertTrue("Timeout", end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);

        Assert.assertEquals("Wrong energy", 10000, computer.getEnergy());
    }

    @Test(timeout = 2000)
    public void Performance_Skip_AllContact()
    {
        Computer computer = new ComputerImpl(20000);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 19999; i++)
        {
            Invader invader = new InvaderImpl(1, 20000);
            computer.addInvader(invader);
        }

        long start = System.currentTimeMillis();

        computer.skip(20000);

        long end = System.currentTimeMillis();
        Assert.assertTrue("Timeout", end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);

        Assert.assertEquals("Wrong energy", 1, computer.getEnergy());
    }

    @Test(timeout = 2000)
    public void Performance_Skip_Random()
    {
        int initialEnergy = Integer.MAX_VALUE;

        Computer computer = new ComputerImpl(initialEnergy);
        List<Invader> expected = new ArrayList<>();
        int expectedDamage = 0;

        for (int i = 1; i <= 20000; i++)
        {
            int distance = ThreadLocalRandom.current().nextInt(1, 1000);
            int damage = ThreadLocalRandom.current().nextInt(1, 1000);
            Invader invader = new InvaderImpl(damage, distance);
            computer.addInvader(invader);

            if (distance > 800)
            {
                expected.add(invader);
            }
            else
            {
                expectedDamage += damage;
            }
        }

        long start = System.currentTimeMillis();
        computer.skip(800);
        long end = System.currentTimeMillis();

        Assert.assertTrue("Timeout", end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
        Assert.assertEquals("Wrong energy", initialEnergy - expectedDamage, computer.getEnergy());
    }

    @Test(timeout = 2000)
    public void Performance_Complex_ShouldHaveCorrectEnergy()
    {
        Computer computer = new ComputerImpl(20000);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 20000; i++)
        {
            Invader invader = new InvaderImpl(1, i);
            computer.addInvader(invader);
            if (i > 19000)
            {
                expected.add(invader);
            }
        }

        long start = System.currentTimeMillis();

        computer.destroyHighestPriorityTargets(1000);
        computer.skip(1000);
        computer.destroyTargetsInRadius(1000);
        computer.skip(1000);
        computer.skip(1000);
        computer.destroyTargetsInRadius(5000);
        computer.destroyHighestPriorityTargets(11000);

        long end = System.currentTimeMillis();
        Assert.assertTrue("Timeout", end - start <= 150);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
        Assert.assertEquals("Wrong energy", 19000, computer.getEnergy());
    }


    @Test(timeout = 2000)
    public void Performance_DestroyInRadius_TwoDistinctRadiuses()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 20000; i++)
        {
            Invader invader = new InvaderImpl(1, i % 2 + 1);
            computer.addInvader(invader);
            if (i % 2 + 1 == 2)
            {
                expected.add(invader);
            }
        }

        long start = System.currentTimeMillis();
        computer.destroyTargetsInRadius(1);
        long end = System.currentTimeMillis();

        Assert.assertTrue("Timeout", end - start <= 200);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
    }

    @Test(timeout = 2000)
    public void Performance_DestroyInRadius()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 20000; i++)
        {
            Invader invader = new InvaderImpl(1, i);
            computer.addInvader(invader);

            if (i > 15000)
            {
                expected.add(invader);
            }
        }

        long start = System.currentTimeMillis();

        computer.destroyTargetsInRadius(15000);

        long end = System.currentTimeMillis();
        Assert.assertTrue("Timeout", end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
        Assert.assertEquals("Wrong energy", 100, computer.getEnergy());
    }

    @Test(timeout = 2000)
    public void Performance_DestroyInRadius_Random()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expectedFirstRound = new ArrayList<>();
        List<Invader> expectedSecondRound = new ArrayList<>();

        for (int i = 1; i <= 30000; i++)
        {
            int distance = ThreadLocalRandom.current().nextInt(1, 1000);
            int damage = ThreadLocalRandom.current().nextInt(1, 1000);
            Invader invader = new InvaderImpl(damage, distance);
            computer.addInvader(invader);

            if (distance > 250)
            {
                expectedFirstRound.add(invader);
            }

            if (distance > 500)
            {
                expectedSecondRound.add(invader);
            }
        }

        long start = System.currentTimeMillis();
        computer.destroyTargetsInRadius(100);
        computer.destroyTargetsInRadius(200);
        computer.destroyTargetsInRadius(250);
        long end = System.currentTimeMillis();

        Assert.assertTrue("Timeout", end - start <= 150);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expectedFirstRound, actual);

        start = System.currentTimeMillis();
        computer.destroyTargetsInRadius(300);
        computer.destroyTargetsInRadius(400);
        computer.destroyTargetsInRadius(500);
        end = System.currentTimeMillis();

        Assert.assertTrue("Timeout", end - start <= 100);

        actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expectedSecondRound, actual);
    }


    @Test(timeout = 2000)
    public void Performance_DestroyHighestPriority()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 20000; i++)
        {
            Invader invader = new InvaderImpl(1, i);
            computer.addInvader(invader);
            expected.add(invader);
        }

        Comparator<Invader> byDistance = (i1, i2) ->
                Integer.compare(i1.getDistance(), i2.getDistance());

        Comparator<Invader> byDamage = (i1, i2) ->
                Integer.compare(i2.getDamage(), i1.getDamage());

        expected = expected.stream()
                .sorted(byDistance.thenComparing(byDamage))
                .skip(10000)
                .collect(Collectors.toList());

        long start = System.currentTimeMillis();
        computer.destroyHighestPriorityTargets(10000);
        long end = System.currentTimeMillis();
        Assert.assertTrue("Timeout", end - start <= 100);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }

    @Test(timeout = 2000)
    public void Performance_DestroyHighestPriority_Random()
    {
        Computer computer = new ComputerImpl(100);
        List<Invader> expected = new ArrayList<>();

        for (int i = 1; i <= 10000; i++)
        {
            int distance = ThreadLocalRandom.current().nextInt(1, 1000);
            int damage = ThreadLocalRandom.current().nextInt(1, 1000);
            Invader invader = new InvaderImpl(damage, distance);
            computer.addInvader(invader);
            expected.add(invader);
        }

        long start = System.currentTimeMillis();
        computer.destroyHighestPriorityTargets(5000);
        long end = System.currentTimeMillis();

        Assert.assertTrue("Timeout", end - start <= 100);

        Comparator<Invader> byDistance = (i1, i2) ->
                Integer.compare(i1.getDistance(), i2.getDistance());

        Comparator<Invader> byDamage = (i1, i2) ->
                Integer.compare(i2.getDamage(), i1.getDamage());

        List<Invader> toRemove = expected.stream()
                .sorted(byDistance.thenComparing(byDamage))
                .limit(5000)
                .collect(Collectors.toList());

        expected.removeAll(toRemove);

        List<Invader> actual = new ArrayList<>();
        for (Invader inv : computer.invaders()) {
            actual.add(inv);
        }

        Assert.assertEquals("Collections not equal", expected, actual);
    }
}

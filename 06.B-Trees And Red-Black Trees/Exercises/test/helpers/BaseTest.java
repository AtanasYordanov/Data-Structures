package Exercises.test.helpers;


import Exercises.main.IHierarchy;
import Exercises.test.HierarchyStructureInitializer;
import org.junit.Before;

public class BaseTest {

    protected static final int DefaultRootValue = 5;
    protected IHierarchy<Integer> Hierarchy;

    @Before
    public void setUp() {
        this.Hierarchy = HierarchyStructureInitializer.create(DefaultRootValue);
    }
}

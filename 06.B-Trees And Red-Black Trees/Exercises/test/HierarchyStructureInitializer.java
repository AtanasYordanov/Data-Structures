package Exercises.test;


import Exercises.main.Hierarchy;
import Exercises.main.IHierarchy;

public class HierarchyStructureInitializer {
    
    public static <T> IHierarchy<T> create(T root) {
        return new Hierarchy<>(root);
    }
}

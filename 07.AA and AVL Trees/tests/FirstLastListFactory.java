package tests;

import FirstLastList.FirstLastList;
import FirstLastList.IFirstLastList;

public class FirstLastListFactory {
    public static <T extends Comparable<T>> IFirstLastList<T> create() {
    	return new FirstLastList<T>();
    }
}

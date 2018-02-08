package tests;

import org.junit.Assert;
import org.junit.Test;
import FirstLastList.FirstLastList;
import FirstLastList.IFirstLastList;

import static org.junit.Assert.assertEquals;

public class PerformanceTestsFirstLastList {
    private IFirstLastList<Product> products = new FirstLastList<Product>();
    private int addCounter = 0;

    @Test(timeout = 200)
    public void testPerformance_Add() {
        // Act
        this.addProducts(25000);
        Assert.assertEquals(25000, this.products.getCount());

    }

    @Test(timeout = 200)
    public void testPerformance_First() {
        // Arrange
        this.addProducts(10000);

        // Act
        for (int i = 0; i < 500; i++) {
            this.addProducts(1);
            Product[] returnedProducts =
                    TestUtilities.iterableToStream(this.products.first(i))
                            .toArray(Product[]::new);
            assertEquals(i, returnedProducts.length);
        }
    }

    @Test(timeout = 200)
    public void testPerformance_Last() {
        // Arrange
        this.addProducts(10000);

        // Act
        for (int i = 0; i < 500; i++) {
            this.addProducts(1);
            Product[] returnedProducts =
                    TestUtilities.iterableToStream(this.products.last(i))
                            .toArray(Product[]::new);
            assertEquals(i, returnedProducts.length);
        }
    }

    @Test(timeout = 200)
    public void testPerformance_Min() {
        // Arrange
        this.addProducts(10000);

        // Act
        for (int i = 0; i < 230; i++) {
            this.addProducts(1);
            Product[] returnedProducts =
                    TestUtilities.iterableToStream(this.products.min(i))
                            .toArray(Product[]::new);
            assertEquals(i, returnedProducts.length);
        }
    }

    @Test(timeout = 200)
    public void testPerformance_Max() {
        // Arrange
        this.addProducts(12000);

        // Act
        for (int i = 0; i < 230; i++) {
            this.addProducts(1);
            Product[] returnedProducts =
                    TestUtilities.iterableToStream(this.products.max(i))
                            .toArray(Product[]::new);
            assertEquals(i, returnedProducts.length);
        }
    }

    @Test(timeout = 200)
    public void testPerformance_RemoveAll() {
        // Arrange
        this.addProducts(12000);

        // Act
        while (this.products.getCount() > 0) {
            this.addProducts(1);
            Product first = TestUtilities.iterableToStream(
                    this.products.first(1)).findFirst().get();
            this.products.removeAll(first);
        }
    }

    private void addProducts(int count) {
        for (int i = 0; i < count; i++) {
            addCounter++;
            this.products.add(new Product(
                    addCounter % 1000, "Product" + addCounter));
        }
    }
}

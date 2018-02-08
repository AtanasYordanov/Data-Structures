package tests;

import org.junit.Assert;
import org.junit.Test;
import FirstLastList.FirstLastList;
import FirstLastList.IFirstLastList;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class UnitTestsFirstLastList {

    @Test
    public void add2Items_GetCount_ShouldReturn2() {
        // Arrange
        IFirstLastList<Integer> items = new FirstLastList<Integer>();

        // Act
        items.add(5);
        items.add(10);

        // Assert
        Assert.assertEquals(2, items.getCount());
    }

    @Test
    public void add3Items_First2Items_ShouldReturnFirst2Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.first(2));

        // Assert
        Integer[] expectedItems = new Integer[]{5, 10};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3Items_First0Items_ShouldReturn0Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.first(0));

        // Assert
        Integer[] expectedItems = new Integer[]{};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5ItemsDuplicates_First4Items_ShouldReturnFirst4Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.35, "candy"));
        items.add(new Product(1.20, "cola"));

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.first(4))
                        .map(Product::getTitle);

        // Assert
        String[] expectedItems = new String[]{
                "coffee", "mint drops", "beer", "candy"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3ItemsDuplicates_First3Items_ShouldReturnFirst3Items() {
        // Arrange
        IFirstLastList<String> items = FirstLastListFactory.create();
        items.add("coffee");
        items.add("coffee");
        items.add("milk");

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.first(3));

        // Assert
        String[] expectedItems = new String[]{
                "coffee", "coffee", "milk"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add2Items_First3Items_ShouldThrowException() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.first(3));
    }

    @Test
    public void add3Items_Last2Items_ShouldReturnLast2Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.last(2));

        // Assert
        Integer[] expectedItems = new Integer[]{-2, 10};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3Items_Last0Items_ShouldReturn0Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.last(0));

        // Assert
        Integer[] expectedItems = new Integer[]{};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5ItemsDuplicates_Last4Items_ShouldReturnLast4Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.35, "candy"));
        items.add(new Product(1.20, "cola"));

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.last(4))
                        .map(Product::getTitle);

        // Assert
        String[] expectedItems = new String[]{
                "cola", "candy", "beer", "mint drops"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3ItemsDuplicates_Last3Items_ShouldReturnLast3Items() {
        // Arrange
        IFirstLastList<String> items = FirstLastListFactory.create();
        items.add("coffee");
        items.add("coffee");
        items.add("milk");

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.last(3));

        // Assert
        String[] expectedItems = new String[]{
                "milk", "coffee", "coffee"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add2Items_Last3Items_ShouldThrowException() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.last(3));
    }

    @Test
    public void add5Items_Min3Items_ShouldReturnMin3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(8);
        items.add(1);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.min(3));

        // Assert
        Integer[] expectedItems = new Integer[]{-2, 1, 5};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5Items_Min0Items_ShouldReturn0Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(8);
        items.add(1);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.min(0));

        // Assert
        Integer[] expectedItems = new Integer[]{};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5ItemsDuplicates_Min4Items_ShouldReturnMin4Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(1.20, "cola"));

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.min(4))
                        .map(Product::getTitle);

        // Assert
        String[] expectedItems = new String[]{
                "coffee", "candy", "mint drops", "beer"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3ItemsDuplicates_Min3Items_ShouldReturnMin3Items() {
        // Arrange
        IFirstLastList<String> items = FirstLastListFactory.create();
        items.add("coffee");
        items.add("coffee");
        items.add("milk");

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.min(3));

        // Assert
        String[] expectedItems = new String[]{
                "coffee", "coffee", "milk"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add3Items_Min4Items_ShouldThrowException() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.min(4));
    }

    @Test
    public void add5Items_Max3Items_ShouldReturnMax3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(8);
        items.add(1);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.max(3));

        // Assert
        Integer[] expectedItems = new Integer[]{10, 8, 5};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5Items_Max0Items_ShouldReturn0Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(8);
        items.add(1);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.max(0));

        // Assert
        Integer[] expectedItems = new Integer[]{};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5ItemsDuplicates_Max4Items_ShouldReturnMax4Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(1.20, "cola"));

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.max(4))
                        .map(Product::getTitle);

        // Assert
        String[] expectedItems = new String[]{
                "mint drops", "beer", "cola", "coffee"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add3ItemsDuplicates_Max3Items_ShouldReturnMax3Items() {
        // Arrange
        IFirstLastList<String> items = FirstLastListFactory.create();
        items.add("coffee");
        items.add("coffee");
        items.add("milk");

        // Act
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.max(3));

        // Assert
        String[] expectedItems = new String[]{
                "milk", "coffee", "coffee"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add3Items_Max4Items_ShouldThrowException() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);

        // Act
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.max(4));
    }

    @Test
    public void emptyList_Remove0Items_ShouldReturn0() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();

        // Act
        int countRemoved = items.removeAll(0);

        // Assert
        assertEquals(0, countRemoved);
        Assert.assertEquals(0, items.getCount());
    }

    @Test
    public void add5Items_removeNonExistingItem() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(8);
        items.add(1);

        // Act
        int countRemoved = items.removeAll(0);

        // Assert
        assertEquals(0, countRemoved);
        Assert.assertEquals(5, items.getCount());
    }

    @Test
    public void add4Items_RemoveNonExistingItem_CheckFirstLastMinMax() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(1);
        items.add(-5);
        items.add(10);
        items.add(4);

        // Act
        int countRemoved = items.removeAll(0);
        int first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        int last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        int min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        int max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();

        // Assert
        assertEquals(0, countRemoved);
        Assert.assertEquals(4, items.getCount());
        assertEquals(1, first);
        assertEquals(4, last);
        assertEquals(-5, min);
        assertEquals(10, max);
    }

    @Test
    public void add6Items_Remove2Items_First3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(10);
        items.add(7);
        items.add(70);

        // Act
        int countRemoved = items.removeAll(10);
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.first(3));

        // Assert
        assertEquals(2, countRemoved);
        Integer[] expectedItems = new Integer[]{5, -2, 7};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add7ItemsDuplicates_Remove3Items_First3Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(1.11, "first"));
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(1.20, "cola"));
        items.add(new Product(2.99, "chocolate"));

        // Act
        int countRemoved = items.removeAll(new Product(1.20, null));
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.first(3))
                        .map(Product::getTitle);

        // Assert
        assertEquals(3, countRemoved);
        String[] expectedItems = new String[]{
                "first", "coffee", "candy"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add6Items_Remove2Items_Last3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(10);
        items.add(-2);
        items.add(10);
        items.add(7);
        items.add(8);

        // Act
        int countRemoved = items.removeAll(10);
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.last(3));

        // Assert
        assertEquals(2, countRemoved);
        Integer[] expectedItems = new Integer[]{8, 7, -2};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add7ItemsDuplicates_Remove3Items_Last3Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(1.11, "first"));
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(2.50, "chocolate"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(1.20, "cola"));

        // Act
        int countRemoved = items.removeAll(new Product(1.20, null));
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.last(3))
                        .map(Product::getTitle);

        // Assert
        assertEquals(3, countRemoved);
        String[] expectedItems = new String[]{
                "candy", "chocolate", "coffee"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add5Items_Remove2Items_Min3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(77);
        items.add(10);
        items.add(-2);
        items.add(10);
        items.add(7);

        // Act
        int countRemoved = items.removeAll(10);
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.min(3));

        // Assert
        assertEquals(2, countRemoved);
        Integer[] expectedItems = new Integer[]{-2, 5, 7};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add7ItemsDuplicates_Remove3Items_Min3Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(2.22, "chocolate"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(0.01, "cent"));
        items.add(new Product(1.20, "cola"));

        // Act
        int countRemoved = items.removeAll(new Product(1.20, null));
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.min(3))
                        .map(Product::getTitle);

        // Assert
        assertEquals(3, countRemoved);
        String[] expectedItems = new String[]{
                "cent", "coffee", "candy"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add6Items_Remove2Items_Max3Items() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(5);
        items.add(77);
        items.add(10);
        items.add(-2);
        items.add(10);
        items.add(7);

        // Act
        int countRemoved = items.removeAll(10);
        Stream<Integer> returnedItems =
                TestUtilities.iterableToStream(items.max(3));

        // Assert
        assertEquals(2, countRemoved);
        Integer[] expectedItems = new Integer[]{77, 7, 5};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add7ItemsDuplicates_Remove3Items_Max3Items() {
        // Arrange
        IFirstLastList<Product> items = FirstLastListFactory.create();
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(1.20, "mint drops"));
        items.add(new Product(1.20, "beer"));
        items.add(new Product(2.22, "chocolate"));
        items.add(new Product(0.50, "candy"));
        items.add(new Product(0.01, "cent"));
        items.add(new Product(1.20, "cola"));

        // Act
        int countRemoved = items.removeAll(new Product(1.20, null));
        Stream<String> returnedItems =
                TestUtilities.iterableToStream(items.max(3))
                        .map(Product::getTitle);

        // Assert
        assertEquals(3, countRemoved);
        String[] expectedItems = new String[]{
                "chocolate", "coffee", "candy"};
        TestUtilities.assertListEquals(expectedItems, returnedItems);
    }

    @Test
    public void add2Items_Clear_CountShouldBe0() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(1);
        items.add(-5);

        // Act
        items.clear();

        // Assert
        Assert.assertEquals(0, items.getCount());
    }

    @Test
    public void add4Items_Clear_AddItem_CheckFirstLastMinMax() {
        // Arrange
        IFirstLastList<Integer> items = FirstLastListFactory.create();
        items.add(1);
        items.add(-5);
        items.add(10);
        items.add(4);

        // Act
        items.clear();
        items.add(3);

        // Assert
        Assert.assertEquals(1, items.getCount());
        int first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        int last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        int min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        int max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals(3, first);
        assertEquals(3, last);
        assertEquals(3, min);
        assertEquals(3, max);
    }

    @Test
    public void complexTest_AllOperationsStrings() {
        IFirstLastList<String> items = FirstLastListFactory.create();
        items.add("zero");
        Assert.assertEquals(1, items.getCount());
        TestUtilities.assertListEquals(new String[]{"zero"}, items.first(1));
        TestUtilities.assertListEquals(new String[]{"zero"}, items.last(1));
        TestUtilities.assertListEquals(new String[]{"zero"}, items.max(1));
        TestUtilities.assertListEquals(new String[]{"zero"}, items.min(1));

        items.clear();
        Assert.assertEquals(0, items.getCount());

        items.add("first");
        items.add("second");
        items.add("third");
        items.add("fourth");
        Assert.assertEquals(4, items.getCount());
        TestUtilities.assertListEquals(new String[]{"first"}, items.first(1));
        TestUtilities.assertListEquals(new String[]{"fourth"}, items.last(1));
        TestUtilities.assertListEquals(new String[]{"first"}, items.min(1));
        TestUtilities.assertListEquals(new String[]{"third"}, items.max(1));

        Assert.assertEquals(1, items.removeAll("first"));
        Assert.assertEquals(1, items.removeAll("fourth"));
        Assert.assertEquals(0, items.removeAll("first"));

        Assert.assertEquals(2, items.getCount());
        TestUtilities.assertListEquals(new String[]{"second"}, items.first(1));
        TestUtilities.assertListEquals(new String[]{"third"}, items.last(1));
        TestUtilities.assertListEquals(new String[]{"second"}, items.min(1));
        TestUtilities.assertListEquals(new String[]{"third"}, items.max(1));
    }

    @Test
    public void complexTest_AllOperationsProducts() {
        IFirstLastList<Product> items = FirstLastListFactory.create();
        Assert.assertEquals(0, items.getCount());

        items.add(new Product(0.50, "coffee"));
        Assert.assertEquals(1, items.getCount());
        Product first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        Product last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        Product min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        Product max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("coffee", first.getTitle());
        assertEquals("coffee", last.getTitle());
        assertEquals("coffee", min.getTitle());
        assertEquals("coffee", max.getTitle());

        items.add(new Product(0.50, "candy"));
        Assert.assertEquals(2, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("coffee", first.getTitle());
        assertEquals("candy", last.getTitle());
        assertEquals("coffee", min.getTitle());
        assertEquals("coffee", max.getTitle());

        items.add(new Product(2.99, "chocolate"));
        Assert.assertEquals(3, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("coffee", first.getTitle());
        assertEquals("chocolate", last.getTitle());
        assertEquals("coffee", min.getTitle());
        assertEquals("chocolate", max.getTitle());

        items.add(new Product(0.50, "candy"));
        Assert.assertEquals(4, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("coffee", first.getTitle());
        assertEquals("candy", last.getTitle());
        assertEquals("coffee", min.getTitle());
        assertEquals("chocolate", max.getTitle());

        int countRemoved = items.removeAll(new Product(0.50, "some product"));
        assertEquals(3, countRemoved);
        Assert.assertEquals(1, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("chocolate", first.getTitle());
        assertEquals("chocolate", last.getTitle());
        assertEquals("chocolate", min.getTitle());
        assertEquals("chocolate", max.getTitle());

        items.add(new Product(0.50, "candy"));
        Assert.assertEquals(2, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("chocolate", first.getTitle());
        assertEquals("candy", last.getTitle());
        assertEquals("candy", min.getTitle());
        assertEquals("chocolate", max.getTitle());

        items.clear();
        Assert.assertEquals(0, items.getCount());

        items.add(new Product(2.50, "beer"));
        items.add(new Product(7.35, "vodka"));
        items.add(new Product(0.50, "coffee"));
        items.add(new Product(0.50, "candy"));
        Assert.assertEquals(4, items.getCount());
        Stream<String> first2 = TestUtilities.iterableToStream(items.first(2)).map(Product::getTitle);
        Stream<String> last2 = TestUtilities.iterableToStream(items.last(2)).map(Product::getTitle);
        Stream<String> min2 = TestUtilities.iterableToStream(items.min(2)).map(Product::getTitle);
        Stream<String> max2 = TestUtilities.iterableToStream(items.max(2)).map(Product::getTitle);
        TestUtilities.assertListEquals(new String[]{"beer", "vodka"}, first2);
        TestUtilities.assertListEquals(new String[]{"candy", "coffee"}, last2);
        TestUtilities.assertListEquals(new String[]{"coffee", "candy"}, min2);
        TestUtilities.assertListEquals(new String[]{"vodka", "beer"}, max2);

        int removedCount = items.removeAll(new Product(12345, null));
        assertEquals(0, removedCount);

        removedCount = items.removeAll(new Product(7.35, null));
        assertEquals(1, removedCount);

        removedCount = items.removeAll(new Product(2.50, null));
        assertEquals(1, removedCount);

        Assert.assertEquals(2, items.getCount());
        first2 = TestUtilities.iterableToStream(items.first(2)).map(Product::getTitle);
        last2 = TestUtilities.iterableToStream(items.last(2)).map(Product::getTitle);
        min2 = TestUtilities.iterableToStream(items.min(2)).map(Product::getTitle);
        max2 = TestUtilities.iterableToStream(items.max(2)).map(Product::getTitle);
        TestUtilities.assertListEquals(new String[]{"coffee", "candy"}, first2);
        TestUtilities.assertListEquals(new String[]{"candy", "coffee"}, last2);
        TestUtilities.assertListEquals(new String[]{"coffee", "candy"}, min2);
        TestUtilities.assertListEquals(new String[]{"coffee", "candy"}, max2);

        removedCount = items.removeAll(new Product(0.50, null));
        assertEquals(2, removedCount);
        Assert.assertEquals(0, items.getCount());

        items.add(new Product(0.50, "milk"));
        items.add(new Product(1.20, "amstel"));
        items.add(new Product(1.20, "xxx"));
        Assert.assertEquals(3, items.getCount());
        first2 = TestUtilities.iterableToStream(items.first(2)).map(Product::getTitle);
        last2 = TestUtilities.iterableToStream(items.last(2)).map(Product::getTitle);
        min2 = TestUtilities.iterableToStream(items.min(2)).map(Product::getTitle);
        max2 = TestUtilities.iterableToStream(items.max(2)).map(Product::getTitle);
        TestUtilities.assertListEquals(new String[]{"milk", "amstel"}, first2);
        TestUtilities.assertListEquals(new String[]{"xxx", "amstel"}, last2);
        TestUtilities.assertListEquals(new String[]{"milk", "amstel"}, min2);
        TestUtilities.assertListEquals(new String[]{"amstel", "xxx"}, max2);

        removedCount = items.removeAll(new Product(0.50, null));
        assertEquals(1, removedCount);
        Assert.assertEquals(2, items.getCount());

        removedCount = items.removeAll(new Product(1.20, null));
        assertEquals(2, removedCount);
        Assert.assertEquals(0, items.getCount());

        items.add(new Product(0.50, "coffee"));
        Assert.assertEquals(1, items.getCount());
        first = TestUtilities.iterableToStream(items.first(1)).findFirst().get();
        last = TestUtilities.iterableToStream(items.last(1)).findFirst().get();
        min = TestUtilities.iterableToStream(items.min(1)).findFirst().get();
        max = TestUtilities.iterableToStream(items.max(1)).findFirst().get();
        assertEquals("coffee", first.getTitle());
        assertEquals("coffee", last.getTitle());
        assertEquals("coffee", min.getTitle());
        assertEquals("coffee", max.getTitle());
    }

}
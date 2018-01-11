import org.junit.Assert;
import org.junit.Test;


public class ArrayListTests {

	@Test
	public void addSingleElementShouldIncreaseCount(){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(1);
		
		Assert.assertEquals(1, list.getCount());
	}
	
	@Test
	public void addMultipleElementShouldIncreaseCount()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++)
        {
            list.add(i);
        }

        Assert.assertEquals(100, list.getCount());
    }
	
	@Test
	public void setSingleElement()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(0);
        list.set(0,2);

        Integer expected = 2;
        
        Assert.assertEquals(expected, list.get(0));
    }
	
	@Test
	public void addAndGetMultipleElements()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++)
        {
            list.add(i);
        }

        for (Integer i = 0; i < 100; i++)
        {
            Assert.assertEquals(i, list.get(i));
        }
    }	
	
	@Test
	public void addAndGetSingleElement()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(5);
        Integer expected = 5;
        
        Assert.assertEquals(expected, list.get(0));
    }
	
	@Test
	public void setMultipleElements()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++)
        {
            list.add(i);
        }

        for (Integer i = 0; i < list.getCount(); i++)
        {
            list.set(i, i + 1);
        }

        for (int i = 0; i < 100; i++)
        {
            Assert.assertEquals(Integer.valueOf(i + 1), list.get(i));
        }
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void removeAtInvalidPositionShouldThrow()
    {		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.removeAt(3);
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPositionShouldThrow()
    {
		ArrayList<Integer> list = new ArrayList<>();

        list.set(5, 2);
    }

	@Test
	public void removeMultipleElements()
    {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 100; i++)
        {
            list.add(i);
        }

        for (int i = 0; i < 50; i++)
        {
            list.removeAt(0);
        }

        Assert.assertEquals(50, list.getCount());
        for (int i = 0; i < 50; i++)
        {
            Assert.assertEquals(i + 50, list.get(i).intValue());
        }
    }
	
	@Test
	public void removeSingleElementShouldHaveCorrectElements()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(0);
        list.add(1);
        list.add(2);
        list.removeAt(0);

        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
    }
	
	@Test
	public void removeSingleElementShouldHaveCorrectCount()
    {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(0);
        list.removeAt(0);

        Assert.assertEquals(0, list.getCount());
    }
	
}

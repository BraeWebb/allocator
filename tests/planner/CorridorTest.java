package planner;

import planner.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Corridor} implementation class.
 */
public class CorridorTest {

    private Corridor c;
    private Location start = new Location("start");
    private Location end = new Location("end");

    /**
     * Check that the {@link Corridor} class is created in the correct initial state.
     */
    @Test
    public void testInitialState(){
        c = new Corridor(start, end, 40);
        Assert.assertEquals(start, c.getStart());
        Assert.assertEquals(end, c.getEnd());
        Assert.assertEquals(40, c.getCapacity());
        Assert.assertEquals("Corridor start to end (40)", c.toString());
        Assert.assertTrue(c.checkInvariant());
    }

    /**
     * Checks that an IllegalArgumentException error is thrown when {@link Corridor}
     * is initialized with equal start and end locations.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEqualLocationsInitialState(){
        c = new Corridor(start, start, 40);
    }

    /**
     * Check that a NullPointerException error is thrown when {@link Corridor}
     * is initialized with a null start location.
     */
    @Test(expected = NullPointerException.class)
    public void testNullStartInitialState(){
        c = new Corridor(null, end, 40);
    }

    /**
     * Check that a NullPointerException error is thrown when {@link Corridor}
     * is initialized with a null end location.
     */
    @Test(expected = NullPointerException.class)
    public void testNullEndInitialState(){
        c = new Corridor(start, null, 40);
    }

    /**
     * Checks that an IllegalArgumentException error is thrown when
     * {@link Corridor} is initialized with a negative capacity
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCapacityInitialState(){
        c = new Corridor(start, end, -40);
    }

    /**
     * Checks that an IllegalArgumentException error is thrown when
     * {@link Corridor} is initialized with a capacity of zero
     */
    @Test(expected = IllegalArgumentException.class)
    public void testZeroCapacityInitialState(){
        c = new Corridor(start, end, 0);
    }

    /**
     * Checks that the {@link Corridor#equals(Object)} method performs as expected by testing that the method
     * is reflexive, symmetric and transitive.
     * Also performs checks to ensure that the equals method correctly identifies
     * objects which are not equal
     */
    @Test
    public void testEquals(){
        c = new Corridor(start, end, 40);

        // test reflexive
        Assert.assertEquals(c, c);

        // test symmetric
        Corridor c1 = new Corridor(start, end, 40);
        Assert.assertEquals(c, c1);
        Assert.assertEquals(c1, c);

        // test transitive
        Corridor c2 = new Corridor(start, end, 40);
        Assert.assertEquals(c, c1);
        Assert.assertEquals(c, c2);
        Assert.assertEquals(c1, c2);

        // test not equal

        // test different object is not equal
        Object nc1 = new Object();
        Assert.assertNotEquals(c, nc1);

        // test different capacity is not equal
        Corridor nc2 = new Corridor(start, end, 20);
        Assert.assertNotEquals(c, nc2);

        // test different start is not equal
        Corridor nc3 = new Corridor(new Location("not start"), end, 40);
        Assert.assertNotEquals(c, nc3);

        // test different end is not equal
        Corridor nc4 = new Corridor(start, new Location("not end"), 40);
        Assert.assertNotEquals(c, nc4);

        Assert.assertTrue(c.checkInvariant());
    }

    /**
     * Checks that the {@link Corridor#hashCode()} method performs as expected by ensuring that the hashcode
     * is reflexive, symmetric and transitive.
     */
    @Test
    public void testHashCode(){
        c = new Corridor(start, end, 40);

        // test reflexive
        Assert.assertEquals(c.hashCode(), c.hashCode());

        // test symmetric
        Corridor c1 = new Corridor(start, end, 40);
        Assert.assertEquals(c.hashCode(), c1.hashCode());
        Assert.assertEquals(c1.hashCode(), c.hashCode());

        // test transitive
        Corridor c2 = new Corridor(start, end, 40);
        Assert.assertEquals(c.hashCode(), c1.hashCode());
        Assert.assertEquals(c.hashCode(), c2.hashCode());
        Assert.assertEquals(c1.hashCode(), c2.hashCode());

        Assert.assertTrue(c.checkInvariant());
    }

    /**
     * Checks that the {@link Corridor#compareTo(Corridor)} method performs as expected by comparing
     * whether the method returns 0, greater than 0 or less than 0.
     */
    @Test
    public void testCompareTo(){
        c = new Corridor(start, end, 40);
        Corridor c1 = new Corridor(start, end, 40);
        Corridor c2 = new Corridor(new Location("a"), end, 40);
        Corridor c3 = new Corridor(start, new Location("a"), 40);
        Corridor c4 = new Corridor(start, end, 30);

        // returns 0 if objects are equal
        Assert.assertTrue(c.compareTo(c1) == 0);

        // returns the correct value based on start location ordering
        Assert.assertTrue(c.compareTo(c2) > 0);
        Assert.assertTrue(c2.compareTo(c) < 0);

        // returns the correct value based on end location ordering
        Assert.assertTrue(c.compareTo(c3) > 0);
        Assert.assertTrue(c3.compareTo(c) < 0);

        // returns the correct value based on the ordering of capacity
        Assert.assertTrue(c.compareTo(c4) > 0);
        Assert.assertTrue(c4.compareTo(c) < 0);

        Assert.assertTrue(c.checkInvariant());
    }
}
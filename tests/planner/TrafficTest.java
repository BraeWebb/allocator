package planner;

import planner.*;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * Basic tests for the {@link Traffic} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
public class TrafficTest {

    // Correct line separator for executing machine (used in toString method)
    private final static String LINE_SEPARATOR = System.getProperty(
            "line.separator");
    // locations to test with
    private Location[] locations;
    // corridors to test with
    private Corridor[] corridors;

    /**
     * This method is run by JUnit before each test to initialise instance
     * variables locations and corridors.
     */
    @Before
    public void setUp() {
        // locations to test with
        locations = new Location[6];
        locations[0] = new Location("l0");
        locations[1] = new Location("l1");
        locations[2] = new Location("l2");
        locations[3] = new Location("l3");
        locations[4] = new Location("l4");
        locations[5] = new Location("l4");

        // corridors to test with
        corridors = new Corridor[4];
        corridors[0] = new Corridor(locations[0], locations[1], 100);
        corridors[1] = new Corridor(locations[1], locations[2], 200);
        corridors[2] = new Corridor(locations[2], locations[3], 300);
        corridors[3] = new Corridor(locations[3], locations[4], 400);
    }

    /**
     * Basic test of the empty constructor of the class.
     */
    @Test
    public void testEmptyConstructor() {
        // the Traffic object under test
        Traffic traffic = new Traffic();

        // expected results
        Set<Corridor> expectedCorridorsWithTraffic = new HashSet<>();
        String expectedString = "";

        // check the traffic on a non-null corridor
        Assert.assertEquals(0, traffic.getTraffic(corridors[0]));
        // check that there are no corridors with traffic
        Assert.assertEquals(expectedCorridorsWithTraffic, traffic
                .getCorridorsWithTraffic());
        // check the string representation
        Assert.assertEquals(expectedString, traffic.toString());
        // check that the class invariant holds
        Assert.assertTrue(traffic.checkInvariant());
    }

    /**
     * Basic test of the updateTraffic method.
     */
    @Test
    public void testUpdateTraffic() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(corridors[0], 10);
        traffic.updateTraffic(corridors[1], 50);
        traffic.updateTraffic(corridors[0], 20);
        traffic.updateTraffic(corridors[1], -10);

        // expected results
        Set<Corridor> expectedCorridorsWithTraffic = new HashSet<>();
        expectedCorridorsWithTraffic.add(corridors[0]);
        expectedCorridorsWithTraffic.add(corridors[1]);
        String expectedString = "Corridor l0 to l1 (100): 30" + LINE_SEPARATOR
                + "Corridor l1 to l2 (200): 40" + LINE_SEPARATOR;

        // check the traffic on some corridors
        Assert.assertEquals(30, traffic.getTraffic(corridors[0]));
        Assert.assertEquals(40, traffic.getTraffic(corridors[1]));
        Assert.assertEquals(0, traffic.getTraffic(corridors[2]));
        // check the corridors with traffic
        Assert.assertEquals(expectedCorridorsWithTraffic, traffic
                .getCorridorsWithTraffic());
        // check the string representation
        Assert.assertEquals(expectedString, traffic.toString());
        // check that the class invariant holds
        Assert.assertTrue(traffic.checkInvariant());
    }

    /**
     * Check that the appropriate exception is thrown if updateTraffic is called
     * with a null corridor.
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateTrafficNullPointer() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(null, 20);
    }

    /**
     * Check that the appropriate exception is thrown if a call to updateTraffic
     * would result in a negative amount of traffic on a corridor.
     */
    @Test(expected = InvalidTrafficException.class)
    public void testUpdateTrafficInvalidTrafficException() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(corridors[0], -10);
    }

    /**
     * Basic test of the addTraffic method.
     */
    @Test
    public void testAddTraffic() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(corridors[0], 10);
        traffic.updateTraffic(corridors[1], 30);

        // the extra traffic to add
        Traffic extraTraffic = new Traffic();
        extraTraffic.updateTraffic(corridors[1], 40);

        traffic.addTraffic(extraTraffic);

        // expected results
        Set<Corridor> expectedCorridorsWithTraffic = new HashSet<>();
        expectedCorridorsWithTraffic.add(corridors[0]);
        expectedCorridorsWithTraffic.add(corridors[1]);
        String expectedString = "Corridor l0 to l1 (100): 10" + LINE_SEPARATOR
                + "Corridor l1 to l2 (200): 70" + LINE_SEPARATOR;

        // check the traffic on some corridors
        Assert.assertEquals(10, traffic.getTraffic(corridors[0]));
        Assert.assertEquals(70, traffic.getTraffic(corridors[1]));
        Assert.assertEquals(0, traffic.getTraffic(corridors[2]));
        // check the corridors with traffic
        Assert.assertEquals(expectedCorridorsWithTraffic, traffic
                .getCorridorsWithTraffic());
        // check the string representation
        Assert.assertEquals(expectedString, traffic.toString());
        // check that the class invariant holds
        Assert.assertTrue(traffic.checkInvariant());
    }

    /**
     * Check that the appropriate exception is thrown if a call to addTraffic is
     * passed a null parameter.
     */
    @Test(expected = NullPointerException.class)
    public void testAddNullTraffic() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.addTraffic(null);
    }

    /**
     * Basic test of the copy constructor
     */
    @Test
    public void testCopyConstructor() {
        // the Traffic object that will be copied
        Traffic initialTraffic = new Traffic();
        initialTraffic.updateTraffic(corridors[0], 10);
        initialTraffic.updateTraffic(corridors[1], 70);
        initialTraffic.updateTraffic(corridors[2], 20);

        // the Traffic object under test
        Traffic traffic = new Traffic(initialTraffic);

        // expected results
        Set<Corridor> expectedCorridorsWithTraffic = new HashSet<>();
        expectedCorridorsWithTraffic.add(corridors[0]);
        expectedCorridorsWithTraffic.add(corridors[1]);
        expectedCorridorsWithTraffic.add(corridors[2]);
        String expectedString = "Corridor l0 to l1 (100): 10" + LINE_SEPARATOR
                + "Corridor l1 to l2 (200): 70" + LINE_SEPARATOR
                + "Corridor l2 to l3 (300): 20" + LINE_SEPARATOR;

        // check the traffic on some corridors
        Assert.assertEquals(10, traffic.getTraffic(corridors[0]));
        Assert.assertEquals(70, traffic.getTraffic(corridors[1]));
        Assert.assertEquals(20, traffic.getTraffic(corridors[2]));
        Assert.assertEquals(0, traffic.getTraffic(corridors[3]));
        // check the corridors with traffic
        Assert.assertEquals(expectedCorridorsWithTraffic, traffic
                .getCorridorsWithTraffic());
        // check the string representation
        Assert.assertEquals(expectedString, traffic.toString());
        // check that the class invariant holds
        Assert.assertTrue(traffic.checkInvariant());
    }

    /**
     * Check that the appropriate exception is thrown when a new instance of the
     * class Traffic is constructed with a null argument.
     */
    @Test(expected = NullPointerException.class)
    public void testCopyConstructorNullArgument() {
        // the Traffic object under test
        Traffic traffic = new Traffic(null);
    }

    /** Basic test for the sameTraffic method **/
    @Test
    public void testSameTrafficMethod() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(corridors[0], 10);
        traffic.updateTraffic(corridors[1], 20);

        // the Traffic object to compare
        Traffic other = new Traffic();
        other.updateTraffic(corridors[0], 10);

        // check that the objects are not currently the same
        Assert.assertFalse(traffic.sameTraffic(other));

        // update other so that they are now the same and check
        other.updateTraffic(corridors[1], 20);
        Assert.assertTrue(traffic.sameTraffic(other));
    }

    /**
     * Test that the sameTraffic method throws the appropriate exception when
     * the parameter is null
     */
    @Test(expected = NullPointerException.class)
    public void testSameTrafficMethodNullParameter() {
        // the Traffic object under test
        Traffic traffic = new Traffic();
        traffic.updateTraffic(corridors[0], 10);
        traffic.updateTraffic(corridors[1], 20);

        traffic.sameTraffic(null);
    }

}

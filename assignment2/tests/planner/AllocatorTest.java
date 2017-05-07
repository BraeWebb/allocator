package planner;

import planner.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

/**
 * Basic tests for the {@link Allocator.allocate} method in the
 * {@link Allocator} implementation class.
 * 
 * Write your own junit4 tests for the method here.
 */
public class AllocatorTest {

    @Test(timeout = 5000)
    public void task_sheet_test() {
        // checks the assignment sheet example
        Event e0 = new Event("e0", 10);
        Event e1 = new Event("e1", 7);
        Event e2 = new Event("e2", 5);

        ArrayList<Event> events = new ArrayList<Event>();
        events.add(e0);
        events.add(e1);
        events.add(e2);

        ArrayList<Venue> venues;
        try {
            venues = (ArrayList) VenueReader.read("task_sheet_venues.txt");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            return;
        }

        Set<Map<Event, Venue>> expected = new HashSet<>();

        HashMap<Event, Venue> map1 = new HashMap<>();
        map1.put(e0, venues.get(0));
        map1.put(e1, venues.get(3));
        map1.put(e2, venues.get(2));

        HashMap<Event, Venue> map2 = new HashMap<>();
        map2.put(e0, venues.get(2));
        map2.put(e1, venues.get(3));
        map2.put(e2, venues.get(0));

        expected.add(map1);
        expected.add(map2);

        Assert.assertTrue(expected.contains(Allocator.allocate(events, venues)));
    }
}

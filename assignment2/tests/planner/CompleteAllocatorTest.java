package planner;

import planner.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

/**
 * Tests for the {@link Allocator.allocate} method in the {@link Allocator}
 * implementation class.
 */
public class CompleteAllocatorTest {

    /**
     * Test that the result is correct when there are no events.
     */
    @Test(timeout = 5000)
    public void testNoEvents() throws Exception {
        testNoEventsNoVenues();
        testNoEventsManyVenues();
    }

    /**
     * Test that the result is correct when there are no events, and no venues.
     */
    private void testNoEventsNoVenues() throws Exception {
        List<Venue> venues = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there are no events, but many
     * venues.
     */
    private void testNoEventsManyVenues() throws Exception {
        List<Venue> venues = VenueReader.read("input_01_venues.txt");
        List<Event> events = new ArrayList<>();

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there is one event, and either it
     * can or can't be safely allocated.
     */
    @Test(timeout = 5000)
    public void testOneEvent() throws Exception {
        testOneEventAllocationPossible();
        testOneEventAllocationNotPossibleVenuesTooSmall();
        testOneEventAllocationNotPossibleNoVenues();
    }

    /**
     * Test that the result is correct when there is one event, and it can be
     * safely allocated.
     */
    private void testOneEventAllocationPossible() throws Exception {
        List<Venue> venues = VenueReader.read("input_01_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 10));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there is one event, and it cannot be
     * safely allocated.
     * 
     * The reason it cannot be allocated is that it is too big to be hosted by
     * any of the available venues.
     */
    private void testOneEventAllocationNotPossibleVenuesTooSmall()
            throws Exception {
        List<Venue> venues = VenueReader.read("input_01_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 12));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        Assert.assertTrue(allocation == null);
    }

    /**
     * Test that the result is correct when there is one event, and it cannot be
     * safely allocated.
     * 
     * The reason it cannot be allocated is that there aren't enough venues.
     */
    private void testOneEventAllocationNotPossibleNoVenues() throws Exception {
        List<Venue> venues = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 5));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        Assert.assertTrue(allocation == null);
    }

    /**
     * Test specified on handout:
     * 
     * Test that the result is correct when there are many events, and they can
     * be safely allocated.
     * 
     * Only two allocations possible in this case.
     */
    @Test(timeout = 5000)
    public void testManyEventsAllocationPossibleA() throws Exception {
        List<Venue> venues = VenueReader.read("input_01_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 10));
        events.add(new Event("e1", 7));
        events.add(new Event("e2", 5));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there are many events, and they
     * cannot be safely allocated.
     * 
     * In this case because the traffic would exceed safe usage.
     */
    @Test(timeout = 5000)
    public void testManyEventssAllocationNotPossible() throws Exception {
        List<Venue> venues = VenueReader.read("input_01_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 10));
        events.add(new Event("e1", 10));
        events.add(new Event("e2", 9));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        Assert.assertTrue(allocation == null);
    }

    /**
     * Test that the result is correct when there are many events, and they can
     * be safely allocated.
     * 
     * Test not from handout.
     * 
     * Not all venues need to be allocated.
     */
    @Test(timeout = 5000)
    public void testManyEventssAllocationPossibleB() throws Exception {
        List<Venue> venues = VenueReader.read("input_03_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 50));
        events.add(new Event("e1", 30));
        events.add(new Event("e2", 90));
        events.add(new Event("e3", 100));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there are many events, and they can
     * be safely allocated.
     * 
     * Test not from handout.
     * 
     * All venues need to be allocated.
     */
    @Test(timeout = 5000)
    public void testManyEventssAllocationPossibleC() throws Exception {
        List<Venue> venues = VenueReader.read("input_04_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 100));
        events.add(new Event("e1", 75));
        events.add(new Event("e2", 50));
        events.add(new Event("e3", 20));
        events.add(new Event("e4", 10));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there are many events, and they can
     * be safely allocated.
     * 
     * Test not from handout.
     * 
     * Not all venues need to be allocated.
     */
    @Test(timeout = 5000)
    public void testManyEventssAllocationPossibleD() throws Exception {
        List<Venue> venues = VenueReader.read("input_05_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 100));
        events.add(new Event("e1", 75));
        events.add(new Event("e2", 50));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Check that the method does not allocate the same venue more than once.
     */
    @Test(timeout = 5000)
    public void testSameVenueNotAllocatedTwice() throws Exception {
        testSameVenueNotAllocatedTwiceAllocationPossible();
        testSameVenueNotAllocatedTwiceAllocationImpossible();
    }

    /**
     * Test that the result is correct when there are many events, and they can
     * be safely allocated.
     * 
     * Only one possible allocation is safe in this case. There is an option for
     * one venue to traffic-safely host both events, but that is not allowed in
     * the definition of a safe allocation.
     */
    private void testSameVenueNotAllocatedTwiceAllocationPossible()
            throws Exception {
        List<Venue> venues = VenueReader.read("input_02_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 30));
        events.add(new Event("e1", 60));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        checkSafeAllocation(events, venues, allocation);
    }

    /**
     * Test that the result is correct when there are many events, and they
     * cannot be safely allocated.
     * 
     * There is an option for one venue to traffic-safely host both events (but
     * that isn't allowed in the definition of a safe allocation), and no other
     * allocations are possible.
     */
    private void testSameVenueNotAllocatedTwiceAllocationImpossible()
            throws Exception {
        List<Venue> venues = VenueReader.read("input_02_venues.txt");
        List<Event> events = new ArrayList<>();
        events.add(new Event("e0", 50));
        events.add(new Event("e1", 50));

        Map<Event, Venue> allocation = Allocator.allocate(new ArrayList<>(
                events), new ArrayList<>(venues));

        Assert.assertTrue(allocation == null);
    }

    // -----Helper Methods-------------------------------

    /**
     * Returns true if and only if the allocation is safe for the given events
     * and venues.
     */
    private void checkSafeAllocation(List<Event> events, List<Venue> venues,
            Map<Event, Venue> allocation) {

        // (i) check that the allocation is not null
        Assert.assertFalse(allocation == null);

        // (ii) check that all events are allocated
        Set<Event> actualEvents = allocation.keySet();
        Assert.assertEquals(new HashSet<>(events), actualEvents);

        // check that the allocations are sound
        Set<Venue> allocatedVenues = new HashSet<>();
        for (Event event : events) {
            Venue venue = allocation.get(event);
            // (ii) check that the venue is from venues
            Assert.assertTrue(venues.contains(venue));
            // (ii) check that the venue can host the event
            Assert.assertTrue(venue.canHost(event));
            // (iii) check that the venue hasn't already been allocated
            Assert.assertFalse(allocatedVenues.contains(venue));
            allocatedVenues.add(venue);
        }

        // (iv) check that the allocation's traffic is safe
        Assert.assertTrue(safeTraffic(allocation));
    }

    /**
     * Returns true if and only if the traffic caused by the allocation is safe.
     */
    private static boolean safeTraffic(Map<Event, Venue> allocation) {
        Traffic usage = getUsageOf(allocation);
        return usage.isSafe();
    }

    /**
     * Returns the traffic caused by the allocation.
     */
    private static Traffic getUsageOf(Map<Event, Venue> allocation) {
        Traffic result = new Traffic();
        for (Event event : allocation.keySet()) {
            Venue venue = allocation.get(event);
            result.addTraffic(venue.getTraffic(event));
        }
        return result;
    }
}

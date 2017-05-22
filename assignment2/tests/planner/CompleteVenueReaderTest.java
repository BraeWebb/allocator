package planner;

import java.util.*;
import java.io.*;
import planner.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the {@link VenueReader} implementation class.
 */
public class CompleteVenueReaderTest {

    // Correct line separator for executing machine
    private final static String LINE_SEPARATOR = System.getProperty(
            "line.separator");

    /**
     * Test from handout:
     * 
     * Test reading from a file where there are no venues.
     */
    @Test(timeout = 5000)
    public void testCorrectlyFormattedZeroVenues() throws Exception {
        // string representations of the venues that we expect from the file
        List<String> expectedVenues = new ArrayList<>();
        // the actual venues read from the file
        List<Venue> actualVenues = VenueReader.read(
                "test_01_correctlyFormatted_zeroVenues.txt");
        // check that the expected and actual venues read are the same
        checkVenues(expectedVenues, actualVenues);
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file where there is one typical venue.
     */
    @Test(timeout = 5000)
    public void testCorrectlyFormattedOneVenue() throws Exception {
        // string representations of the venues that we expect from the file
        List<String> expectedVenues = new ArrayList<>();
        expectedVenues.add("The Zoo (93)" + LINE_SEPARATOR
                + "Corridor City to Royal Queensland Show - EKKA (400): 51"
                + LINE_SEPARATOR + "Corridor City to St. Lucia (500): 7"
                + LINE_SEPARATOR + "Corridor Valley to City (300): 71"
                + LINE_SEPARATOR);

        // the actual venues read from the file
        List<Venue> actualVenues = VenueReader.read(
                "test_02_correctlyFormatted_oneVenue.txt");
        // check that the expected and actual venues read are the same
        checkVenues(expectedVenues, actualVenues);
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file where there are many venues.
     * 
     * Note that one of the venues doesn't generate any traffic -- that's OK.
     */
    @Test(timeout = 5000)
    public void testCorrectlyFormattedManyVenuesA() throws Exception {
        // string representations of the venues that we expect from the file
        List<String> expectedVenues = new ArrayList<>();

        expectedVenues.add("The Gabba (200)" + LINE_SEPARATOR
                + "Corridor l1 to l2 (200): 150" + LINE_SEPARATOR
                + "Corridor l2 to l3 (100): 50" + LINE_SEPARATOR);

        expectedVenues.add("Tivoli (50)" + LINE_SEPARATOR);

        expectedVenues.add("Suncorp Stadium (100)" + LINE_SEPARATOR
                + "Corridor l0 to l1 (100): 25" + LINE_SEPARATOR
                + "Corridor l1 to l2 (200): 70" + LINE_SEPARATOR);

        // the actual venues read from the file
        List<Venue> actualVenues = VenueReader.read(
                "test_03_correctlyFormatted_manyVenues.txt");
        // check that the expected and actual venues read are the same
        checkVenues(expectedVenues, actualVenues);
    }

    /**
     * Test reading from a file where there are many venues.
     */
    @Test(timeout = 5000)
    public void testCorrectlyFormattedManyVenuesB() throws Exception {
        // string representations of the venues that we expect from the file
        List<String> expectedVenues = new ArrayList<>();

        expectedVenues.add("v0 (100)" + LINE_SEPARATOR
                + "Corridor l4 to l5 (110): 100" + LINE_SEPARATOR
                + "Corridor l5 to l6 (50): 50" + LINE_SEPARATOR);

        expectedVenues.add("v1 (100)" + LINE_SEPARATOR
                + "Corridor l4 to l3 (100): 50" + LINE_SEPARATOR
                + "Corridor l4 to l5 (110): 50" + LINE_SEPARATOR
                + "Corridor l5 to l6 (50): 30" + LINE_SEPARATOR);

        expectedVenues.add("v2 (100)" + LINE_SEPARATOR
                + "Corridor l2 to l3 (110): 100" + LINE_SEPARATOR
                + "Corridor l3 to l4 (100): 50" + LINE_SEPARATOR
                + "Corridor l3 to l9 (50): 50" + LINE_SEPARATOR
                + "Corridor l4 to l5 (105): 20" + LINE_SEPARATOR);

        // the actual venues read from the file
        List<Venue> actualVenues = VenueReader.read(
                "test_15_correctlyFormatted_manyVenues.txt");
        // check that the expected and actual venues read are the same
        checkVenues(expectedVenues, actualVenues);
    }

    /**
     * Test reading from a file where there are many venues.
     */
    @Test(timeout = 5000)
    public void testCorrectlyFormattedManyVenuesC() throws Exception {
        // string representations of the venues that we expect from the file
        List<String> expectedVenues = new ArrayList<>();

        expectedVenues.add("v0 (90)" + LINE_SEPARATOR
                + "Corridor l4 to l5 (100): 90" + LINE_SEPARATOR
                + "Corridor l5 to l6 (50): 45" + LINE_SEPARATOR);

        expectedVenues.add("v1 (100)" + LINE_SEPARATOR
                + "Corridor l6 to l7 (110): 75" + LINE_SEPARATOR
                + "Corridor l7 to l8 (100): 50" + LINE_SEPARATOR
                + "Corridor l8 to l9 (60): 25" + LINE_SEPARATOR);

        expectedVenues.add("v2 (70)" + LINE_SEPARATOR
                + "Corridor l5 to l6 (50): 35" + LINE_SEPARATOR
                + "Corridor l6 to l7 (110): 35" + LINE_SEPARATOR);

        expectedVenues.add("v3 (60)" + LINE_SEPARATOR
                + "Corridor l8 to l9 (60): 30" + LINE_SEPARATOR);

        // the actual venues read from the file
        List<Venue> actualVenues = VenueReader.read(
                "test_16_correctlyFormatted_manyVenues.txt");
        // check that the expected and actual venues read are the same
        checkVenues(expectedVenues, actualVenues);
    }

    /**
     * Tests from handout:
     * 
     * Tests related to venue format being incorrect.
     */
    @Test(timeout = 5000)
    public void testIncorrectlyFormattedVenue() throws Exception {
        testIncorrectlyFormattedVenueName();
        testIncorrectlyFormattedVenueCapacity();
        testIncorrectlyFormattedVenueMissingEmptyLine();
        testIncorrectlyFormattedDuplicateVenues();
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file where a venue's name is invalid.
     */
    private void testIncorrectlyFormattedVenueName() throws Exception {
        // Error on line 6: venue name cannot be ""
        try {
            VenueReader.read("test_11_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file where a venue's capacity is invalid.
     */
    private void testIncorrectlyFormattedVenueCapacity() throws Exception {
        // Error on line 7: venue capacity is invalid
        try {
            VenueReader.read("test_12_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file where a venue does not have an empty line at the
     * end.
     */
    private void testIncorrectlyFormattedVenueMissingEmptyLine()
            throws Exception {
        // Error on line 9: empty line expected to complete venue
        try {
            VenueReader.read("test_13_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Test from handout:
     * 
     * Test reading from a file that contains the same description of a venue
     * twice.
     */
    private void testIncorrectlyFormattedDuplicateVenues() throws Exception {
        // Error found when line 10 reached: duplicate venue detected
        try {
            VenueReader.read("test_14_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Augmented tests from handout:
     * 
     * Tests related to traffic format being incorrect.
     */
    @Test(timeout = 5000)
    public void testIncorrectlyFormattedTraffic() throws Exception {
        testIncorrectlyFormattedCorridor();
        testIncorrectlyFormattedCorridorTraffic();
    }

    /**
     * Augmented test from handout:
     * 
     * Test reading from a file where the traffic specified for a corridor is
     * incorrect.
     */
    private void testIncorrectlyFormattedCorridorTraffic() throws Exception {
        // Error on line 4: traffic exceeds venue capacity
        try {
            VenueReader.read("test_04_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }

        // Error on line 5: traffic exceeds corridor capacity
        try {
            VenueReader.read("test_05_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }

        // Error on line 3: traffic is not an integer ("INVALID")
        try {
            VenueReader.read("test_06_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }

        // Error on line 4: traffic is missing
        try {
            VenueReader.read("test_07_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Augmented test from handout:
     * 
     * Test reading from a file where a corridor in the traffic of a venue has
     * an error.
     */
    private void testIncorrectlyFormattedCorridor() throws Exception {
        // Error on line 6: same corridor appears more than once in traffic
        try {
            VenueReader.read("test_08_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }

        // Error on line 4: incorrectly formatted corridor - END is ""
        try {
            VenueReader.read("test_09_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }

        // Error on line 5: incorrectly formatted corridor - capacity is not an
        // integer
        try {
            VenueReader.read("test_10_incorrectlyFormatted.txt");
            Assert.fail("FormatException not thrown");
        } catch (FormatException e) {
            // OK
            // System.out.println(e.getMessage());
        }
    }

    /**
     * Test reading from a file that does not exist.
     */
    @Test(timeout = 5000, expected = IOException.class)
    public void testReadIOError() throws Exception {
        VenueReader.read("doesNotExist.txt");
    }

    // -----Helper Methods-------------------------------

    /**
     * Check that the list actualVenues has all of the venues described by
     * expectedVenueStrings, in the order that they appear in that list.
     * 
     * @param expectedVenueStrings
     *            A list of the expected string representations of the venues
     *            that should appear in actualVenues.
     * @param actualVenues
     *            The list of venues to be checked against expectedVenueStrings.
     */
    private void checkVenues(List<String> expectedVenueStrings,
            List<Venue> actualVenues) {
        Assert.assertEquals(expectedVenueStrings.size(), actualVenues.size());
        for (int i = 0; i < expectedVenueStrings.size(); i++) {
            Assert.assertEquals(expectedVenueStrings.get(i), actualVenues.get(i)
                    .toString());
        }
    }

}

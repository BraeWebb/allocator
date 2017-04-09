package planner;

import java.util.*;

/**
 * <p>
 * A mutable class for recording the amount of traffic on traffic corridors.
 * </p>
 * 
 * <p>
 * The traffic on a corridor is measured in non-negative integer units,
 * representing people.
 * </p>
 */
public class Traffic {

    // hashmap which maps corridors to their present amount of traffic
    private HashMap<Corridor, Integer> people;

    /*
     * invariant:
     *
     * people != null &&
     *
     * corridor keys of people are not null &&
     *
     * traffic of each corridor is not null &&
     *
     * traffic of each corridor is greater than or equal to zero
     */

    /**
     * <p>
     * Creates a new instance of the class in which every traffic corridor
     * initially has no (i.e. zero) traffic.
     * </p>
     * 
     * <p>
     * That is, for any non-null traffic corridor c, this.getTraffic(c) == 0.
     * </p>
     */
    public Traffic() {
        people = new HashMap<>();
    }

    /**
     * <p>
     * Creates a new instance of this class that initially has the same traffic
     * as parameter initialTraffic.
     * </p>
     * 
     * <p>
     * The parameter initialTraffic should not be modified by this method.
     * Furthermore, future changes to the parameter initialTraffic should not
     * affect this instance of the class, and vice versa. That is, the new
     * instance of the class should be a deep copy of initialTraffic.
     * </p>
     * 
     * @param initialTraffic
     *            the initial traffic for this instance of the class
     * @throws NullPointerException
     *             if initialTraffic is null
     */
    public Traffic(Traffic initialTraffic) {
        if(initialTraffic == null){
            throw new NullPointerException("initialTraffic must not be null");
        }
        people = new HashMap<>();
        // deep copy the values of the initial traffic people hashmap to the objects people variable
        for(Corridor c: initialTraffic.people.keySet()){
            people.put(c, initialTraffic.people.get(c));
        }
    }

    /**
     * <p>
     * Returns the amount of traffic on the given corridor.
     * </p>
     * 
     * <p>
     * The amount of traffic on a corridor is always non-negative, meaning that
     * this method always a returns an integer that is greater than or equal to
     * zero.
     * </p>
     * 
     * @param corridor
     *            the corridor whose associated amount of traffic will be
     *            returned
     * @return the amount of traffic on the given corridor
     * @throws NullPointerException
     *             if the parameter corridor is null
     */
    public int getTraffic(Corridor corridor) {
        if(corridor == null){
            throw new NullPointerException("corridor must not be null");
        }
        // if the traffic object does not know about the corridor return 0
        if(!people.containsKey(corridor)){
            return 0;
        }
        return people.get(corridor);
    }

    /**
     * Returns the set of all traffic corridors c for which this.getTraffic(c)
     * is greater than zero.
     * 
     * @return the set of traffic corridors with an amount of traffic that is
     *         greater than zero
     */
    public Set<Corridor> getCorridorsWithTraffic() {
        // deep copy the keys of the people hashmap to a new variable and return that variable
        Set<Corridor> newSet = new HashSet<Corridor>();
        newSet.addAll(people.keySet());
        return newSet;
    }

    /**
     * <p>
     * Returns true if parameter other currently records the same traffic as
     * this traffic record, and false otherwise.
     * </p>
     * 
     * <p>
     * That is, it returns true if and only if for every traffic corridor, the
     * traffic currently on that corridor in this object equals the traffic
     * currently on that corridor in the other object.
     * </p>
     * 
     * @param other
     *            the Traffic object to compare
     * @return true if this object and other currently record the same traffic,
     *         and false otherwise
     * @throws NullPointerException
     *             if other is null
     */
    public boolean sameTraffic(Traffic other) {
        if(other == null){
            throw new NullPointerException("the other traffic must not be null");
        }
        // base whether the traffic classes are equal on whether the people hashmap
        // is equal as the people hashmap is the sole class variable
        return people.equals(other.people);
    }

    /**
     * <p>
     * Updates the traffic on the given corridor by adding parameter amount to
     * the existing traffic on the corridor.
     * </p>
     * 
     * <p>
     * Parameter amount may be either a negative or positive integer (or zero),
     * but an InvalidTrafficException will be thrown if the result of adding
     * amount to the current traffic on the corridor will result in a negative
     * quantity of traffic on that corridor.
     * </p>
     * 
     * @param corridor
     *            the corridor whose amount of traffic will be updated
     * @param amount
     *            the amount that will be added to the traffic on the given
     *            corridor
     * @throws NullPointerException
     *             if corridor is null
     * @throws InvalidTrafficException
     *             if the addition of amount and the current amount of traffic
     *             on the given corridor is negative (i.e. less than zero).
     */
    public void updateTraffic(Corridor corridor, int amount) {
        if(corridor == null){
            throw new NullPointerException("corridor must not be null");
        }
        if(!people.containsKey(corridor)){
            // if the people hashmap does not contain the corridor ensure that the given amount is
            // above zero and place that corridor and amount mapping into the people hashmap
            if(amount < 0){
                throw new InvalidTrafficException("the initial amount of traffic must not be negative");
            }
            people.put(corridor, amount);
        }else{
            // if the people hashmap contains the corridor ensure that the given amount plus the current amount is
            // above zero and replace the current amount with the new amount plus current amount
            int newTraffic = people.get(corridor) + amount;
            if(newTraffic < 0){
                throw new InvalidTrafficException("the result of adding amount to the current traffic must not be null");
            }
            people.replace(corridor, newTraffic);
        }
    }

    /**
     * <p>
     * This method adds all of the traffic defined by parameter extraTraffic to
     * this object.
     * </p>
     * 
     * <p>
     * That is, for each traffic corridor c, this method updates the traffic on
     * that corridor in this object by adding to it the traffic that parameter
     * extraTraffic associates with c.
     * </p>
     * 
     * <p>
     * (Unless this == extraTraffic) this method must not modify the given
     * parameter, and future modifications to this object should not affect the
     * parameter extraTraffic and vice versa.
     * </p>
     * 
     * @param extraTraffic
     *            the traffic to be added to this object
     * @throws NullPointerException
     *             if extraTraffic is null
     */
    public void addTraffic(Traffic extraTraffic) {
        if(extraTraffic == null){
            throw new NullPointerException("extraTraffic must not be null");
        }
        // loop through each corridor in the other traffic object
        for(Corridor c: extraTraffic.people.keySet()){
            // if the current object contains the corridor add the old value to the new value
            // else if the current object does not contain the corridor just add the new value
            if(people.containsKey(c)) {
                people.replace(c, people.get(c) + extraTraffic.people.get(c));
            }else{
                people.put(c, extraTraffic.people.get(c));
            }
        }
    }

    /**
     * <p>
     * The string representation is the concatenation of strings of the form
     * <br>
     * <br>
     * 
     * "CORRIDOR: TRAFFIC" + LINE_SEPARATOR <br>
     * <br>
     * 
     * where CORRIDOR is the toString() representation of a traffic corridor c
     * for which this.getTraffic(c) is greater than zero, and TRAFFIC is its
     * corresponding amount of traffic, and LINE_SEPARATOR is the line separator
     * retrieved in a machine-independent way by calling
     * System.getProperty("line.separator").
     * </p>
     * 
     * <p>
     * In the string representation, the corridors should appear in the order of
     * their natural ordering (i.e. using the order defined by the compareTo
     * method in the Corridor class).
     * </p>
     * 
     * <p>
     * If there are no traffic corridors c for which this.getTraffic(c) is
     * greater than zero, then the string representation is the empty string "".
     * </p>
     * 
     * <p>
     * (Note that we have one line for each corridor with a non-zero amount of
     * traffic in this string representation, and no lines for corridors with a
     * zero amount of traffic.)
     * </p>
     */
    @Override
    public String toString() {
        // convert the people keySet into a sortable collection and sort that collection
        ArrayList<Corridor> corridors = new ArrayList<>();
        corridors.addAll(people.keySet());
        Collections.sort(corridors);

        // define a blank output
        String output = "";
        // loop through each corridor in order appending the required text to the blank output string
        for(Corridor c: corridors){
            output = output + (c.toString() + ": " + people.get(c) + System.getProperty("line.separator"));
        }
        return output;
    }

    /**
     * <p>
     * Determines whether this class is internally consistent (i.e. it satisfies
     * its class invariant).
     * </p>
     * 
     * <p>
     * NOTE: This method is only intended for testing purposes.
     * </p>
     * 
     * @return true if this class is internally consistent, and false otherwise.
     */
    public boolean checkInvariant() {
        if(people == null){
            return false;
        }
        for(Corridor c: people.keySet()){
            if(c == null) {
                return false;
            }
            if(people.get(c) == null){
                return false;
            }
            if(people.get(c) < 0){
                return false;
            }
        }
        return true;
    }

}

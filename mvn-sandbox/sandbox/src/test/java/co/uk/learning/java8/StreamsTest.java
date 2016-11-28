package co.uk.learning.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

public class StreamsTest {

    private List<String> getStringData() {
        final List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        return stringCollection;
    }

    @Ignore
    @Test
    public void testFilteringAndSortingAStringList() {
        final List<String> stringCollection = getStringData();

        System.out.println("filtering....");
        // gives...
        //        aaa2
        //        aaa1
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a")) // a filter is a predicate... so a boolean functional interface, taking one argument
                .forEach(System.out::println); // so this is the terminal operation and will execute on all the filtered elements

        System.out.println("sorting....");
        // gives...
        // "aaa1", "aaa2"
        stringCollection
                .stream()
                .sorted() // sorts the list by natural order
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        System.out.println("sorting 2....");
        // gives...
        // "aaa1", "aaa2"
        getStringData()
                .stream()
                .filter((s) -> s.startsWith("a")) //  <---- I was thinking surely you would sort AFTER filtering!
                .sorted() // sorts the list by natural order
                .forEach(System.out::println);

        System.out.println("sorting with a comparator....");
        // gives...
        // "bbb3", "bbb2", "bbb1"
        getStringData()
                .stream()
                .filter((s) -> s.startsWith("b")) //  <---- I was thinking surely you would sort AFTER filtering!
                .sorted((first, second) -> second.compareTo(first)) // use a simple comparator to REVERSE the natural order of Strings
                .forEach(System.out::println);

        // remember the stream operations DO NOT alter the base data construct
        System.out.println("no change to the base data set....");
        System.out.println(stringCollection);
        // ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1
    }

    private class PersonBase {

        String firstname;
        String lastname;

    }

    private class PersonSummary extends PersonBase {
        private String getFullName() {
            return firstname + " " + lastname;
        }

        @Override
        public String toString() {
            return getFullName();
        }
    }

    private class PersonInFull extends PersonBase {
        int age;

        int getAge() { // lazibones-showoff created this getter so that he could use a class level method reference below! :-)
            return age;
        }

        // added this as an afterthought
        private PersonSummary getSummary() {
            final PersonSummary summary = new PersonSummary();
            summary.firstname = this.firstname;
            summary.lastname = this.lastname;
            return summary;
        }

        @Override
        public String toString() {
            return firstname + " " + lastname + "(aged " + age + ")";
        }
    }

    private List<PersonInFull> getPeopleData() {
        final List<PersonInFull> people = new ArrayList<>();

        final PersonInFull simon = new PersonInFull();
        simon.firstname = "Simon";
        simon.lastname = "Drake";
        simon.age = 100;
        people.add(simon);

        final PersonInFull joshua = new PersonInFull();
        joshua.firstname = "Joshua";
        joshua.lastname = "James";
        joshua.age = 15;
        people.add(joshua);

        final PersonInFull kira = new PersonInFull();
        kira.firstname = "Kira";
        kira.lastname = "Drake";
        kira.age = 26;
        people.add(kira);

        return people;
    }

    @Ignore
    @Test
    public void testTheMapFunctionality() {

        final List<String> stringCollection = getStringData();
        stringCollection
                .stream()
                .map(String::toUpperCase) // <-- creates a NEW STREAM with the resulting instances of the "map"
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);
        // "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"

        // lets use some object types
        final List<PersonInFull> people = getPeopleData();
        people.stream()
                .sorted((a, b) -> a.lastname.compareTo(b.lastname))
                .forEach(System.out::println);

        // now do some transformation with this map thing
        people.stream() /// here we have the personInFull type
                .map((p) -> mapperFunction(p)) // NOTE --> I was able to type in that text and then get eclipse to generate the method signature for me
                .sorted((a, b) -> a.getFullName().compareTo(b.getFullName())) // so this line operates on the SUMMARY type
                .forEach(System.out::println);

        // thinking about how to define the mapping code in a more reusable way
        // ... adding a method on to the model class itself
        people.stream() /// here we have the personInFull type
                .map((p) -> p.getSummary()) // <--- and this is what I have come up with!
                .sorted((a, b) -> a.getFullName().compareTo(b.getFullName())) // so this line operates on the SUMMARY type
                .forEach(System.out::println);
    }

    private PersonSummary mapperFunction(final PersonInFull p) {
        final PersonSummary summary = new PersonSummary();
        summary.firstname = p.firstname;
        summary.lastname = p.lastname;
        return summary;
    }

    @Ignore
    @Test
    public void testMatch() {

        final List<String> stringCollection = getStringData();

        final boolean anyStartsWithA = // NOTE how there is a result... so this is a TERMINATOR of the stream
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));
        System.out.println("Do ANY of the elements start with 'a'? " + anyStartsWithA); // true

        final boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println("Do ALL of the elements start with 'a'? " + allStartsWithA); // false

        final boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));

        System.out.println("Do NONE of the elements start with 'z'? " + noneStartsWithZ); // true

    }

    @Ignore
    @Test
    public void testCount() {

        final List<String> stringCollection = getStringData();

        final long startsWithB = // again we have a result so this terminates the stream
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b")) // why count with a stream? Because we can count after a filter!
                        .count();

        System.out.println(stringCollection);
        System.out.println("How many start with the letter 'b'? " + startsWithB); // 3
    }

    @Ignore
    @Test
    public void playingWithReduction() {

        final List<PersonInFull> people = getPeopleData();

        final OptionalDouble averageAge = people.stream()
                .mapToInt(PersonInFull::getAge) // this is using a standard mapper but leveraging a custom type's bespoke method
                .average(); // this calculation automatically reduces the dataset to 1 ... using an Optional as the result

        System.out.println(averageAge.orElse(0)); // "orElse(0)" a shortcut for making it runtime safe

        final Integer totalAge = people
                .stream()
                .mapToInt(PersonInFull::getAge)
                .sum();

        System.out.println("sum used to get the total age > " + totalAge);

        final Integer totalAgeReduce = people
                .stream()
                .map(PersonInFull::getAge)
                .reduce(
                        0, // both the initial value and the return if there are no values in the stream
                        (a, b) -> a + b); // I guess sum() above is just a specific for this kind of operation syntax
        // first element (a) is the accumulated value and the second (b) is the next item in the stream

        System.out.println("Reduction used to get the total age > " + totalAgeReduce);

        // see if I can come up with somehting from this
        final String listOfNames = people
                .stream()
                .map((p) -> p.getSummary().getFullName())
                .reduce("names", (s, s1) -> s + ", " + s1);

        System.out.println(listOfNames);
        // oh yeah baby!!!!
        // gives > names, Simon Drake, Joshua James, Kira Alice

        // NOTE on reduce ... every iteration produces a new value which feeds into the next iteration... ok with numbers, expensive with objects!
        // .... which is where collections comes in :-)

    }

    class Averager implements IntConsumer {
        private int total = 0;
        private int count = 0;

        public double average() {
            return count > 0 ? ((double) total) / count : 0;
        }

        @Override
        public void accept(final int i) {
            total += i;
            count++;
        }

        public void combine(final Averager other) {
            total += other.total;
            count += other.count;
        }
    }

    @Ignore
    @Test
    public void playingWithCollect() {

        final List<PersonInFull> people = getPeopleData();

        final Averager averageCollect = people.stream()
                .filter(p -> "Drake".equals(p.lastname))
                .map(PersonInFull::getAge)
                .collect(Averager::new, // supplier - this is a factory method for the result container
                        Averager::accept, // accumulator - applies the next stream element to the result container
                        Averager::combine); // combiner - merges the result containers (presumably at the end of each iteration)

        System.out.println("Average age of the Drakes is: " + averageCollect.average());

        //
        // collecting the items of a list that you may want
        //
        final List<String> fullNamesOfTheDrakes = people // expecting to get a list of Strings out for the name values of our choice
                .stream() // operating on our DTOs
                .filter(p -> "Drake".equals(p.lastname)) // so filter to the set we want
                .map(p -> p.getSummary().getFullName()) // say we are only interested in a specific value of them, mapping here to String
                .collect(Collectors.toList()); // for the list!
        System.out.println("The Drakes are: " + fullNamesOfTheDrakes);
        // NOTE - in comparison with the previous calculation example, this collect only takes one parameter
        // ... it actually encapsulates the 3 items that are needed

        //
        // you can also collate into categories as well as just extract a single homogenous grouping
        //
        final Map<String, List<PersonInFull>> byName = people
                .stream()
                .collect(
                        // Collectors.groupingBy(Person::getGender));  <-- the cribbed example had a getter and an enum....
                        Collectors.groupingBy((p) -> p.lastname)); /// ... but as I'm lazy, use the actual instance and refer to the field

        // and let's use our knowledge so far to help output the results of the above
        final Set<String> keys = byName.keySet();
        for (final String lastname : keys) {
            final List<PersonInFull> peopleIfSameSurname = byName.get(lastname);
            final String listOfNames = peopleIfSameSurname
                    .stream()
                    .map((p) -> p.getSummary().getFullName())
                    .reduce("", (s, s1) -> s + ", " + s1);
            System.out.println("The " + lastname + "s: " + listOfNames);
        }

        //
        // Now collate the data set by one attribute AND AT THE SAME TIME only collect another attribute!
        // (as opposed to collecting the whole DTO so to speak)
        //
        final List<PersonInFull> people2 = getPeopleData();
        final int sampleAge = people2.get(0).age; // make sure we have some meaningful age realted data
        people2.get(people2.size() - 1).age = sampleAge; // to pass in to the example
        final Map<Integer, List<String>> namesByAge = people2
                .stream()
                .collect(
                        Collectors.groupingBy((p) -> p.age,
                                Collectors.mapping((p) -> p.firstname,
                                        Collectors.toList())));

        final Set<Integer> ageKeys = namesByAge.keySet();
        for (final Integer key : ageKeys) {
            System.out.println("People of age " + key + " are: " + namesByAge.get(key));
        }

        // NOTE > you can use this last approach to apply aggregating functions across a filtered set if data
        //.... eg we could get the average age from the filtered set.
        /// As my test data is not lending itself to that, I've left it.
        // If interested, see the end of this tutorial page:
        // > https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html

    }

    @Ignore
    @Test
    public void parrallel() {

        // create some data
        final int max = 1000000;
        final List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            final UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // sort it with normal single-thread stream
        final long t0 = System.nanoTime();
        final long count = values.stream().sorted().count();
        System.out.println(count);
        final long t1 = System.nanoTime();
        final long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));

        // parallel
        final long tt0 = System.nanoTime();
        final long ccount = values.parallelStream().sorted().count();
        System.out.println(ccount);
        final long tt1 = System.nanoTime();
        final long mmillis = TimeUnit.NANOSECONDS.toMillis(tt1 - tt0);
        System.out.println(String.format("parallel sort took: %d ms", mmillis));
    }

}

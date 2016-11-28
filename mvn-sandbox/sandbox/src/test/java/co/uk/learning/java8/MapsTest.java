package co.uk.learning.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.Ignore;
import org.junit.Test;

public class MapsTest {

    @Ignore
    @Test
    public void mapNewPutsAndComputes() {

        final Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i); // note the new put!
        }

        map.forEach((id, val) -> System.out.println(val));

        System.out.println("====================");

        /*
         * The compute thing is about updating the value in a map based upon a mapping bean. So a NEW value is created and saved against the EXISTING key.
         *
         * If the mapping bean returns null then the original mapping (key->value) is removed from the map!
         *
         * The exact behaviour depends on the compute method variant used
         */

        // the compute function
        map.computeIfPresent(3, (num, val) -> val + num); //
        map.get(3); // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9); // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23); // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3); // val33

        /// so I suppose this is another way of updating one of the entries:
        map.computeIfPresent(5, (num, val) -> "scooby");

        map.forEach((id, val) -> System.out.println(val));

        /*
         * I guess this comes into its own if you have some algorithm you can apply to a map... though you may be able to pass in a method reference to outer
         * code which does something bigger?
         */

    }

    @Ignore
    @Test
    public void mapRemoveAndDefaultGet() {

        final Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i); // note the new put!
        }

        // will only remove if the value matches
        map.remove(3, "not mapped");
        System.out.println(map.get(3)); // val3

        map.remove(3, "val3");
        System.out.println(map.get(3)); // null

        // and defaulting a return value if we have one
        System.out.println(map.getOrDefault(42, "not found"));

    }

    @Test
    public void mergeMap() {

        final Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.putIfAbsent(i, "val" + i); // note the new put!
        }

        /*
         * ok, so point of this one is that you provide a compute function again which will compute a value and the result will be set on the map with the
         * passed in key. HOWEVER the mapper is only called if the map already has that key in it and then the mapper is passed the default value which it may
         * or may not use. If the entry does not already exist, the default value is set against the key.
         *
         *
         */

        // NOTE - key 9 does not yet exist
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9)); // val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9)); // val9concat

        /*
         * Me just seeing if I can do this the old fashioned way
         *
         */
        final BiFunction<String, String, String> myMapper = new BiFunction<String, String, String>() {

            @Override
            public String apply(final String valueInMap, final String input) {

                System.out.println("valueInMap = " + valueInMap);
                System.out.println("input = " + input);

                String retVal = null;
                if (valueInMap != null) {
                    retVal = valueInMap + input;
                }
                return retVal;
            }
        };

        /*
         * This was me playing... it works to a degree but I'm sure it is failing to reproduce some of the intended functionality of this tupe of class ... ...
         * like what the hell is that 3rd type for in the def???
         *
         */
        final String defaultValue = "default"; // <--- just to prove that the default is nothing to do with the mapper
        map.merge(9, defaultValue, myMapper);
        System.out.println(map.get(9)); // val9concatdefault

        map.merge(15, defaultValue, myMapper);
        System.out.println(map.get(15)); // default

        System.out.println(map.get(1)); // val9concat
        map.merge(1, "concat", (value, newValue) -> "ONE"); // this just proves that concat is not used unless it is used by the mapper... or the key is null
        System.out.println(map.get(1)); // val9concat
    }

}

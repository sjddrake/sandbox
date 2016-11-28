package co.uk.learning.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Ignore;
import org.junit.Test;

public class LambdaExpressionsTest {

    @FunctionalInterface
    interface Formula {

        double calculate(int a); //classic interface abstract method definition ... only one allowed if class is to be used in lambdas

        default double sqrt(final int a) { // a default method provided
            return Math.sqrt(a);
        }

        default double add(final int a, final int b) { // can have more than one defaulted method
            return a + b;
        }
    }

    @Ignore
    @Test
    public void playingWithExtensionMethods() {

        // the standard Java way of creating an anonymous object
        final Formula formula = new Formula() {
            @Override
            public double calculate(final int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println("This was from the concrete implementation: " + formula.calculate(100)); // 100.0
        System.out.println("This was provided by the extension method on the interface: " + formula.sqrt(16)); // 4.0
        System.out.println("This was provided by the extension method on the interface: " + formula.add(16, 4)); // 20.0

        // the standard Java way of creating an anonymous object
        final Formula formula2 = new Formula() {
            @Override
            public double calculate(final int a) {
                return sqrt(a * 100);
            }

            @Override
            public double add(final int a, final int b) { // and this is us overriding what's defaulted in the interface
                return a - b;
            }
        };
        System.out.println("This was from the concrete implementation: " + formula2.calculate(100)); // 100.0
        System.out.println("This was provided by the extension method on the interface: " + formula2.sqrt(16)); // 4.0
        System.out.println("This was an override of the default method: " + formula2.add(16, 4)); // 20.0

        // .....so.... you can sort of do multiple inheritance of functions... but that's all as interfaces do not have state (well, only "final" members)
        /// NOTE ... and that is the differnce between an interface with default methods and an abstract class

        //
        // THE GOTCHA... as we will see, lambda functions can replace the verbose anonymous object construct ... BUT NOT TO ACCESS DEFAULT METHODS :(
        //
        // what we can do is...
        final Formula formla = (a) -> (a * 100); // use the lambda to define the single abstract method which gives us an instance
        System.out.println(formla.calculate(2)); // this is us using the lamda
        System.out.println(formla.sqrt(2)); // this is us using the provided default method

        // But there's no way to redefine the default method
        // ... which is logical because in a lambda you DONT name the method that you are implementing so
        // if we start trying to do that now, of course it will faiL!
        //
        // Formula formla = (a) -> sqrt( a * 100);   ... this will NOT compile

    }

    @Ignore
    @Test
    public void theLambdaExpressionSyntaxInSteps() {

        // final String[] testNames = { "peter", "anna", "mike", "xenia" };
        final List<String> testNames = Arrays.asList("peter", "anna", "mike", "xenia");

        // traditional anon class approach
        List<String> names = new ArrayList<>(testNames);
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(final String a, final String b) {
                return a.compareTo(b);
            }
        });
        System.out.println(names);
        /*
         * so we know:
         *
         * sort expects a given type which has the method that will be called the method gives us the signature
         */

        //
        // The half-house lambda
        //
        names = new ArrayList<>(testNames);
        System.out.println(names);

        Collections.sort(names, (final String a, final String b) -> {
            return b.compareTo(a);
        });
        System.out.println(names);
        /*
         * so we know:
         *
         * stating the signature that we will be using (at least the inputs!) the inputs are "passed in" (hence arrow?) to the body the body is provided inline
         * which is just like an anonymous class
         */

        //
        // The full lambda
        //
        names = new ArrayList<>(testNames);
        System.out.println(names);

        Collections.sort(names, (final String a, final String b) -> b.compareTo(a));
        System.out.println(names);

        /*
         * so we know:
         *
         * the bounds of the method body and the return keyword aren't required IF ITS A ONE-LINE BODY
         */

        //
        // The full lambda cut down further!
        //
        names = new ArrayList<>(testNames);
        System.out.println(names);

        Collections.sort(names, (a, b) -> b.compareTo(a));
        System.out.println(names);

        /*
         * so we know:
         *
         * the input params types aren't needed because the compiler knows what is being passed in thanks to the enclosing method
         */

        // an experiment: can we make the lambda even smaller... by having no inputs
        final ScoobyLogTimeStamper logTimeStamper = () -> System.out.println("The time is now: " + new Date()); // so this works
        logTimeStamper.logTimeStamp();
        executeLogTimeStamp(logTimeStamper);

        //
        // THIS DOESN'T compile... I tried leaving out "()"
        //
        // final ScoobyLogTimeStamper logTimeStamper2 = -> System.out.println("The time is now: " + new Date());
        // logTimeStamper2.logTimeStamp();
        // executeLogTimeStamp(logTimeStamper2);

        // TYR ANONOMOUSLY
        // executeLogTimeStamp(-> System.out.println("The time is now: " + new Date()));   ... nah, cant leave out "()"
        executeLogTimeStamp(() -> System.out.println("An anon time is log: " + new Date()));

    }

    private void executeLogTimeStamp(final ScoobyLogTimeStamper logTimeStamper) {
        logTimeStamper.logTimeStamp();
    }

    @FunctionalInterface
    interface ScoobyLogTimeStamper<S, T> {
        void logTimeStamp();
    }

    @FunctionalInterface
    interface ScoobyLogger<S, T> {
        void log(S textValue1, T textValue2);
    }

    @Ignore
    @Test
    public void playingWithACustomFunctionalInterface() {

        final ScoobyLogger<String, String> logger1 = (textValue1, textValue2) -> System.out.println(textValue1 + " concatenated to " + textValue2);
        logger1.log("Scooby", "Doo");
        /*
         * wow - so this is like defining a local method... well, actually its a local implementation of the interface ... but the interface only has one method
         * so we dont need to specify it in the code.
         *
         */
        final ScoobyLogger<String, Integer> logger2 = (textValue1, numValue2) -> System.out.println(textValue1 + " added to " + numValue2);
        logger2.log("Scooby", 99);
        /*
         * wow - getting polymorphioc with this duding thing!
         *
         *
         * So we're specifying a capability but leaving the implementation to a throw-away bit of code
         *
         *
         * We could have a private method on the containing class doing this thing and it could be overloaded too but if this is throwaway code, then we aren't
         * polluting the class
         *
         * ... probably find though that this is more for supporting OTHER new syntactical constructs
         *
         */
    }

    class Something {
        String startsWith(final String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    @Ignore
    @Test
    public void playingWithMethodReferences() {
        //
        // static references
        //
        {
            final Converter<String, Integer> converter = Integer::valueOf;
            final Integer converted = converter.convert("123");
            System.out.println(converted); // 123
        }

        //
        // instance reference
        //
        {
            final Something something = new Something();
            final Converter<String, String> converter = something::startsWith;
            final String converted = converter.convert("Java");
            System.out.println(converted); // "J"
        }
        /*
         * So this is like having disparate implementations of a similar activity that can all be treated through the same interface WITHOUT ACTUALLY TYING
         * those implementations to the interface in question... so we're leveraging the power of other code anonymously.
         *
         */
    }

    class Person {
        String firstName;
        String lastName;

        int age;
        String sex;

        Person() {
        }

        Person(final String firstName, final String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        void dump() {
            System.out.println("Hello Mr " + firstName + " " + lastName + ", aged: " + age);
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

    @Ignore
    @Test
    public void playingWithPassingConstructorReferences() {

        final PersonFactory<Person> personFactory = Person::new;
        final Person person = personFactory.create("Peter", "Parker");
        person.dump();
        /*
         * So the 'new' keyword obviosly links to the constructor and we've specified the class as Person (like static reference) and the Interface's method's
         * signature will allow the compiler to link to the right constructor.... tried adding another String and eclipse showed the applicable compilation
         * method :-)
         *
         * So can use this approach to pass a factory to a framework / generic class that needs to build instances but doesnt know what instance to build
         *
         */
    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    static int outerStaticNum;
    int outerNum;

    @Ignore
    @Test
    public void lambdaScopes() {

        //
        // use of local method variables within the lambda expersssion needs final OR IMPLIED FINAL
        //

        {
            final int num = 1; // so this value will be used in the lambda's body
            final Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
            final String value = stringConverter.convert(2); // 3
            System.out.println(value);
        }

        {
            final int num = 1; // THE COMPILER WILL ALLOW THIS VARIABLE TO BE NOT FINAL... but my formatting rules wont! ;-)
            final Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
            final String value = stringConverter.convert(2); // 3
            // num = 5;  ... however, if you try and change the value of the outer variable, compiler does then complain!
            System.out.println(value);
        }

        // NOTE > haven't tried to prove it but YOU CAN'T write to the passed-in outer variable from within the lamda

        //
        // Conversely... the class level attributes of the outer class CAN BE WRITTEN TO
        // (just like for anonnymous classes)
        //

        // EXAMPLE of being able to change the value of the OUTER INSTANCE VARIABLE
        final Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        // EXAMPLE of being able to change the value of the OUTER STATIC VARIABLE
        final Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }

    @Ignore
    @Test
    public void playingWithPredicates() {

        final Predicate<String> isStringTooLong = (s) -> s.length() > 16;
        final String tooBig = "I Bet this string is too big. Is that true? ";
        System.out.println(tooBig + isStringTooLong.test(tooBig));
        final String small = "tiny is best? ";
        System.out.println(small + isStringTooLong.test(small));

        final Predicate<String> isStringEmpty = String::isEmpty; // this is just showing off the lamda shorthand
        final String emptyString = "";
        System.out.println("If this works then the empty String variable should produce true here > " + isStringEmpty.test(emptyString));

        final String myName = "Simon";
        final Predicate<String> isMyNameBaby = myName::equals; // this is just showing off the lamda shorthand
        final String aTestname = "Simon";
        System.out.println("If this works then the String variable with a test name should produce true here > " + isMyNameBaby.test(aTestname));

        //
        // But the real power comes from chaining these predicates
        //
        System.out.println("get this > " + isMyNameBaby.and(isStringTooLong).negate().test(aTestname));

        // ofcourse, as a Predicate is an instance of an object, you can pass them into methods... after all, that's what chaining is
        onlyDoThisIfThePredicatePasses(String::isEmpty, myName);
        onlyDoThisIfThePredicatePasses(String::isEmpty, "");
        final Integer aTestNumber = 13;
        onlyDoThisIfThePredicatePasses(aTestNumber::equals, 11);

        // so...
        //
        // I guess if you have a situation where you want to do the same operation on different things in differen scenarios
        // then you could use this approach of sending in the applicable predicate with the applicable data and let the
        // execution method decide whether to fulfil its operation RATHER than not calling it... I guess that helps if
        // there are some steps in that method that you want executnig but not others. All the loose logic we had with Spring registered
        // manifest generation and column types and what is and isnt valid (PearsonNALS) might benefit from that???
        //

    }

    private <T> void onlyDoThisIfThePredicatePasses(final Predicate<T> predicate, final T testThis) {
        if (predicate.negate().test(testThis)) {
            System.out.println("we're good to go on " + testThis.toString() + "!");
        } else {
            System.out.println("I'm skipping " + testThis.toString() + "!");
        }
    }

    @Ignore
    @Test
    public void playingWithFunctions() {

        final Function<String, Integer> stringLength = (s) -> s.length();

        // Will print 11
        final String helloWorld = "Hello world";
        System.out.println(helloWorld + " has " + stringLength.apply(helloWorld) + " characters in it.");

        final Function<String, String> addThisText = (s) -> s.toString();
        final Function<String, String> addWhiteSpace = (s) -> s + " ";
        final Function<String, String> addBoom = (s) -> s + "Boom!";

        System.out.println(addThisText.apply("Hello"));
        System.out.println(addThisText.andThen(addWhiteSpace).andThen(addBoom).apply("Hello"));

        // you can use functions in a similar way to predicates in that you can operate on the input value
        // as part of your testing.

        {
            // example from website
            final Function<String, Integer> stringLength2 = (s) -> s.length();
            final Function<Integer, Boolean> lowerThanTen = (i) -> i < 10;
            final Function<String, Boolean> function = stringLength2.andThen(lowerThanTen);

            // Will print false
            function.apply("Hello world");
        }

        {
            final String sex = "male";
            final int age = 25;
            final boolean timeToEnjoyLife = oldSchoolWorkingItOut(sex, age);
            if (timeToEnjoyLife) {
                System.out.println("Time to enjoy life!");
            } else {
                System.out.println("Get back to work!");
            }

            // damn - was hoping I could come up with a new-school version based on predicates but my example doesnt chain
            // as there are two different inputs of data... could probably make it work if I used a Person object rather
            // than the atomic valuse themselves... because in each step you can choose what object field to work on
            // ... ok, lets do it!
            final Person mrBloggs = new Person();
            mrBloggs.age = 69;
            mrBloggs.sex = "male";
            // No, still cant chain because the output of Boolean needs to be the input to the next in the chain
            // but we want to keep testing the person... this needs predicates!
            // final Function<Person, Boolean> checkSex = (p) -> p.sex.equals("male");
            // final Function<Person, Boolean> checkAge = (p) -> p.sex.equals("male");

            final Predicate<Person> checkForMale = (p) -> p.sex.equals("male");
            final Predicate<Person> checkForMaleRetirementAge = (p) -> p.age > 75;
            final Predicate<Person> checkForFemaleRetirementAge = (p) -> p.age > 60;
            System.out.println("Will Mr Bloggs have male retirement?" + checkForMale.and(checkForMaleRetirementAge).test(mrBloggs));
            System.out.println("Will Mr Bloggs have female retirement?" + checkForMale.negate().and(checkForFemaleRetirementAge).test(mrBloggs));
            /// hmmm, so cant get the full function of the "old school" code into one line of predicates even... so not worth using them in a sense

        }
    }

    private boolean oldSchoolWorkingItOut(final String sex, final int age) {
        final boolean timeToEnjoyLife;
        if (sex == "male") {
            if (age > 70) {
                timeToEnjoyLife = true;
            } else {
                timeToEnjoyLife = false;
            }
        } else {
            if (age > 60) {
                timeToEnjoyLife = true;
            } else {
                timeToEnjoyLife = false;
            }
        }
        return timeToEnjoyLife;
    }

    int supplierAgeIncrementer = 0;

    @Ignore
    @Test
    public void suppliersAndConsumers() {

        //
        // Suppliers produce a result of the stated type but DO NOT accept any inputs
        //

        // another use of the constructor syntax... could pass a Supplier to a framework then!
        final Supplier<Person> personFactory = Person::new;
        final Person john = personFactory.get();
        john.dump();

        // try something more involved... my expo
        // Supplier<Person> personSupplier = () -> new Person(); ... quick syntax check
        final Supplier<Person> personSupplier = () -> {
            final Person person = new Person();
            person.age = ++supplierAgeIncrementer;
            return person;
        };
        final Person simon = personSupplier.get();
        simon.dump();
        final Person peter = personSupplier.get();
        peter.dump();

        //
        // A consumer does the opposite!
        //

        final Consumer<Person> consumer = (p) -> System.out.println("My age is " + p.age);
        consumer.accept(john);
        consumer.accept(simon);
        consumer.accept(peter);
    }

    @Ignore
    @Test
    public void comparatorSyntax() {

        // there's a new way to define a comparator and you can also add Predicate like chaining of handy methods
        // - notice the use of "compareTo" to make it very readable on one line... if not null safe!
        final Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

        final Person p1 = new Person("John", "Doe");
        // final Person p2 = new Person(null, "Wonderland");   ....ype, definitely not null safe!! :-)
        final Person p2 = new Person("Alice", "Wonderland");

        comparator.compare(p1, p2); // > 0

        // and note the chaining!
        comparator.reversed().compare(p1, p2); // < 0

    }

    @Test
    public void optionalPlaytime() {

        Optional<String> optional = Optional.of("bam");
        System.out.println("The Optional is loaded with the String value 'bam'");
        System.out.println("optional.isPresent(): " + optional.isPresent()); // true
        System.out.println("optional.get(): " + optional.get()); // "bam"
        System.out.println("optional.orElse('fallback'): " + optional.orElse("fallback")); // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0))); // "b"

        optional = Optional.empty();
        System.out.println("The Optional is NOW loaded with the NULL");
        System.out.println("optional.orElse('fallback'): " + optional.orElse("fallback")); // "fallback"
        // System.out.println("optional.get(): " + optional.get()); // EXCEPTION! "No Value Present"

        // So... last one illustrates that we need to test for a value before calling get()

    }

}

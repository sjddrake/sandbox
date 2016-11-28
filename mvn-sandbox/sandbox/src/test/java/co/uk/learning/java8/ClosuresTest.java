package co.uk.learning.java8;

import java.util.function.Supplier;

import org.junit.Test;

public class ClosuresTest {

    /*
     * This example comes from this stackoverflow article:
     *
     * https://stackoverflow.com/questions/17204279/does-java-8-support-closures
     *
     */

    @Test
    public void showHowContainersInfluenceLambdaExpressions_enclosed() {

        showTestHeader("showHowContainersInfluenceLambdaExpressions_enclosed");

        // creating an instance of this functional interface with a lambda
        // - ie a re-usable callable method... and it will have its own state
        final Supplier<Supplier<String>> mutterfunktion = () -> {

            final int container[] = { 0 }; // <--- NOTE how the array "container" is final
            // the lambda is itself using a lambda to implement
            return () -> {
                container[0]++; // <-- we can update the value within the container in the outer lambda
                return "Ich esse " + container[0] + " Kuchen.";
            };
        };

        // here's our application code using the lamda instance (outer)
        final Supplier<String> essen = mutterfunktion.get();
        System.out.println(essen.get());
        System.out.println(essen.get());
        System.out.println(essen.get());
        // gives -> Ich esse 1 Kuchen. // Ich esse 2 Kuchen. // Ich esse 3 Kuchen.
        // so can see that the value in the container is able to be updated
        // ALSO the container and the value are hidden from the application code

    }

    private void showTestHeader(final String testName) {
        System.out.println("======================== " + testName + "======================== ");
    }

    @Test
    public void showHowContainersInfluenceLambdaExpressions_visibleToAppCode() {

        showTestHeader("showHowContainersInfluenceLambdaExpressions_visibleToAppCode");

        // this lambda has access to the container which is within
        // the application code method scope so can be interfered with!
        final int container[] = { 0 };
        final Supplier<String> essen = () -> {
            container[0]++;
            return "Ich esse " + container[0] + " Kuchen.";
        };
        System.out.println(essen.get());
        System.out.println(essen.get());
        container[0]++;
        System.out.println(essen.get());
        // gives -> Ich esse 1 Kuchen. // Ich esse 2 Kuchen. // Ich esse 4 Kuchen.
    }

    @Test
    public void showHowContainersInfluenceLambdaExpressions_buildLambdaInSeparateMethod() {

        showTestHeader("showHowContainersInfluenceLambdaExpressions_visibleToAppCode");

        // this lambda builds its own private variable in a separate method
        // so is that not going to achieve the same thing... IE hide the container?
        // ... but the argument is, Simon, that the truly "enclosed closure" could
        // be in a separate class and the application code would not have to be
        // dependent on the classes that the lambda is dependent upon.
        final Supplier<String> essen = getEssen();
        System.out.println(essen.get());
        System.out.println(essen.get());
        System.out.println(essen.get());
        // gives -> Ich esse 1 Kuchen. // Ich esse 2 Kuchen. // Ich esse 3 Kuchen.
    }

    private Supplier<String> getEssen() {
        final int container[] = { 0 };
        final Supplier<String> essen = () -> {
            container[0]++;
            return "Ich esse " + container[0] + " Kuchen.";
        };
        return essen;
    }

}

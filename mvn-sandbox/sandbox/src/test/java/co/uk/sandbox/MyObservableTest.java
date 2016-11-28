package co.uk.sandbox;

import org.junit.Test;

public class MyObservableTest {

    @Test
    public void test() {
        final MyService service = new MyService();
        service.addObserver(new MyObserver("Observant #1"));
        service.addObserver(new MyObserver("Observant #2"));
        service.addObserver(new MyObserver("Observant #3"));
        service.postAMessage("Boom!", "the-app");
        service.postAMessage("Oh Yeah!", "the-app");
    }

}

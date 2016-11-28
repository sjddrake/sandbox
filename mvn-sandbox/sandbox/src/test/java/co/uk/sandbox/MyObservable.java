package co.uk.sandbox;

import java.util.Observable;

public class MyObservable extends Observable {

    //    private MyService service;
    //
    //    public void setService(final MyService myService) {
    //        service = myService;
    //    }

    public void broadcast(final MyService myService) {
        super.setChanged();
        super.notifyObservers(myService);
    }

}

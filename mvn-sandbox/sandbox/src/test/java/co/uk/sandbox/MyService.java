package co.uk.sandbox;

import java.util.Observer;

public class MyService {

    MyObservable telegraph = new MyObservable();
    String lastMessage = null;

    public void postAMessage(final String message, final String user) {
        lastMessage = message;
        System.out.println(user + " left this message: " + message);
        telegraph.broadcast(this);
    }

    public void readLastMessage(final String user) {
        System.out.println(user + " READ this message: " + lastMessage);
    }

    public synchronized void addObserver(final Observer o) {
        telegraph.addObserver(o);
    }

    public synchronized void deleteObserver(final Observer o) {
        // TODO Auto-generated method stub
        telegraph.deleteObserver(o);
    }

    public synchronized void deleteObservers() {
        telegraph.deleteObservers();
    }

    public synchronized boolean hasChanged() {
        return telegraph.hasChanged();
    }

    public synchronized int countObservers() {
        return telegraph.countObservers();
    }

    //    public MyService() {
    //        telegraph.setService(this);
    //    }

}

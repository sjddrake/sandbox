package co.uk.sandbox;

import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer {

    private String user = null;

    public MyObserver(final String userName) {
        this.user = userName;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        /*
         * REMEMBER
         *
         * The Observable is the delegate I created on the service so that could have a reference to the service and hence we could get at the service that way,
         * leaving arg free for a DTO.
         *
         * OR the Observable delegate itself could have a strongly designed API so that the service is protected and it could manage what to expose and what not
         * to.
         *
         * This time though I've just passed the service reference as arg so we'll get the reference from there...just for this experiment!
         *
         */

        System.out.println(user + " is about to read the message");
        //        System.out.println(o);
        //        System.out.println(arg);
        ((MyService) arg).readLastMessage(this.user);
    }

}

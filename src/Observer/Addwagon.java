package Observer;

import Trains.TrainStorage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Benzuin on 28/12/2016.
 */
public class Addwagon implements Observer {

    private String wagon;
    private int seats;

    public Addwagon(Observable observable) {
        observable.addObserver(this);
    }

    public void update(Observable obs, Object arg) {
        if (obs instanceof TrainStorage) {
            TrainStorage storage = (TrainStorage) obs;
            this.wagon = storage.getWagon();
            this.seats = storage.getWagonseats();
        }
    }
}
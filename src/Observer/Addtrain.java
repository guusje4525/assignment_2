package Observer;

import Trains.TrainStorage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Benzuin on 28/12/2016.
 */
public class Addtrain implements Observer {

    Observable observable;
    private String train;

    public Addtrain(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    public void update(Observable obs, Object arg) {
        if (obs instanceof TrainStorage) {
            TrainStorage storage = (TrainStorage) obs;
            this.train = storage.getTrain();
        }
    }
}

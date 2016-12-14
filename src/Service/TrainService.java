package Service;

import Controllers.Controller;
import Trains.Train;
import Trains.TrainStorage;
import Trains.Wagon;

import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class TrainService {

    public static void main(String[] args){

        TrainStorage.addTrain("trein1");
        TrainStorage.addTrain("trein2");
        TrainStorage.addTrain("trein3");

        TrainStorage.addWagon("trein1", "wagon1", 50);
        TrainStorage.addWagon("trein1", "wagon2", 30);
        TrainStorage.addWagon("trein2", "wagon3", 10);
        TrainStorage.addWagon("trein2", "wagon4");
        TrainStorage.addWagon("trein3", "wagon5");
        TrainStorage.addWagon("trein3", "wagon6");
        TrainStorage.addWagon("trein3", "wagon7");
        TrainStorage.addWagon("trein5", "wagon70");
        TrainStorage.addWagon("trein78", "wagon70");

        Controller.start();

    }

}

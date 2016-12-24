package Dao;

import Trains.Train;
import Trains.Wagon;

import java.util.List;

public interface TrainDao {
	void addWagon(Wagon w);
	void addTrain(Train t);
	void addWagonToTrain(Wagon w, Train t);

    List<Train> getTrains();
}

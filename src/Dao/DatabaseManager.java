package Dao;

import Trains.Train;
import Trains.Wagon;

import java.util.List;

/**
 * Created by Guus on 2/6/2017.
 */
public interface DatabaseManager {

    void createConnection();
    void addObject(Object obj);
    void deleteObject(Object obj);
    List<Train> getTrains();
    List<Wagon> getWagons();

}

package Trains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Guus on 12/14/2016.
 */
public class TrainStorage {

    private static List<Train> trains = new ArrayList<Train>();

    public static List<Train> getTrains(){
        return trains;
    }

    public static void addTrain(String trainName){
        if(trains.stream().filter(train -> train.getName().equals(trainName)).collect(Collectors.toList()).isEmpty()) trains.add(new Train(trainName));
    }

    public static boolean trainExist(String trainName) {
        boolean exist = false;
        for(Train train : trains){
            if(train.getName().equals(trainName)) exist = true;
        }
        return exist;
    }

    public static void addWagon(String trainName, Wagon wagon){
        for(Train train : trains){
            if(train.getName().equals(trainName)) train.addWagon(wagon);
        }
    }

}

package Trains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainStorage {

    private static List<Train> trains = new ArrayList<Train>();

    public static List<Train> getTrains(){
        return trains;
    }

    public static void addTrain(String trainName){
        //ik weet het, ik doe moelijk maar het ziet er wel grappig uit :)
        if(trains.stream().filter(train -> train.getName().equals(trainName)).collect(Collectors.toList()).isEmpty()) trains.add(new Train(trainName));
    }

    public static boolean trainExist(String trainName) {
        for(Train train : trains) if(train.getName().equals(trainName)) return true;
        return false;
    }

    public static boolean wagonExist(String wagonName) {
        for (Train train : trains) {
            for (Wagon wagon : train.getWagons()) {
                if (wagon.getWagon().equals(wagonName)) return true;
            }
        }
        return false;
    }

    public static void addWagon(String trainName, String wagonName){
        for(Train train : trains){
            if(train.getName().equals(trainName)) train.addWagon(new Wagon(wagonName));
        }
    }

    public static void addWagon(String trainName, String wagonName, int seats){
        for(Train train : trains){
            if(train.getName().equals(trainName)) train.addWagon(new Wagon(wagonName, seats));
        }
    }

    public static int getWagonSeats(String wagonName){
        for(Train train : trains){
            for(Wagon wagon : train.getWagons()){
                if(wagon.getWagon().equals(wagonName)) return wagon.getSeats();
            }
        }
        //No wagon found, fallback, controller will catch this
        return -1;
    }

    public static String getTrainSeats(String trainName){
        int totalSeats = 0;
        for(Train train : trains){
            if(train.getName().equals(trainName)) return "" + train.getSeats();
        }
        return "No train found";
    }

    public static int getAllSeats(){
        int totalSeats = 0;
        for(Train train : trains){
            totalSeats += train.getSeats();
        }
        return totalSeats;
    }

    public static boolean deleteTrain(String trainName){
        for (Iterator<Train> iter = trains.listIterator(); iter.hasNext(); ) {
            Train train = iter.next();
            if (train.getName().equals(trainName)) {
                iter.remove();
                return true;
            }
        }
        //No train found
        return false;
    }

    public static boolean deleteWagon(String wagonName){

        for(Train train : trains){
            for (Iterator<Wagon> iter = train.getWagons().listIterator(); iter.hasNext(); ) {
                Wagon wagon = iter.next();
                if (wagon.getWagon().equals(wagonName)) {
                    iter.remove();
                    return true;
                }
            }
        }
        //No wagon found
        return false;
    }

}

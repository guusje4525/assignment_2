package Trains;

import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class Train {

    private ArrayList<Wagon> wagons = new ArrayList<>();
    private String name;

    public Train() {
    }

    public Train(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    
    public void setName(String n) {
    	name = n;
    }

    public ArrayList<Wagon> getWagons(){
        return wagons;
    }

    public void addWagon(Wagon wagon){
        wagons.add(wagon);
    }

    public int getSeats(){
        int totalSeats = 0;
        for(Wagon w : wagons){
            totalSeats += w.getSeats();
        }
        return totalSeats;
    }

}

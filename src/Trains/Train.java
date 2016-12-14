package Trains;

import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class Train {

    private ArrayList<Wagon> wagons = new ArrayList<>();
    private String name;

    public Train(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addWagon(Wagon wagon){
        wagons.add(wagon);
    }

}

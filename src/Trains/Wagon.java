package Trains;

/**
 * Created by Guus on 12/12/2016.
 */
public class Wagon {

    private String name;
    private int seats = 20; //20 is default

    public Wagon(String name){
        this.name = name;
    }

    public Wagon(String name, int seats){
        this.name = name;
        this.seats = seats;
    }

    public String getWagon(){
        return name;
    }

    public int getSeats(){
        return seats;
    }
    
    public void setSeats(int s) {
    	seats = s;
    }
}

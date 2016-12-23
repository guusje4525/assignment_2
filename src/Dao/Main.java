package Dao;

import java.sql.SQLException;

import Trains.Train;

public class Main {

	public static void main(String[] args) throws SQLException {
		Train t = new Train("id2");
		TrainDaoImpl.addTrain(t);	
	}
}

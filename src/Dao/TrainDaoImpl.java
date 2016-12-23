package Dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import Trains.Train;
import Trains.Wagon;

public class TrainDaoImpl {

	public static Connection Connection() {
		System.out.println("Testing database connection...");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is the Oracle JDBC Driver?");
			e.printStackTrace();
			return null;
		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@ondora02.hu.nl:8521/cursus02.hu.nl", 
				"tocba_2016_2c_team5", "tocba_2016_2c_team5");
			System.out.println("Connection Succes!");
			return connection;
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}

	public static void addTrain(Train t) throws SQLException{
		String id = t.getName();
		Statement stmt = Connection().createStatement();
		stmt.executeQuery("INSERT INTO TRAIN (ID) VALUES ('"+id+"')");
		stmt.close();	
		Connection().close();

	}
	
	public static void addWagon(Wagon w) throws SQLException{
		String id = w.getWagon();
		int seats = w.getSeats();
		Statement stmt = Connection().createStatement();
		stmt.executeQuery("INSERT INTO WAGON (ID, NUMSEATS) VALUES ('"+id+"',"+seats+")");
		stmt.close();	
		Connection().close();

	}
	
	public static void addWagonToTrain(Wagon w, Train t) throws SQLException{
		String wagonid = w.getWagon();
		String trainid = t.getName();
		Statement stmt = Connection().createStatement();
		stmt.executeQuery("INSERT INTO TRAINWAGON (TRAINID, WAGONID) VALUES ('"+trainid+"','"+wagonid+"')");
		stmt.close();
		Connection().close();
	}
}
package Dao;

import Trains.Train;
import Trains.Wagon;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Guus on 2/6/2017.
 */
public class SQLLiteManager implements DatabaseManager {

    private SessionFactory factory;

    public void createConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException eString) {
            System.out.println("Could not init JDBC driver - driver not found");
        }

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public List<Wagon> getWagons() {
        if (factory == null) createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Wagon> wagons = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List wagonsFromDB = session.createQuery("FROM Wagon").list();
            for (Iterator iterator = wagonsFromDB.iterator(); iterator.hasNext(); ) {
                wagons.add((Wagon) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return wagons;
    }

    public void addObject(Object obj) {
        if (factory == null) createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteObject(Object obj) {
        if(obj == null) return;
        if (factory == null) createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Train> getTrains() {
        if (factory == null) createConnection();
        List<Wagon> wagons = getWagons();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Train> trains = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List products = session.createQuery("FROM Train").list();
            for (Iterator iterator = products.iterator(); iterator.hasNext(); ) {
                Train train = (Train) iterator.next();
                trains.add(train);
                for (Wagon wagon : wagons) if (wagon.getTrainName().equals(train.getName())) train.addWagon(wagon);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return trains;
    }

}

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
 * Created by Guus on 12/28/2016.
 */
public class dbController {

    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        if (factory == null) createFactory();
        return factory;
    }

    public static void createFactory() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static List<Wagon> getWagonsFromDB() {
        if (factory == null) createFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Wagon> wagons = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List products = session.createQuery("FROM Wagon").list();
            for (Iterator iterator = products.iterator(); iterator.hasNext(); ) {
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

    public static void addObject(Object obj) {
        if (factory == null) createFactory();
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

    public static void deleteObject(Object obj) {
        if (factory == null) createFactory();
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

    public static List<Train> getTrainsFromDB() {
        if (factory == null) createFactory();
        List<Wagon> wagons = getWagonsFromDB();
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

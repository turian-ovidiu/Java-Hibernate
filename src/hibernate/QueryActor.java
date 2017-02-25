package hibernate;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class QueryActor {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Actor.class).buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            /*
            session = factory.getCurrentSession();
            session.beginTransaction();
            Actor jack = new Actor("Jack","Dooo");
            Actor bob = new Actor("Bob","Foo");
            session.save(jack);
            session.save(bob);
            session.getTransaction().commit();
            session.close();
            */


            session = factory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Actor a where a.id>150");
            query.setMaxResults(10);
            List<Actor> list = query.list();
            List<String> listName = (List<String>) query.list();
            displayActor(list);
            session.getTransaction().commit();



            /*
            session = factory.getCurrentSession();
            session.beginTransaction();
            Query query =session.createQuery("select firstName from Actor");
            query.setMaxResults(25);
            List<String> listName = (List<String>) query.list();
            displayString(listName);
            session.getTransaction().commit();




            session = factory.getCurrentSession();
            session.beginTransaction();
            String minUserId = "5";
            String firstName = "Joe";
            query = session.createQuery("from Actor a where a.id> :userId and a.firstName= :userName");
            query.setInteger("userId", Integer.parseInt(minUserId));
            query.setString("userName", firstName);
            List<Actor> list = query.list();
            displayActor(list);
            session.getTransaction();


            session = factory.getCurrentSession();
            session.beginTransaction();
            query = session.getNamedQuery("UserDetails.byId");
            query.setInteger(0, 10);
            list = query.list();
            displayActor(list);
            session.getTransaction().commit();
            session.close();
            */

            //If we want to use query like in SQL .
            /*
			session = factory.getCurrentSession();
			session.beginTransaction();
			query = session.getNamedQuery("UserDetails.byName");
			query.setString(0, "Joe");
			list = query.list();
			displayActor(list);
			session.getTransaction().commit();
			session.close();
			*/

            //Important  Criteria
            session = factory.getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Actor.class);
            criteria.add(Restrictions.gt("id", 190))
                    .add(Restrictions.like("firstName", "%hn"))
                    .add(Restrictions.between("id", 203, 205));
            //.add(Restrictions.eq("firstName","Karl"))
            list = criteria.list();
            displayActor(list);
            session.getTransaction().commit();


            session = factory.getCurrentSession();
            session.beginTransaction();
            Criteria criteria2 = session.createCriteria(Actor.class);
            criteria2.add(Restrictions.or(Restrictions.between("id", 1, 10), Restrictions.between("id", 40, 65)));
            list = criteria2.list();
            displayActor(list);
            session.getTransaction().commit();


            session = factory.getCurrentSession();
            session.beginTransaction();
            Criteria criteria3 = session.createCriteria(Actor.class)
                    .setProjection(Projections.property("firstName"));

            listName = criteria3.list();
            displayString(listName);
            session.getTransaction().commit();


            Actor exampleActor = new Actor();
            exampleActor.setId(218);
            exampleActor.setFirstName("Jack");
            //  exampleActor.setFirstName("J%");
            Example example = Example.create(exampleActor);
            //  Example example = Example.create(exampleActor).enableLike();
            //	Example example = Example.create(exampleActor).excludeProperty("id");
            session = factory.getCurrentSession();
            session.beginTransaction();
            Criteria criteria4 = session.createCriteria(Actor.class)
                    .add(example);

            list = criteria4.list();
            displayActor(list);
            session.getTransaction().commit();
            session.close();

        } finally {
            factory.close();
        }
    }

    private static void displayActor(List<Actor> list) {
        for (Actor actor : list) {
            System.out.println(actor);
        }
    }

    private static void displayString(List<String> strgList) {
        for (String a : strgList) {
            System.out.println(a);
        }
    }


}

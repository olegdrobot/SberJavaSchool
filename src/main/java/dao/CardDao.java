package dao;

import model.Card;
import model.CardDate;
import model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CardDao {
    private SessionFactory sessionFactory;
    private Session session;

    private void setConnect() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Card.class)
                .buildSessionFactory();
        this.session = null;
        this.session = sessionFactory.getCurrentSession();
        this.session.beginTransaction();
    }

    public void createCard(int year, int month, int day, int[] CardNumber, Client client, boolean autoSetExpDate) {
        setConnect();
        Card card = new Card(year, month, day, CardNumber, client, autoSetExpDate);
        session.save(card);
        session.getTransaction().commit();
        sessionFactory.close();
    }

    public List<Card> getAll() {
        setConnect();
        List<Card> cardList = session.createQuery("SELECT i FROM Card i", Card.class).getResultList();
        session.getTransaction().commit();
        sessionFactory.close();
        return cardList;
    }

    public Card getById(int id) {
        setConnect();
        Card cardFromDB = session.get(Card.class, id);
        session.getTransaction().commit();
        sessionFactory.close();
        return cardFromDB;
    }

    public void update(Card updatedCard, int id) {
        setConnect();
        Card cardFromDB = session.get(Card.class, id);
        cardFromDB.setCardNumber(updatedCard.getCardNumber());
        CardDate cardDate = updatedCard.getExpirationDate();
        cardFromDB.setExpirationDate(cardDate.getYear(), cardDate.getMonth(), cardDate.getDay());
        cardFromDB.setClient(updatedCard.getClient());
        session.getTransaction().commit();
        sessionFactory.close();
    }

    public void remove(int id) {
        setConnect();
        Card cardFromDB = session.get(Card.class, id);
        session.remove(cardFromDB);
        session.getTransaction().commit();
        sessionFactory.close();
    }
}

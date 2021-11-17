package dao;

import model.Card;
import model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClientDao {
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

    public List<Client> getAll() {
        setConnect();
        List<Client> clientList = session.createQuery("SELECT i FROM Client i", Client.class).getResultList();
        session.getTransaction().commit();
        sessionFactory.close();
        return clientList;
    }

    public Client getById(int id) {
        setConnect();
        Client clientFromDB = session.get(Client.class, id);
        session.getTransaction().commit();
        sessionFactory.close();
        return clientFromDB;
    }

    public void createClient(String surname, String name, String patronymic, String birthday, String sex, String email) {
        setConnect();
        Client client = new Client(surname, name, patronymic, birthday, sex, email);
        session.save(client);
        session.getTransaction().commit();
        sessionFactory.close();

    }

    public void update(Client updatedClient, int id) {
        setConnect();
        Client clientFromDB = session.get(Client.class, id);
        clientFromDB.setName(updatedClient.getName());
        clientFromDB.setSurname(updatedClient.getSurname());
        clientFromDB.setPatronymic(updatedClient.getPatronymic());
        clientFromDB.setSex(updatedClient.getSex());
        clientFromDB.setEmail(updatedClient.getEmail());
        session.getTransaction().commit();
        sessionFactory.close();
    }

}

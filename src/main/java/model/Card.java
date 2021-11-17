package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "Card")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cardNumber")
    private CardNumber cardNumber = new CardNumber();

    @Column(name = "issueDate")
    private CardDate issueDate = new CardDate();

    @Column(name = "expirationDate")
    private CardDate expirationDate = new CardDate();

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    public Card() {

    }

    public Card(int year, int month, int day, int[] cardNumber, Client client, boolean autoSetExpDate) {
        setIssueDate(year, month, day, autoSetExpDate);
        setCardNumber(cardNumber);
        setClient(client);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCardNumber(int[] cardNumber) {
        this.cardNumber.setNumber(cardNumber);
    }

    public String getCardNumberString() {
        return this.cardNumber.getNumber()[0] + " "
                + this.cardNumber.getNumber()[1] + " "
                + this.cardNumber.getNumber()[2] + " "
                + this.cardNumber.getNumber()[3];
    }

    public int[] getCardNumber() {
        return this.cardNumber.getNumber();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setIssueDate(int year, int month, int day, boolean autoSetExpDate) {
        this.issueDate.setYear(year);
        this.issueDate.setMonth(month);
        this.issueDate.setDay(day);
        if (autoSetExpDate) {
            Calendar calendar = new GregorianCalendar(this.issueDate.getYear(),
                    this.issueDate.getMonth(),
                    this.issueDate.getDay());
            calendar.add(Calendar.YEAR, +2);
            this.expirationDate.setYear(calendar.get(Calendar.YEAR));
            this.expirationDate.setMonth(calendar.get(Calendar.MONTH));
            this.expirationDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    public Date getIssueDate() {
        Calendar calendar = new GregorianCalendar(this.issueDate.getYear(),
                this.issueDate.getMonth(),
                this.issueDate.getDay());
        return calendar.getTime();
    }

    public void setExpirationDate(int year, int month, int day) {
        this.expirationDate.setYear(year);
        this.expirationDate.setMonth(month);
        this.expirationDate.setDay(day);
    }

    public CardDate getExpirationDate() {
        return this.expirationDate;
    }

    public boolean isTermOver() {
        Calendar calendar = new GregorianCalendar();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (currentYear > this.expirationDate.getYear()) {
            return true;
        }
        if (currentYear == this.expirationDate.getYear() &&
                currentMonth == this.expirationDate.getMonth() &&
                currentDay > this.expirationDate.getDay()) {
            return true;
        } else {
            return false;
        }
    }

    public String cardInfo() {
        return this.id + ". " + getCardNumberString() + " " +
                this.expirationDate.getYear() + "." +
                this.expirationDate.getMonth() + "." +
                this.expirationDate.getDay();
    }
}
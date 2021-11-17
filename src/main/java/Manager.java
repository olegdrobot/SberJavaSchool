import dao.CardDao;
import dao.ClientDao;
import model.Card;
import model.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Manager {

    public boolean menu() {
        int item = 0;
        System.out.println("0 - выход");
        System.out.println("1 - показать всех клиентов");
        System.out.println("2 - добавить клиента");
        System.out.println("3 - показать карты клиента");
        System.out.println("4 - добавить карту");
        System.out.println("5 - аннулировать карту");
        System.out.print("Выберети действие:");
        Scanner scanner = new Scanner(System.in);
        item = scanner.nextInt();
        switch (item) {
            case 0:
                return false;
            case 1:
                showAllClients();
                break;
            case 2:
                addClient();
                break;
            case 3:
                showClientCards();
                break;
            case 4:
                addCard();
                break;
            case 5:
                removeCard();
                break;
        }
        return true;
    }

    private void addClient() {
        String name;
        String surname;
        String patronymic;
        String birthDay;
        String sex;
        String email;
        ClientDao clientDao = new ClientDao();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите фамилию: ");
        surname = scanner.nextLine();
        System.out.print("Введите имя: ");
        name = scanner.nextLine();
        System.out.print("Введите отчество: ");
        patronymic = scanner.nextLine();
        System.out.print("Введите дату рождения цифрами (год.месяц.день): ");
        birthDay = scanner.nextLine();
        System.out.print("Введите пол: ");
        sex = scanner.nextLine();
        System.out.print("Введите email: ");
        email = scanner.nextLine();
        int potentialId = isClientExist(surname, name, patronymic, birthDay, sex, email);
        if (potentialId == -1) {
            clientDao.createClient(surname, name, patronymic, birthDay, sex, email);
        } else {
            System.out.println("Такой клиент уже есть в базе, его id: " + potentialId);
        }


    }

    private void showAllClients() {
        ClientDao clientDao = new ClientDao();
        List<Client> clientList = clientDao.getAll();
        System.out.printf("%-5s %-10s %-10s %-20s %-15s %-3s %-20s",
                "id",
                "Фамилия",
                "Имя",
                "Отчество",
                "Дата рождения",
                "пол",
                "email"
        );
        System.out.println();
        for (int i = 0; i < clientList.size(); i++) {
            Client currentClient = clientList.get(i);
            System.out.printf("%-5d %-10s %-10s %-20s %-15s %-3s %-20s",
                    currentClient.getId(),
                    currentClient.getSurname(),
                    currentClient.getName(),
                    currentClient.getPatronymic(),
                    currentClient.getBirthday(),
                    currentClient.getSex(),
                    currentClient.getEmail()
            );
            System.out.println();
        }
    }

    private void showClientCards() {
        ClientDao clientDao = new ClientDao();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите id клиента: ");
        int id = scanner.nextInt();
        try {
            Client client = clientDao.getById(id);
            System.out.printf("%-5s %-22s %-15s %-15s",
                    "id",
                    "номер карты",
                    "дата выдачи",
                    "срок действия"
            );
            System.out.println();
            List<Card> cards = client.getCards();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < cards.size(); i++) {
                Card currentCard = cards.get(i);
                Calendar calendar = new GregorianCalendar(currentCard.getExpirationDate().getYear(),
                        currentCard.getExpirationDate().getMonth(),
                        currentCard.getExpirationDate().getDay());
                System.out.printf("%-5d %-22s %-15s %-15s",
                        currentCard.getId(),
                        currentCard.getCardNumberString(),
                        formater.format(currentCard.getIssueDate()),
                        formater.format(calendar.getTime())
                );
                System.out.println();
            }
        } catch (NullPointerException e) {
            System.out.println("Клиента с таким id не существует");
        }
    }

    private void addCard() {
        CardDao cardDao = new CardDao();
        ClientDao clientDao = new ClientDao();
        int year;
        int month;
        int day;
        int[] number = new int[4];
        int clientId;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите год выпуска карты: ");
        year = scanner.nextInt();
        System.out.print("Введите месяц выпуска карты: ");
        month = scanner.nextInt();
        System.out.print("Введите день выпуска карты: ");
        day = scanner.nextInt();
        System.out.print("Введите первую цифру номера карты: ");
        number[0] = scanner.nextInt();
        System.out.print("Введите вторую цифру номера карты: ");
        number[1] = scanner.nextInt();
        System.out.print("Введите третью цифру номера карты: ");
        number[2] = scanner.nextInt();
        System.out.print("Введите четвертую цифру номера карты: ");
        number[3] = scanner.nextInt();
        System.out.print("Введите id владельца карты: ");
        clientId = scanner.nextInt();
        Client client = clientDao.getById(clientId);
        cardDao.createCard(year, month, day, number, client, true);

    }

    private int isClientExist(String surname, String name, String patronymic, String birthDay, String sex, String email) {
        ClientDao clientDao = new ClientDao();
        int id = -1;
        List<Client> clientList = clientDao.getAll();
        for (int i = 0; i < clientList.size(); i++) {
            Client currentClient = clientList.get(i);
            if (currentClient.getName().equals(name) &&
                    currentClient.getSurname().equals(surname) &&
                    currentClient.getPatronymic().equals(patronymic) &&
                    currentClient.getBirthday().equals(birthDay) &&
                    currentClient.getSex().equals(sex) &&
                    currentClient.getEmail().equals(email)
            ) id = currentClient.getId();
        }
        return id;
    }

    private void removeCard() {
        CardDao cardDao = new CardDao();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите id карты: ");
        int id = scanner.nextInt();
        cardDao.remove(id);
    }

    public void checkCardDate() {
        Runnable runnable = new Runnable() {
            public void run() {
                ClientDao clientDao = new ClientDao();
                List<Client> clientList = clientDao.getAll();
                for (int i = 0; i < clientList.size(); i++) {
                    for (int j = 0; j < clientList.get(i).getCards().size(); j++) {
                        if (clientList.get(i).getCards().get(j).isTermOver()) {
                            EmailSender sender = new EmailSender("+++your email+++", "+++your password");
                            sender.send("CardManager",
                                    "У Вашей карты " + clientList.get(i).getCards().get(j).getCardNumberString() + "истек срок действия",
                                    "+++your email+++", clientList.get(i).getEmail());
                        }
                        System.out.println(clientList.get(i).getCards().get(j).getCardNumberString());
                    }
                }
            }
        };

        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
    }
}

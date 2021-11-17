import javax.transaction.SystemException;

public class Main {
    public static void main(String[] args) throws SystemException {
        Manager manager = new Manager();
        manager.checkCardDate();
        while (manager.menu());
    }
}

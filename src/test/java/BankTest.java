import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Тест класса Bank")
class BankTest {

    private Bank bank = new Bank();
    private Account account1 = new Account(100_000, "1");
    private Account account2 = new Account(30_000, "2");
    private Account account3 = new Account(40_000, "3");
    private Account account4 = new Account(50_000, "4");
    private HashMap<String, Account> accounts = new HashMap<>();

    @BeforeEach
    void setUp() {
        accounts.put("1", account1);
        accounts.put("2", account2);
        accounts.put("3", account3);
        accounts.put("4", account4);
        bank.setAccounts(accounts);
    }

    @Test
    @DisplayName("Блокировка через метод isFraud()")
    void isFraud() throws InterruptedException {
        long exSum = 220000;

        bank = spy(Bank.class);
        when(bank.isFraud("1", "2", 50_000)).thenReturn(true);
        bank.setAccounts(accounts);
        bank.transfer("1", "2", 50_000);

        assertEquals(0, bank.getBalance("1"));
        assertEquals(0, bank.getBalance("2"));
        assertEquals(exSum, bank.getSumAllAccounts());
    }

    @Test
    @DisplayName("Переводы в многопоточном режиме")
    void transfer() throws InterruptedException {
        int testMoney = 5000;
        long expected1 = 75_000;
        long expected2 = 5_000;
        long expected3 = 90_000;
        long expected4 = 50_000;
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    bank.transfer("1", "3", testMoney);
                    bank.transfer("4", "2", testMoney);
                    bank.transfer("2", "4", testMoney);
                    bank.transfer("2", "3", testMoney);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(100);
        assertEquals(expected1, bank.getBalance("1"));
        assertEquals(expected2, bank.getBalance("2"));
        assertEquals(expected3, bank.getBalance("3"));
        assertEquals(expected4, bank.getBalance("4"));
    }

    @Test
    @DisplayName("Баланс счёта")
    void getBalance() {
        long expected1 = 100_000;
        long expected2 = 30_000;
        assertEquals(expected1, bank.getBalance("1"));
        assertEquals(expected2, bank.getBalance("2"));
    }

    @Test
    @DisplayName("Сумма на всех банковских счетах")
    void getSumAllAccounts() throws InterruptedException {
        long expected = 220_000;
        bank.transfer("1", "2", 1000);
        assertEquals(expected, bank.getSumAllAccounts());
    }

    @Test
    @DisplayName("Создание счетов")
    void createAccounts() {
        int testAmount = 30;
        int expected = 30;
        int actual = bank.createAccounts(testAmount).size();
        assertEquals(expected, actual);
    }
}
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {

    private Map<String, Account> accounts;
    private final Random random = new Random();
    private final long LIMIT = 50_000;

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        if (accounts.size() != 0) {
            Account sender = accounts.get(fromAccountNum);
            Account recipient = accounts.get(toAccountNum);

            if (sender.isCheck() || recipient.isCheck()) {
                return;
            }
            int compare = sender.compareTo(recipient);
            synchronized (compare > 0 ? sender : recipient) {
                synchronized (compare < 0 ? recipient : sender) {
                    if (amount >= LIMIT) {
                        if (isFraud(fromAccountNum, toAccountNum, amount)) {
                            sender.setCheck(true);
                            recipient.setCheck(true);
                            System.err.println("Счета заблокированы!");
                        }
                    }
                    if (amount > 0 && amount <= sender.getMoney()) {
                        sender.setMoney(sender.getMoney() - amount);
                        recipient.setMoney(recipient.getMoney() + amount);
                    }
                }
            }
        }
    }

    public long getBalance(String accountNum) {
        if (accounts.size() != 0) {
            Account account = accounts.get(accountNum);
            if (!account.isCheck()) {
                return account.getMoney();
            }
        }
        return 0;
    }

    public long getSumAllAccounts() {
        long sum = 0;
        if (accounts.size() != 0) {
            for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
                sum = sum + accountEntry.getValue().getMoney();
            }
        }
        return sum;
    }

    public Map<String, Account> createAccounts(int amount) {
        accounts = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            long countMoney = (long) (Math.random() * (100_000 - 15_000) + 15_000);
            Account account = new Account(countMoney, Integer.toString(i));
            accounts.put(Integer.toString(i), account);
        }
        return accounts;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}

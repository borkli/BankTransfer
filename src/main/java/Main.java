import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private final static int ACC_AMOUNT = 50;
    private final static int AMOUNT_THREAD = 4;
    private static ExecutorService service = Executors.newFixedThreadPool(AMOUNT_THREAD);

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.createAccounts(ACC_AMOUNT);

        /*for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    bank.transfer("1", "2", 5000);
                    System.out.println(bank.getBalance("1"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    bank.transfer("2", "1", 5000);
                    System.out.println(bank.getBalance("2"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }*/

        for (int i = 0; i < AMOUNT_THREAD; i++) {
            service.execute(() -> {
                for (int j = 0; j < ACC_AMOUNT - 1; j++) {
                    int amount = (int) (Math.random() * (100_000 + 10_000) + 10_000);
                    try {
                        bank.transfer(Integer.toString(j), Integer.toString(j + 1), amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(bank.getBalance(Integer.toString(j)));
                    System.out.println(bank.getBalance(Integer.toString(j + 1)));
                }
            });
        }
        service.shutdown();
    }
}

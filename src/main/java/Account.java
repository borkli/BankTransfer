public class Account {

    private long money;
    private String accNumber;
    private boolean check = false;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isCheck() {
        return check;
    }

    public synchronized void setCheck(boolean check) {
        this.check = check;
    }

    public int compareTo(Account acc) {
        return this.getAccNumber().compareTo(acc.getAccNumber());
    }
}

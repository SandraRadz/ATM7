package dao;

public interface WriteOffDao {
    int getCount();
    void addWriteOff(int id, double sum, String cardFrom, String cardTo);
}

package dao;

import entity.WriteOff;

public interface WriteOffDao {
    WriteOff get(long id);
    int insert(WriteOff user);
    void update(WriteOff user);
    //void remove(WriteOff woff);
    //boolean ifExists(WriteOff woff);
}

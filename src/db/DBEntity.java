package db;

import java.util.UUID;

public abstract class DBEntity {
    String id;

    public String getId() {return id;}

    public DBEntity() {
        id = UUID.randomUUID().toString();
    }
    public DBEntity(String id) {
        this.id = id;
    }
}

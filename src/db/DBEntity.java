package db;

import java.util.UUID;

public abstract class DBEntity {
    protected String id;

    public String getId(){ return id;}

    protected abstract String toCSV();

    protected DBEntity() {
        this.id = UUID.randomUUID().toString();
    }

}

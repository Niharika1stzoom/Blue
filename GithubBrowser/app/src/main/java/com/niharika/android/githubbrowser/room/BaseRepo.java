package com.niharika.android.githubbrowser.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import java.io.Serializable;

//Repo for database
@Entity(tableName = "repo_table",primaryKeys = {"name","owner"})
public class BaseRepo implements Serializable {
    @NonNull
    String name;
    String description;
    @NonNull
    String owner;

    public BaseRepo(String name, String description, String owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

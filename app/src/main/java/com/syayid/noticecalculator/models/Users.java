package com.syayid.noticecalculator.models;

public class Users {
    private int id, level;
    private String name, password;

    // contrustor(empty)
    public Users() {
    }

    // constructor
    public Users(int id, String name, String password, int level) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
    }

    /*Setter and Getter*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

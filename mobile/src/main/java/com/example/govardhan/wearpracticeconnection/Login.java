package com.example.govardhan.wearpracticeconnection;

import com.example.govardhan.wearpracticeconnection.Database.DAO;

/**
 * Created by Govardhan on 13/5/2017.
 */

public class Login {
    private int id;
    private String Name;
    private String Age;
    private String Gender;

    public Login(int id, String name, String age, String gender) {
        this.id = id;
        Name = name;
        Age = age;
        Gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void addLogin(Login login, DAO loginDAO) {
        loginDAO.AddLoign(login);
    }
}

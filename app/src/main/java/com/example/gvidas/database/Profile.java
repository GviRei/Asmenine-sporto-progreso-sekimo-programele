package com.example.gvidas.database;

public class Profile {
    public int profileID;
    public String name;
    public int age;
    public String gender;
    public int height;
    public int weight;

    public Profile() {
    }

    public Profile(int id, String name, int age, String gender, int height, int weight) {
        this.profileID = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

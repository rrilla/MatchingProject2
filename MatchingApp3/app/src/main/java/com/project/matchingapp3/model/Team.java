package com.project.matchingapp3.model;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private String location;
    private String explaintation; // 팀설명
    private String image;
    private User owner;
    private List<User> users;

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", explaintation='" + explaintation + '\'' +
                ", image='" + image + '\'' +
                ", owner=" + owner +
                ", users=" + users +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplaintation() {
        return explaintation;
    }

    public void setExplaintation(String explaintation) {
        this.explaintation = explaintation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
package com.example.portefeuillealilo.model;



public class User {

    public User(int id, String name, String email, String password, String solde, String depenses, String revenu) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.solde = solde;
        this.revenu = revenu;
        this.depenses = depenses;
    }

    public User() {

    }

    private int id;
    private String name;
    private String email;
    private String password;
    private String solde;
    private String revenu;
    private String depenses;




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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setSolde(String solde) { this.solde = solde; }
    public String getSolde() {
        return solde;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRevenu() {
        return revenu;
    }

    public void setRevenu(String revenu) {
        this.revenu = revenu;
    }


    public String getDepenses() {
        return depenses;
    }

    public void setdepenses(String depenses) {
        this.depenses = depenses;
    }
}

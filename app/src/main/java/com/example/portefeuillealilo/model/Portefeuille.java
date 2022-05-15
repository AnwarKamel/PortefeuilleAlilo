package com.example.portefeuillealilo.model;

public class Portefeuille {
    private int portfi_id;
    private int user_id;
    private String title;
    private String type;
    private String solde;
    private String date;

    public Portefeuille() {

    }
    public Portefeuille(int portfi_id, int user_id, String title, String type, String solde, String date) {
        this.portfi_id = portfi_id;
        this.user_id = user_id;
        this.title = title;
        this.type = type;
        this.solde = solde;
        this.date = date;
    }

    public Portefeuille(int portfi_id, int user_id, String title, String type, String solde) {
        this.portfi_id = portfi_id;
        this.user_id = user_id;
        this.title = title;
        this.type = type;
        this.solde = solde;
    }




    public int getPortfi_id() {
        return portfi_id;
    }

    public void setPortfi_id(int portfi_id) {
        this.portfi_id = portfi_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}

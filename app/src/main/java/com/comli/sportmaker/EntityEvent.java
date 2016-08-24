package com.comli.sportmaker;

/**
 * Created by Simone on 29/06/2016.
 */
public class EntityEvent {
    int cod_event;
    String data;
    String sport;
    String luogo;
    int n_giocatori;
    int id_user;
    double costo;
    double coordinate;

    public EntityEvent(){
    }

    public void setSport(String sport){
        this.sport=sport;
    }

    public void setCod_event(int cod_event) {
        this.cod_event = cod_event;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setN_giocatori(int n_giocatori) {
        this.n_giocatori = n_giocatori;
    }

    public void setCoordinate(double coordinate) {
        this.coordinate = coordinate;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getluogo(){
        return luogo;
    }

    public int getNGiocatori(){
        return n_giocatori;
    }

    public double getCosto(){
        return costo;
    }

    public double getCoordinate(){
        return coordinate;
    }

    public int getIdUser(){
        return id_user;
    }


    public String getSport(){
        return sport;
    }

    public int getCod_event() {
        return cod_event;
    }

    public int getNGiocatoriRimasti(){
        return (n_giocatori); //togliere il numero di giocatori iscritti
    }

    public String getData() {
        return data;
    }
}

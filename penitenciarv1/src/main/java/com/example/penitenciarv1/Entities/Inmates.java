package com.example.penitenciarv1.Entities;

import eu.hansolo.toolbox.time.DateTimes;

public class Inmates {
    private String name;
    private int id;
    private String sentenceRemained;
    private String profession;
    private String idCelula;

    public Inmates(String name, int id, String sentenceRemained, String profession) {

        this.name = name;
        this.id = id;

    }

    public Inmates() {
        this.name = "";
        this.id = 0;
        this.sentenceRemained = null;
        this.profession = "Unknown";
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentenceRemained() {
        return sentenceRemained;
    }

    public void setSentenceRemained(String sentenceRemained) {
        this.sentenceRemained = sentenceRemained;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setIdCelula(String idCelula) {
        this.idCelula = idCelula;
    }

    public String getIdCelula() {
        return idCelula;
    }
}

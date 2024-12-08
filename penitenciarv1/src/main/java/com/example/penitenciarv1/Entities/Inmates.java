package com.example.penitenciarv1.Entities;

import eu.hansolo.toolbox.time.DateTimes;

public class Inmates {
    public String name;
    public int age;
    DateTimes start_date;

    public Inmates(String name, int age) {

        this.name = name;
        this.age = age;
    }
}

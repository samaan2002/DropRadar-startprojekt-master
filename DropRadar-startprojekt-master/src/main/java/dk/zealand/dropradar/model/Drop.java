package dk.zealand.dropradar.model;

import java.time.LocalDate;

public class Drop {
    private final Integer id;
    private final String name;
    private final String category;
    private final LocalDate date;

    // Bruges, når repository læser et drop fra MySQL og oprette et Drop objekt
    public Drop(Integer id, String name, String category, LocalDate date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
    }

    // Bruges i service, før et nyt drop gemmes i MySQL.
    public Drop(String name, String category, LocalDate date) {
        this(null, name, category, date);
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
    }

    public LocalDate date() {
        return date;
    }

    // Bruges, når vi skriver System.out.println(drop).
    @Override
    public String toString() {
        return "Drop[id=" + id + ", name=" + name
                + ", category=" + category + ", date=" + date + "]";
    }
}
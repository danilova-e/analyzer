package ru.analyzer.model;

public class Subject {

    private final int id;
    private final String name;

    public Subject(String name) {
        id = -1;
        this.name = name;
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}

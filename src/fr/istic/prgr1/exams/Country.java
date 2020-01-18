package fr.istic.prgr1.exams;

public class Country implements Comparable<String> {

    public String name;
    public int surface;
    public int total;

    public Country(String name, int surface, int total) {
        this.name = name;
        this.surface = surface;
        this.total = total;
    }

    @Override
    public int compareTo(String o) {
        return this.name.compareTo(o);
    }

    @Override
    public String toString() {
        return "name "  + name + " superficie " + surface + " total " + total;
    }
}

package fr.event.commercant;

public class Client {
    private int id;
    private String name;
    private String firstName;
    private int points;

    public Client(int id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.points = 0;
    }

    public Client(int id, String firstName, String name, int points) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.points = points;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

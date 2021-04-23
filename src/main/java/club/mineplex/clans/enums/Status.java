package club.mineplex.clans.enums;

public enum Status {

    ENABLED ("Enabled"),
    DISABLED ("Disabled"),
    PAUSED ("Paused");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}

package cs3500.model;

public enum Player {
    RED,
    BLUE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

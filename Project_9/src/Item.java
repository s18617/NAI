public class Item {
    private final double value;
    private final double weight;

    public Item(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }
}

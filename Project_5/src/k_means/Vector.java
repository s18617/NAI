package k_means;

public class Vector {
    private final double[] vector;
    private final int length;

    public Vector(double[] vector) {
        this.vector = vector;
        this.length = vector.length;
    }

    public double[] getVector() {
        return vector;
    }

    public int getLength() {
        return length;
    }
}

package k_means;

import java.util.Arrays;

public class Vector {
    private final double[] vector;
    private final int length;

    public Vector(double[] vector) {
        this.vector = vector;
        this.length = vector.length;
    }

    // Getters
    public double[] getVector() {
        return vector;
    }

    public int getLength() {
        return length;
    }

    // Methods

    /**
     * Returns squared distance between vectors
     *
     * @param vector vector to which squared distance will be found
     * @return squared distance between vector on which method was called on, and vector passed as an argument
     */
    public double getSquaredDistanceTo(Vector vector) {
        if (this.getLength() != vector.getLength()) {
            System.err.println("> Vectors " + vector + " and " + this + " have different number of dimensions");
            return -1;
        }

        double distance = 0;

        for (int i = 0; i < vector.getLength(); i++) {
            distance += Math.pow(this.getVector()[i] - vector.getVector()[i], 2);
        }

        return distance;
    }

    @Override
    public String toString() {
        return "vector=" + Arrays.toString(vector);
    }
}

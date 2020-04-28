package k_means;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class KMeans {
    public static ArrayList<ArrayList<Vector>> group(int k, List<Vector> vectors) {
        ArrayList<ArrayList<Vector>> groups = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            groups.add(new ArrayList<>());
        }

        // randomly assign to groups all the vectors
        for (Vector vector : vectors) {
            int i = ThreadLocalRandom.current().nextInt(0, k);
            groups.get(i).add(vector);
        }

        // k-means
        ArrayList<Vector> centroids;
        boolean changed;
        do {
            centroids = calcCentroids(groups);
            changed = false;
            for (int i = 0; i < k; i++) { // i - current group classification
                List<Vector> toRemove = new ArrayList<>();

                for (Vector v : groups.get(i)) {
                    int minDistanceIndex = 0; // - 'correct' group classification
                    double minDistance = centroids.get(0).getSquaredDistanceTo(v);
                    for (int j = 1; j < k; j++) {
                        double newDistance = centroids.get(j).getSquaredDistanceTo(v);
                        if (newDistance < minDistance) {
                            minDistance = newDistance;
                            minDistanceIndex = j;
                        }
                    }
                    if (minDistanceIndex != i) {
                        changed = true;
                        //groups.get(i).remove(v);
                        toRemove.add(v);
                        groups.get(minDistanceIndex).add(v);
                    }
                }

                groups.get(i).removeAll(toRemove);
            }
            // console
            printGroups(groups);
        } while (changed);

        return groups;
    }

    private static Vector calcCentroid(ArrayList<Vector> group) {
        int dimensions = group.get(0).getLength();
        double[] centroid = new double[dimensions];

        for (Vector v : group) {
            for (int i = 0; i < dimensions; i++) {
                centroid[i] += v.getVector()[i];
            }
        }
        for (int i = 0; i < dimensions; i++) {
            centroid[i] /= group.size();
        }

        return new Vector(centroid);
    }

    private static ArrayList<Vector> calcCentroids(ArrayList<ArrayList<Vector>> groups) {
        ArrayList<Vector> centroids = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            centroids.add(i, calcCentroid(groups.get(i)));
        }

        return centroids;
    }

    private static void printGroups(ArrayList<ArrayList<Vector>> groups) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            sb.append("Group_").append(i).append("_size=").append(groups.get(i).size()).append("; ");
            double sum = 0;
            for (int j = 0; j < groups.get(i).size() - 1; j++) {
                for (int l = j + 1; l < groups.get(i).size(); l++) {
                    sum += groups.get(i).get(j).getSquaredDistanceTo(groups.get(i).get(l));
                }
            }
            sb.append("Sum=").append(Math.round(sum)).append(";\t\t");
        }
        System.out.println(sb);
    }
}

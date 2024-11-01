package io.enderdev.patchoulibooks.util;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {
    private MathUtil() {
    }

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public static List<Point> arrangeOnCircle(int numObjects, double radius, double centerX, double centerY) {
        List<Point> points = new ArrayList<>();

        // Calculate the angle between each object in radians
        double angleIncrement = 2 * Math.PI / numObjects;

        for (int i = 0; i < numObjects; i++) {
            double angle = i * angleIncrement;

            // Calculate x and y coordinates
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            points.add(new Point(x, y));
        }

        return points;
    }
}

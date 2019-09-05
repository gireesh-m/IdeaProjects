package internshipStuff;

import java.util.ArrayList;

/**
 * Created by Gireesh on 6/5/2018.
 */
public class CGS {

    /**
     * Run an extensive test case given a list of line segments, a point,
     * an expected distance and point, and what the test case is testing
     * @param lines an arraylist of Line objects describing all line segments being tested
     * @param point the point to find the short distance to
     * @param expectedDistance a double describing the shortest expected distance from
     *                         point to a line segment
     * @param testing a string detailing what the test case is trying to test
     * @param expectedPoint the expected point on the line segments that is closest to "point"
     */
    public static void test(ArrayList<Line> lines, Point point, double expectedDistance, String testing, Point expectedPoint) {
        System.out.printf("This test case is testing %s\n", testing);
        // set the minimum distance to the maximum possible value
        double min = Double.POSITIVE_INFINITY;
        System.out.printf("Testing %s\n", point.toString());
        System.out.println("Testing Line(s):");
        Line bestLine = null;
        for (Line line : lines) {
            System.out.println(line.toString());
            // for any distance that is less than the current min
            // update current min accordingly
            double distance = line.distanceFromPoint(point);
            if (distance < min) {
                min = distance;
                bestLine = line;
            }
        }
        // based on the shortest distance line, find the actual point
        Point result = bestLine.closestPointOnLine(point);
        System.out.printf("Expected point: %s\nResult point: %s\n", expectedPoint, result);
        System.out.printf("Expected distance: %.5f \nResult distance: %.5f\n", expectedDistance, min);
        // compare points and rounded distances to check if test passed or not
        if (doubleEquals(min, expectedDistance) && result.equals(expectedPoint)) {
            System.out.println("---------------------Test case passed---------------------");
        } else {
            System.out.println("---------------------Test case failed---------------------");
        }
        System.out.println();
    }

    // check if doubles are equal to 3 decimal places
    public static boolean doubleEquals(double a, double b) {
        return Math.round(a*100.000)/100.000 == Math.round(b*100.000)/100.000;
    }

    private static class Point {
        private double x;
        private double y;
        private double z;
        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        // subtract line segment (treated as a unit vector) from a point multiplied by a coefficient
        public Point subtract(Line line, double coefficient) {
            double[] subtract = line.component();
            double newX = this.x - subtract[0]*coefficient/line.magnitude();
            double newY = this.y - subtract[1]*coefficient/line.magnitude();
            double newZ = this.z - subtract[2]*coefficient/line.magnitude();
            return new Point(newX, newY, newZ);
        }
        // subtract line segment (treated as a unit vector) from a point multiplied by a coefficient
        public Point add(Line line, double coefficient) {
            return subtract(line, -1*coefficient);
        }
        // convert point to string form
        public String toString() {
            return String.format("Point (%f,%f,%f)",x,y,z);
        }
        // check if this point equals another point
        public boolean equals(Point that) {
            return (doubleEquals(this.x, that.x) && doubleEquals(this.y, that.y) && doubleEquals(this.z, that.z));
        }
    }


    private static class Line {

        private double x1;
        private double x2;
        private double y1;
        private double y2;
        private double z1;
        private double z2;
        // this is the main constructor, because the computations come easier if the points are
        // all stored as individual doubles
        public Line(double x1, double x2, double y1, double y2, double z1, double z2) {
            this.x2 = x2;
            this.x1 = x1;
            this.y2 = y2;
            this.y1 = y1;
            this.z2 = z2;
            this.z1 = z1;
        }
        // simpler constructor for using two points
        public Line(Point point1, Point point2) {
            this(point1.x, point2.x, point1.y, point2.y, point1.z, point2.z);
        }
        // find the closest point on a line to a given different point
        public Point closestPointOnLine(Point point) {
            // line from start of line to point
            Line lineStartToPoint = new Line(x1,point.x,y1,point.y,z1, point.z);
            // line from end of line to point
            Line lineEndToPoint = new Line(x2,point.x,y2,point.y,z2, point.z);
            double dotProductOfLineStartToPoint = (this.dotProduct(lineStartToPoint))/this.magnitude();
            // deal with 0 magnitude by treating it as a point
            // line from end to line to point
            if (this.magnitude() == 0) {
                return point.subtract(lineStartToPoint, lineStartToPoint.magnitude());
            } // check if point is outside of line on left side
            else if (dotProductOfLineStartToPoint <= 0) {
                return point.subtract(lineStartToPoint, lineStartToPoint.magnitude());
            } // check if point is outside of line on right side
            else if (this.magnitude() <= dotProductOfLineStartToPoint) {
                // find distance from end of line to point
                return point.subtract(lineEndToPoint, lineEndToPoint.magnitude());
            } // check if point is on line
            else if (lineStartToPoint.magnitude() + lineEndToPoint.magnitude() == this.magnitude()) {
                return point;
            }
            // it is over line, thus take starting point of line segment
            // and add the component to go over to get to the point (component of lineStarToPoint
            // in the direction of the line segment)
            else {
                point = new Point(this.x1, this.y1, this.z1);
                return point.add(this, this.dotProduct(lineStartToPoint)/this.magnitude());
            }
        }
        public double distanceFromPoint(Point point) {
            // line from start of line to point
            Line lineStartToPoint = new Line(x1,point.x,y1,point.y,z1, point.z);
            // deal with 0 magnitude by treating it as a point
            if (this.magnitude() == 0) {
                return lineStartToPoint.magnitude();
            }
            // line from end to line to point
            Line lineEndToPoint = new Line(x2,point.x,y2,point.y,z2, point.z);
            // check if point is outside of line on left side
            double dotProductOfLineStartToPoint = (this.dotProduct(lineStartToPoint))/this.magnitude();
            if (dotProductOfLineStartToPoint <= 0) {
                return lineStartToPoint.magnitude();
            } // check if point is outside of line on right side
            else if (this.magnitude() <= dotProductOfLineStartToPoint) {
                // find distance from end of line to point
                return lineEndToPoint.magnitude();
            } // check if point is on line
            else if (lineStartToPoint.magnitude() + lineEndToPoint.magnitude() == this.magnitude()) {
                return 0;
            } // find cross product between line1, and line to the point. Divide by magnitude of line1
            else {
                return crossProduct(lineStartToPoint)/this.magnitude();
            }

        }
        // cross product this line with another line
        private double crossProduct(Line line2) {
            // find cross product by using |a x b| = |a||b|sin(theta)
            return (this.magnitude()*line2.magnitude()*Math.sin(this.angleBetween(line2)));
        }
        private double dotProduct(Line line2) {
            double[] line1Component = this.component();
            double[] line2Component = line2.component();
            // get the dot product
            double product = 0;
            for (int i = 0; i < line1Component.length; i++) {
                product += line1Component[i]*line2Component[i];
            }
            return product;
        }
        // find the angle between two vectors
        private double angleBetween(Line line2) {
            // divide dot product by product of magnitudes of lines
            // deal with 0 magnitude
            if (this.magnitude() == 0 || line2.magnitude() == 0) {
                return 0;
            } else {
                double angle = this.dotProduct(line2) / (this.magnitude() * line2.magnitude());
                // take inverse cosine to find angle
                return Math.acos(angle);
            }
        }
        // get the component form of the line as a integer list
        private double[] component() {
            return (new double[] {this.x2-this.x1, this.y2-this.y1, this.z2-this.z1});
        }
        private double magnitude() {
            double mag = 0;
            double[] comp = this.component();
            for (int i = 0; i < comp.length; i++) {
                mag += comp[i]*comp[i];
            }
            return Math.sqrt(mag);
        }
        @Override
        public String toString() {
            return String.format("Line Starting at (%f,%f,%f), and ending at (%f,%f,%f)",x1,y1,z1,x2,y2,z2);
        }
    }


    public static void main(String[] args) {
        // list of line segments
        ArrayList<Line> lineList = new ArrayList<>();
        // expected answer
        double expectedValue = 0;
        Point point = new Point(1,1,1);
        // test case where point is on line
        lineList.add(new Line(new Point(0,0,0), new Point(2,2,2)));
        test(lineList, point, expectedValue, "whether the program can calculate the distance when the point is on the line", new Point(1,1,1));
        lineList.clear();
        // test case where point is outside of line segment on left
        lineList.add(new Line(new Point(2,2,2), new Point(3,3,3)));
        expectedValue = 1.73205;
        test(lineList, point, expectedValue, "whether the program can calculate the distance when the point is beyond the line segment, and when it would be on the line segment if the segment were extended", new Point(2,2,2));
        lineList.clear();
        // test case where point is outside of line segment on right
        lineList.add(new Line(new Point(0,0,0), new Point(0.5,0.5,0.5)));
        expectedValue = 0.86603;
        test(lineList, point, expectedValue, "whether the program can calculate the distance when the point is beyond the line segment on the right side",new Point(0.5,0.5,0.5));
        lineList.clear();
        // test case where line has magnitude 0
        lineList.add(new Line(new Point(0,0,0), new Point(0,0,0)));
        expectedValue = 1.73205;
        test(lineList, point, expectedValue, "whether the program can calculate the distance when the the line segment has length 0, and therefore is just a point",new Point(0,0,0));
        lineList.clear();
        // point over line
        lineList.add(new Line(new Point(0,0,0), new Point(2,2,0)));
        expectedValue = 1.00000;
        test(lineList, point, expectedValue, "whether the program can calculate the distance when the point is located perpendicular to the line segment (ie a generic case)",new Point(1,1,0));
        lineList.clear();
        // test case with multiple lines (none touching point)
        expectedValue = 0.47140;
        lineList.add(new Line(new Point(0,0,0), new Point(2,2,0)));
        lineList.add(new Line(new Point(4,3,2), new Point(8,9,3)));
        lineList.add(new Line(new Point(2,2,2), new Point(1,0,0)));
        lineList.add(new Line(new Point(0,0,0), new Point(1,1,3)));
        test(lineList, point, expectedValue, "whether the program can compare multiple lines that pass by it, and find the shortest distance",new Point(1.444444,0.888889,0.888889));
    }
}

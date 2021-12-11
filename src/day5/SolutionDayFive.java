package day5;

import utils.Utilities;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDayFive {
    public static void main(String[] args) {
        try (Stream<String> a = Utilities.readInFile("src/day5/input");) {

            List<Object> k = a.map(x -> new Line(
                                    new Point(
                                            Integer.parseInt(x.split(" -> ")[0].split(",")[0]),
                                            Integer.parseInt(x.split(" -> ")[0].split(",")[1]))
                                    ,
                                    new Point(
                                            Integer.parseInt(x.split(" -> ")[1].split(",")[0]),
                                            Integer.parseInt(x.split(" -> ")[1].split(",")[1]))
                            )
                    ).filter(q -> Objects.equals(q.p1().x(), q.p2().x()) || Objects.equals(q.p1().y(), q.p2().y()))
                    .mapMulti((x, consumer) -> {
                        System.out.println(x);
                        getPointsOnLine(x).forEach(consumer);
                    }).toList();
            System.out.println(
                    k.stream().map(x -> new Object[] {Collections.frequency(k, x), x}).filter(u -> (int) u[0] > 1)
                            .map(x -> {
                                System.out.println(Arrays.deepToString(x));
                                return x;
                            })
                            .map(u -> u[1]).unordered().distinct().count());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Point> getPointsOnLine(Line l) {
        final List<Point> list = new ArrayList<>();
        Stream.of(l).filter(x -> x.p2().x() == x.p1().x()).forEach(x -> {
            IntStream.range(Math.min(x.p1().y(), x.p2().y()), Math.max(x.p1().y(), x.p2().y()) + 1).forEach(k -> {
                list.add(new Point(x.p1().x(), k));
            });
        });
        Stream.of(l).filter(x -> x.p2().y() == x.p1().y()).forEach(x -> {
            IntStream.range(Math.min(x.p1().x(), x.p2().x()), Math.max(x.p1().x(), x.p2().x()) + 1).forEach(k -> {
                list.add(new Point(k, x.p1().y()));
            });
        });
        return list;
    }
}


record Point(Integer x, Integer y) {
}

record Line(Point p1, Point p2) {
}
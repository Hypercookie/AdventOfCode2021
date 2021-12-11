package day2;

import utils.Utilities;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SolutionDayTwo {

    public static void main(String[] args) {
        //Part One
        try (Stream<String> a = Utilities.readInFile("src/day2/input");) {

            AtomicInteger f = new AtomicInteger();
            AtomicInteger h = new AtomicInteger();

            a.forEach(d -> {
                String c = d.split(" ")[0];
                int delta = Integer.parseInt(d.split(" ")[1]);
                switch (c) {
                    case "forward" -> f.getAndAdd(delta);
                    case "down" -> h.getAndAdd(delta);
                    case "up" -> h.getAndAdd(-delta);
                }
            });
            System.out.println(f.get() * h.get());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Part Two
        try (Stream<String> a = Utilities.readInFile("src/day2/input");) {

            AtomicInteger f = new AtomicInteger();
            AtomicInteger h = new AtomicInteger();
            AtomicInteger aim = new AtomicInteger();
            a.forEach(d -> {
                String c = d.split(" ")[0];
                int delta = Integer.parseInt(d.split(" ")[1]);
                switch (c) {
                    case "forward" -> {
                        h.getAndAdd(delta);
                        f.getAndAdd(aim.get() * delta);
                    }
                    case "down" -> aim.getAndAdd(delta);
                    case "up" -> aim.getAndAdd(-delta);
                }
            });
            System.out.println(f.get() * h.get());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
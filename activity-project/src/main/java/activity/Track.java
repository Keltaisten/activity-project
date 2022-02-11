package activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Track {
    List<TrackPoint> trackPoints = new ArrayList<>();

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    public double getFullElevation() {
//        double min = trackPoints.get(0).getElevation();
//        double sum = 0;
////        trackPoints.stream().mapToDouble(TrackPoint::getElevation).map(e->)
//        return trackPoints.stream().map(e->e.getElevation() >= min ? (min - e.getElevation()) : 0)
//                .mapToDouble(e->e)
//                .sum();

        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i + 1).getElevation() - trackPoints.get(i).getElevation())
                .filter(n -> n > 0)
                .sum();

//        return trackPoints.stream()
//                .map(TrackPoint::getElevation)
//                .map(new Function<Integer, Optional<Integer>>() {
//                    Optional<Integer> previousValue = Optional.empty();
//
//                    @Override
//                    public Optional<Integer> apply(Integer current) {
//                        Optional<Integer> value = previousValue.map(previous -> current - previous);
//                        previousValue = Optional.of(current);
//                        return value;
//                    }
//                })
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(n -> n > 0)
//                .mapToInt(i -> i)
//                .sum();
    }

    public double getFullDecrease() {
//        return trackPoints.stream()
//                .map(TrackPoint::getElevation)
//                .map(new Function<Integer, Optional<Integer>>() {
//                    Optional<Integer> previousValue = Optional.empty();
//
//                    @Override
//                    public Optional<Integer> apply(Integer current) {
//                        Optional<Integer> value = previousValue.map(previous -> previous - current);
//                        previousValue = Optional.of(current);
//                        return value;
//                    }
//                })
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(n -> n > 0)
//                .mapToInt(i -> i)
//                .sum();

        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i).getElevation() - trackPoints.get(i + 1).getElevation())
                .filter(e -> e > 0)
                .sum();
    }

    public double getDistance() {
//        trackPoints.stream()
//                .mapToDouble((e1, e2)->e2.getDistanceFrom(e1));
//        DoubleStream differences = DoubleStream.
//        IntStream differences = IntStream.range(0, trackPoints.size() - 1)
//                .map(i-> (int) trackPoints.get(i+1).getDistanceFrom(trackPoints.get(i)));
//        return differences;


        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i + 1).getDistanceFrom(trackPoints.get(i)))
                .sum();
    }

    public Coordinate findMinimumCoordinate() {
//        trackPoints.stream()
//                .map(TrackPoint::getCoordinate)
//                .min()
        return new Coordinate(trackPoints.stream()
                .map(TrackPoint::getCoordinate)
                .mapToDouble(Coordinate::getLatitude)
                .min().getAsDouble(),
                trackPoints.stream()
                        .map(TrackPoint::getCoordinate)
                        .mapToDouble(Coordinate::getLongitude)
                        .min().getAsDouble());
    }

    public Coordinate findMaximumCoordinate() {
        return new Coordinate(trackPoints.stream()
                .map(TrackPoint::getCoordinate)
                .mapToDouble(Coordinate::getLatitude)
                .max().getAsDouble(),
                trackPoints.stream()
                        .map(TrackPoint::getCoordinate)
                        .mapToDouble(Coordinate::getLongitude)
                        .max().getAsDouble());
    }

    public double getRectangleArea() {
        return (findMaximumCoordinate().getLatitude() - findMinimumCoordinate().getLatitude())
                * (findMaximumCoordinate().getLongitude() - findMinimumCoordinate().getLongitude());
    }

    public void loadFromGpx(InputStream is) {
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("<trkpt")) {
                    Coordinate coord = new Coordinate(Double.parseDouble(line.substring(12,22)),Double.parseDouble(line.substring(29,39)));
                    double ele = Double.parseDouble(scanner.nextLine().trim().substring(5,10));
                    trackPoints.add(new TrackPoint(coord,ele));
                }
            }
        }
    }

    public static void main(String[] args) {
//        Stream.of(1, 2, 3, 4, 6, 7, 8)
//                .map(new Function<Integer, Optional<Integer>>() {
//                    Optional<Integer> previousValue = Optional.empty();
//                    @Override
//                    public Optional<Integer> apply(Integer current) {
//                        Optional<Integer> value = previousValue.map(previous -> current - previous);
//                        previousValue = Optional.of(current);
//                        return value;
//                    }
//                })
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .forEach(System.out::println);

//        Stream.of(123, 124, 0, 200)
//                .map(new Function<Integer, Optional<Integer>>() {
//                    Optional<Integer> previousValue = Optional.empty();
//
//                    @Override
//                    public Optional<Integer> apply(Integer current) {
//                        Optional<Integer> value = previousValue.map(previous -> current - previous);
//                        previousValue = Optional.of(current);
//                        return value;
//                    }
//                })
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(n -> n > 0)
//                .mapToDouble(d -> d)
//                .sum();
////                .forEach(System.out::println);

        int s[] = {1, 2, 3, 4, 6, 7, 8};
        IntStream differences =
                IntStream.range(0, s.length - 1)
                        .map(i -> s[i + 1] - s[i]);
        System.out.println(Arrays.toString(differences.toArray()));
    }
}

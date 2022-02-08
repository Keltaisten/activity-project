package activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        return trackPoints.stream()
                .map(TrackPoint::getElevation)
                .map(new Function<Integer, Optional<Integer>>() {
                    Optional<Integer> previousValue = Optional.empty();

                    @Override
                    public Optional<Integer> apply(Integer current) {
                        Optional<Integer> value = previousValue.map(previous -> current - previous);
                        previousValue = Optional.of(current);
                        return value;
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(n -> n > 0)
                .mapToInt(i -> i)
                .sum();
    }

    public int getFullDecrease() {
        return trackPoints.stream()
                .map(TrackPoint::getElevation)
                .map(new Function<Integer, Optional<Integer>>() {
                    Optional<Integer> previousValue = Optional.empty();

                    @Override
                    public Optional<Integer> apply(Integer current) {
                        Optional<Integer> value = previousValue.map(previous -> previous - current);
                        previousValue = Optional.of(current);
                        return value;
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(n -> n > 0)
                .mapToInt(i -> i)
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

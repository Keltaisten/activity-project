package activity;

import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

public class Track {
    List<TrackPoint> trackPoints = new ArrayList<>();

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    public double getFullElevation() {
        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i + 1).getElevation() - trackPoints.get(i).getElevation())
                .filter(n -> n > 0)
                .sum();
    }

    public double getFullDecrease() {
        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i).getElevation() - trackPoints.get(i + 1).getElevation())
                .filter(e -> e > 0)
                .sum();
    }

    public double getDistance() {
        return IntStream.range(0, trackPoints.size() - 1)
                .mapToDouble(i -> trackPoints.get(i + 1).getDistanceFrom(trackPoints.get(i)))
                .sum();
    }

    public Coordinate findMinimumCoordinate() {
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
}

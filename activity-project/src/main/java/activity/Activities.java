package activity;

import jdk.javadoc.doclet.Reporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activities {
    private List<Activity> activities = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public int numberOfTrackActivities() {
        return (int) activities.stream()
                .filter(a -> a.getDistance() > 0)
                .count();
    }

    public int numberOfWithoutTrackActivities() {
        return (int) activities.stream()
                .filter(a -> a.getDistance() == 0)
                .count();
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<Report> distancesByTypes() {
        return Arrays.stream(ActivityType.values())
                .map(e->new Report(activities.stream()
                        .filter(a->a.getType().equals(e))
                        .mapToDouble(Activity::getDistance)
                        .sum(),e)).toList();
    }
}

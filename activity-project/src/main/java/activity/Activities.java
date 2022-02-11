package activity;

import java.util.Arrays;
import java.util.List;

public class Activities {
    private List<Activity> activities;

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public int numberOfTrackActivities() {
        return (int) activities.stream()
                .filter(ActivityWithTrack.class::isInstance)
                .count();
    }

    public int numberOfWithoutTrackActivities() {
        return (int) activities.stream()
                .filter(ActivityWithoutTrack.class::isInstance)
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

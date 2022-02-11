package activity;

public class Report {
    private double distance;
    private ActivityType activityType;

    public Report(double distance, ActivityType activityType) {
        this.distance = distance;
        this.activityType = activityType;
    }

    public double getDistance() {
        return distance;
    }

    public ActivityType getActivityType() {
        return activityType;
    }
}

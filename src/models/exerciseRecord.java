package models;

public class exerciseRecord {
    private int memberNum;
    private String exerciseName;
    private double exerciseWeight;

    public exerciseRecord(int memberNum, String exerciseName, double exerciseWeight) {
        this.memberNum = memberNum;
        this.exerciseName = exerciseName;
        this.exerciseWeight = exerciseWeight;
    }

    // getters and setters

    public int getMemberNum() {
        return memberNum;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public double getExerciseWeight() {
        return exerciseWeight;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseWeight(double exerciseWeight) {
        this.exerciseWeight = exerciseWeight;
    }
}
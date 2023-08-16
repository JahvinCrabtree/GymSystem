package models;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class memberData {
    
    private Integer memberNum;
    private Double memberWeight;
    private Double exerciseWeight;
    private Integer repetitions;
    private Integer sets;
    private String exerciseName;
    private Date date;

    public memberData(Integer memberNum, String exerciseName, Double exerciseWeight) {
        this.memberNum = memberNum;
        this.exerciseName = exerciseName;
        this.exerciseWeight = exerciseWeight;
    }

        public memberData(String exerciseName, Double exerciseWeight, Integer repetitions, String dateString, Double memberWeight) {
        this.exerciseName = exerciseName;
        this.exerciseWeight = exerciseWeight;
        this.repetitions = repetitions;
        try {
            this.date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = null;  // Handle this situation appropriately
        }
        this.memberWeight = memberWeight;
    }

    // Getters
    public Integer getMemberNum() {
        return memberNum;
    }

    public Double getMemberWeight() {
        return memberWeight;
    }

    public Double getExerciseWeight() {
        return exerciseWeight;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public Integer getSets() {
        return sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public Date getDate() {
        return date;
    }

    // Setters
    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public void setMemberWeight(Double memberWeight) {
        this.memberWeight = memberWeight;
    }

    public void setExerciseWeight(Double exerciseWeight) {
        this.exerciseWeight = exerciseWeight;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

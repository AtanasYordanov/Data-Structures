public class Submission implements Comparable<Submission> {
    private int id;
    private int userId;
    private int contestId;
    private int points;
    private SubmissionType type;

    public Submission(int id, int points, SubmissionType type, int contestId, int userId) {
        this.id = id;
        this.userId = userId;
        this.contestId = contestId;
        this.points = points;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public SubmissionType getType() {
        return this.type;
    }

    public void setType(SubmissionType type) {
        this.type = type;
    }

    public int getContestId() {
        return this.contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int compareTo(Submission o) {
        return Integer.compare(this.id, o.id);
    }
}

package cs1302.api;

public class Restaurant {
    private String alias;
    private String name;
    private String review;
    private String score_tag;
    private int confidence;
/*
    public Restaurant() {
        this.confidence = 0;
    }
*/
    public void setAlias(String newAlias) {
        this.alias = newAlias;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setReview(String newReview) {
        this.review = newReview;
    }

    public void setScoreTag(String newScoreTag) {
        this.score_tag = newScoreTag;
    }

    public void setConfidence(int  newConfidence) {
        this.confidence = newConfidence;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getName() {
        return this.name;
    }

    public String getReview() {
        return this.review;
    }

    public String getScoreTag() {
        return this.score_tag;
    }

    public int getConfidence() {
        return this.confidence;
    }
}

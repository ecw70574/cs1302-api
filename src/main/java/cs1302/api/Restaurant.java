package cs1302.api;


/**
 * Objects of type Restaurant store information about each
 * restaurant that meets filter conditions.
 */
public class Restaurant {
    private String alias;
    private String name;
    private String review;
    private String scoreTag;
    private int confidence;
    private String agreement;
    public Sentence[] sentenceList;
    private int numSentences;

    /**
     * Creates objects of type Restaurant store information about each
     * restaurant that meets filter conditions.
     */
    public Restaurant() {
        this.sentenceList = new Sentence[numSentences];
    }

    /**
     * Sets a value for number of sentences in review.
     * @param newNum int newNum
     */
    public void setNumSentences(int newNum) {
        this.numSentences = newNum;
    }

    /**
     * Sets a value for Yelp alias.
     * @param newAlias String newAlias
     */
    public void setAlias(String newAlias) {
        this.alias = newAlias;
    }

    /**
     * Sets a value for restaurant name.
     * @param newName String newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Sets a value for restaurant's review text.
     * @param newReview String newReview
     */
    public void setReview(String newReview) {
        this.review = newReview;
    }

    /**
     * Sets a value for sentiment of review.
     * @param newScoreTag String newScoreTag
     */
    public void setScoreTag(String newScoreTag) {
        this.scoreTag = newScoreTag;
    }

    /**
     * Sets a value for confidence level of review.
     * @param newConfidence int newConfidence
     */
    public void setConfidence(int newConfidence) {
        this.confidence = newConfidence;
    }

    /**
     * Sets a value for agreement level of review.
     * @param newAgreement String newAgreement
     */
    public void setAgreement(String newAgreement) {
        this.agreement = newAgreement;
    }

    /**
     * Returns alias of restaurant.
     * @return alias String alias
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Returns name of restaurant.
     * @return name String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns review of restaurant.
     * @return review String review
     */
    public String getReview() {
        return this.review;
    }

    /**
     * Returns sentiment of review.
     * @return sentiment String sentiment
     */
    public String getScoreTag() {
        return this.scoreTag;
    }

    /**
     * Returns confidence level of review.
     * @return confidence String confidence
     */
    public int getConfidence() {
        return this.confidence;
    }

    /**
     * Returns agreement level of review.
     * @return agreement String agreement
     */
    public String getAgreement() {
        return this.agreement;
    }
}

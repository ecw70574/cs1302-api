package cs1302.api;

/**
 * Stores response from sentiment analysis to parse overall sentiment, confidence,
 * agreement level, and sentences tagged for certian sentiment.
 */
public class Request3Response {
    String score_tag;
    String confidence;
    String agreement;
    Sentence[] sentence_list;
}

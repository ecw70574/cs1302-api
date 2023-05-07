package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URLEncoder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import java.io.IOException;
import java.lang.String;
import java.lang.Integer;
import static java.lang.Math.*;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.time.LocalTime;
import javafx.util.Duration;
//import java.util.LinkedList;
//import esapi.Encoder;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {

    public static final String YELP_API_KEY = "vyu1ZJbI_zzzAlvwTsAsQVdHNYwH1l-ht8Xt0ofxWsAfKN"
        +"OeVeNK8FwihDNbEJ2MNQqqcx9MWf9yFtfaGYFaelxISYVMaVcvZ_pvjsH2qZA6qCZW9ad6S5QAfglTZHYx";

    public static final String SENTIMENT_API_KEY = "050210b11c32596d93e6dce64dce3005";
    private static final String ALERT_IAE = " distinct results found, but 21 or more are needed.";


    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();

    Stage stage;
    Scene scene;
    VBox root;
    HBox topRow;
    Label guessLabel;
    Label searchLabel;
    ComboBox priceOptions;
    Button go;
    TextField searchBox;
    Label sourcesLabel;
    Label notice;
    RadioButton strongNeg;
    RadioButton negative;
    RadioButton neutral;
    RadioButton positive;
    RadioButton strongPos;
    ToggleGroup radioGroup;
    Slider radius;
    HBox guessesHolder;
    Label radiusLabel;
    Label priceLabel;
//    Label score_tag;
//    Label agreement;
//    Label subjectivity;
//    Label confidence;
//    Label irony;
    Label instructions;
    TextFlow textFlow;
//    VBox output;
    int numResults;
    String[] aliases;
    String sentimentFilter;
    Restaurant[] restaurants;
    int numToPrint;
    Restaurant[] sentimentMatch;
    Timeline timeline;
    int numSentimentCalls;
//    LinkedList<Restaurant> rankedReviews;
//    ImageView imgView;


    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        this.aliases = new String[100];
//        this.output = new VBox();
        this.textFlow = new TextFlow();
        this.numResults = 0;
        this.numSentimentCalls = 0;
        this.root = new VBox();
        this.topRow = new HBox();
        this.searchLabel = new Label("Search: ");
        this.guessLabel = new Label("Guess: ");
        this.go = new Button("Go!");
        this.searchBox = new TextField("City, State or a street address...");
        this.priceOptions = new ComboBox();
        this.strongNeg = new RadioButton("Strong Negative");
        this.negative = new RadioButton("Negative");
        this.neutral = new RadioButton("Neutral");
        this.positive = new RadioButton("Positive");
        this.strongPos = new RadioButton("Strong Positive");
        this.radioGroup = new ToggleGroup();
        this.radiusLabel = new Label("Enter a preferred radius, in meters:");
        this.priceLabel = new Label("Price level: ");
        this.radius = new Slider(0, 40000,0);
        this.guessesHolder = new HBox();
        this.restaurants = new Restaurant[100];
        this.sentimentMatch = new Restaurant[100];
        this.timeline = new Timeline();
//        this.rankedReviews = new <Restaurant> LinkedList();
        this.instructions = new Label("What do you want the sentiment of the most recent Yelp review to be?");
//        this.score_tag = new Label("Score Tag: ");
//        this.agreement = new Label("Agreement: ");
//        this.subjectivity = new Label("Subjectivity: ");
//        this.confidence = new Label("Confidence: ");
//        this.irony = new Label("Irony: ");
//        this.imgView = new ImageView();

        this.sourcesLabel = new Label("Results provided by the Yelp Fusion API and MeaningCloud Sentiment Analysis API version 2.1");
        this.notice = new Label("Filter for restaurants...");

    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        this.stage = stage;
        // demonstrate how to load local asset using "file:resources/"
        Image yelpImage = new Image("file:resources/readme-banner.png");
//        Image yelpImage = new Image("https://business.yelp.com/wp-content/uploads/2020/10/ILL_Visitors_768x512_2x-768x512.png");
//        ImageView banner = new ImageView(bannerImage);
        ImageView imgView = new ImageView(yelpImage);

        imgView.setImage(yelpImage);
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(640);
        radius.setShowTickLabels(true);
        radius.setShowTickMarks(true);
        radius.setMajorTickUnit(5000);

        // some labels to display information

        strongNeg.setToggleGroup(radioGroup);
        negative.setToggleGroup(radioGroup);
        neutral.setToggleGroup(radioGroup);
        positive.setToggleGroup(radioGroup);
        strongPos.setToggleGroup(radioGroup);
        guessesHolder.getChildren().addAll(strongNeg, negative, neutral,positive, strongPos,go);

        guessesHolder.setHgrow(go, Priority.ALWAYS);
//        go.setAlignment(Pos.RIGHT_CENTER);
        priceOptions.setValue("$");
        priceOptions.getItems().addAll("$","$$","$$$","$$$$");

        root.getChildren().addAll(notice,topRow,radiusLabel, radius, instructions,
            guessesHolder,imgView,textFlow,sourcesLabel);
        topRow.getChildren().addAll(searchLabel,searchBox,priceLabel, priceOptions);
        topRow.setHgrow(searchBox, Priority.ALWAYS);


         // setup scene
        scene = new Scene(root);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

        this.go.setOnAction(event -> buttonPress());

    } // start

    public void buttonPress() {
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        System.out.println(toggleGroupValue);
        if (toggleGroupValue.equals("Strong Positive")) {
            this.sentimentFilter = "P+";
        } else if (toggleGroupValue.equals("Positive")) {
            this.sentimentFilter = "P";
        } else if (toggleGroupValue.equals("Neutral")) {
            this.sentimentFilter = "NEU";
        } else if (toggleGroupValue.equals("Negative")) {
            this.sentimentFilter = "N";
        } else if (toggleGroupValue.equals("Strong Negative")) {
            this.sentimentFilter = "N+";
        }
        getYelpAliases();
        getYelpReviews();
        getSentiment();
        printToScene();
        System.out.println("done!!!");
    }


    public void getYelpAliases() {
        System.out.println("alias has been stored.");
        double doubleRadius = this.radius.getValue();
        int intRadius = (int) Math.round(doubleRadius);
        String location = this.searchBox.getText();
//        String loc = esapi.encoder().encodeForUrl(location);
        String loc = URLEncoder.encode(location, StandardCharsets.US_ASCII);
        loc = loc.replaceAll("\\+", "%20");
//        String term = "restaurants";
        String selectedPrice = this.priceOptions.getValue().toString();
        int priceNum;
        if (selectedPrice.equals("$")){
            priceNum = 1;
        } else if (selectedPrice.equals("$$")) {
            priceNum = 2;
        } else if (selectedPrice.equals("$$$")) {
            priceNum = 3;
        } else {
            priceNum = 4;
        }
        String targetURL = "https://api.yelp.com/v3/businesses/search?"
        +"location="+loc+"&term=restaurants&radius="+intRadius+"&price="+priceNum
            +"&sort_by=best_match&limit=10";
        System.out.println(targetURL);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(targetURL))
            .header("accept", "application/json")
            .header("Authorization", "Bearer "+YELP_API_KEY)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        try {
            HttpResponse<String> response;
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println(responseBody);
            Gson gson = new GsonBuilder().create();
            YelpAliases response1 = gson.<YelpAliases>fromJson(responseBody,YelpAliases.class);
            System.out.println(response1.businesses.length);
            this.numResults = response1.businesses.length;
            if (response1.business.length != null) {
                for (int i = 0; i< response1.businesses.length; i++) {
                    YelpBusinesses business = response1.businesses[i];
                    System.out.println(business.alias);
                    aliases[i] = business.alias;
                    Restaurant newRestaurant = new Restaurant();
                    newRestaurant.setAlias(business.alias);
                    newRestaurant.setName(business.name);
                    restaurants[i] = newRestaurant;
                }
            } else {
                throw new IllegalArgumentException(this.numResults + ALERT_IAE);
            }
//            System.out.println(response1.alias);
        } catch (IOException ioe) {
            System.out.println("Unable to send message to server.");
        } catch (InterruptedException ie) {
            System.out.println("Unable to send message to server.");
        }

    }
//    HttpRequest request = HttpRequest.newBuilder();

    public void getYelpReviews() {
        System.out.println("Button has been pressed. Yelp reviews on the way...");
//        getYelpAliases();
        String each_alias;
//        each_alias = "mamas-boy-athens";
        try {
            for (int i = 0; i < numResults; i++) {
                each_alias = aliases[i];
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.yelp.com/v3/businesses/"
                        +each_alias+"/reviews?limit=1&sort_by=yelp_sort"))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + YELP_API_KEY)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
                HttpResponse<String> response;
                response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                String responseBody = response.body();
                Gson gson = new GsonBuilder().create();
                Request2Response response2;
                response2 = gson.<Request2Response>fromJson(responseBody,Request2Response.class);
                Reviews review = response2.reviews[0];
                restaurants[i].setReview(review.text);

                System.out.println(review.text);
//                   aliases[i] = business.alias;
//                System.out.println(response2.reviews.text);
            }
        } catch (IOException ioe) {
            System.out.println("Unable to send message to server.");
        } catch (InterruptedException ie) {
            System.out.println("Unable to send message to server.");
        }
//        System.out.println("Calling getSentiment()");
//        getSentiment();
//        printToScene();

    }

    public void getSentiment() {

//        try {
//            for (int i = 0; i < numResults; i++) {
            EventHandler<ActionEvent> handler = event -> {
                System.out.println("making next request...");
                String reviewEncoded = restaurants[numSentimentCalls].getReview().replace("\n", " ");
                reviewEncoded = reviewEncoded.replaceAll(" ", "%20");
                reviewEncoded = reviewEncoded.replaceAll("\"", "");
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.meaningcloud.com/sentiment-2.1?key="
                        +SENTIMENT_API_KEY+"&lang=en&txt="+reviewEncoded))
                        .header("accept", "application/json")
                        .build();
                    HttpResponse<String> response;
                    response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
                    String responseBody = response.body();
                    Gson gson = new GsonBuilder().create();
                    Request3Response response3;
                    response3 = gson.<Request3Response>fromJson(responseBody,Request3Response.class);
                    String sentiment = response3.score_tag;
                    restaurants[numSentimentCalls].setScoreTag(sentiment);
                    String confidence = response3.confidence;
                    int intConfidence = Integer.parseInt(confidence);
                    restaurants[numSentimentCalls].setConfidence(intConfidence);
                    this.numSentimentCalls++;
                } catch (IOException ioe) {
                    System.out.println("Unable to send message to server.");
                } catch (InterruptedException ie) {
                    System.out.println("Unable to send message to server.");
                }

            };
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
            timeline.setCycleCount(numResults);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            if (timeline.getCycleCount() == numResults) {
                timeline.stop();
            }
//            timeline.pause();
    }

    public void printToScene() {
        /*
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        System.out.println(toggleGroupValue);
        if (toggleGroupValue.equals("Strong Positive")) {
            this.sentimentFilter = "P+";
        } else if (toggleGroupValue.equals("Positive")) {
            this.sentimentFilter = "P";
        } else if (toggleGroupValue.equals("Neutral")) {
            this.sentimentFilter = "NEU";
        } else if (toggleGroupValue.equals("Negative")) {
            this.sentimentFilter = "N";
        } else if (toggleGroupValue.equals("Strong Negative")) {
            this.sentimentFilter = "N+";
        }
        */
        for (int i = 0; i < this.numResults; i++) {
            System.out.println("iteration");
            if (this.sentimentFilter.equals(restaurants[i].getScoreTag())) {
                System.out.println("Met condition to be printed at index " + i);
                this.textFlow.getChildren().add(new Text(restaurants[i].getName()
                +restaurants[i].getConfidence()+"\n"));
//                sentimentMatch[numToPrint] = restaurants[i];
//                numToPrint++;
            }
        }

/*

        for (int i = 0; i < this.numToPrint(); i++) {
            Label newLabel = new Label(sentimentMatch[i].name);
        }

*/
    }

    /**
     * Creates a new thread by taking Runnable parameter.
     * @param task Runnable task
     */
    public static void runInNewThread(Runnable task) {
        Thread t = new Thread(task);
        t.start();
    }

    /**
     * Show a model error akert based on {@code cause}.
     * @param cause a {@link java.lang.Throwable Throwable} that caused the alert
     */
    public static void alertError(Throwable cause) {
        TextArea text = new TextArea(cause.toString());
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    }



} // ApiApp

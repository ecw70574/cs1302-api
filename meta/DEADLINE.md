# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice looking HTML.

## Part 1: App Description

> Please provide a firendly description of your app, including the
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

    https://github.com/ecw70574/cs1302-api

    My app performs sentiment analysis on Yelp reviews, specifically
    the most RECENT yelp review for each restaurant that meets the user's
    search terms. The user can type in zipcode or a City, State. Then, they can
    include a preffered price level and radius of distance to search. From there,
    there are buttons to filter for the restaurants that have a for instance,
    overall positive or negative sentiment in their most recent review.

    The YelpFusion API is called twice, followed by a request to the MeaningCloud
    Sentiment Analysis API. The first request to YelpFusion uses the "Search Businesses"
    function to send the user's location information and price preference to store
    restauarants that meet these filters. Specifically, this API stores Yelp Aliases instead
    of names. The Yelp Aliases are then sent to the next Yelp API using the "Get Reviews by
    Business Id or Alias" function. The response is an array of reviews, with information about
    the most recent 3 reviews for each restauarant. The most recent review is parsed from this
    response, along with the text of the review. This information is sent to the MeaningCloud
    Sentiment Analysis information to store data about the overall sentiment, confidence level,
    consistency of agreement throughout the review, and certain sentences tagged for sentiment.

    Please note that the MeaningCloud Sentiment Analysis API has two repsonse variables
    that fail CheckStyle: score_tag and sentence_list. I had to use incorrect naming conventions
    in order to successfully reference this information.



## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I learned how to apply the basic outline we learned in class of how to
    call an API to various layouts of URI's. I understand a lot more
    about threads and how they work with JavaFX specifically. I also
    worked with Timelines to avoid exceeding the rate limit for my
    sentiment analysis API.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would plan the details of how to connect my API's earlier on, rather
    than waiting until I had made the HTTPRequests already. I would have
    had less stress trying to parse through the Json output to find
    meaningful information.

package com.mindy.dossett;

/*
This is the first Java class used to get initial User rating info
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class UserInfoInitializer {
    private User newUser;
    Scanner userInput = new Scanner(System.in);

    public UserInfoInitializer(){
        newUser = new User("newUser");
    }

    private ArrayList<ArrayList<String>> getMovieForRating(){
        // only include more recent movies for young audiences
        ArrayList<Movie> allMovies = MovieDatabase.filterBy(new YearAfterFilter(1980));
        // Find the top rated movies
        ArrayList<RatingLookUp> allRatings = new ArrayList<RatingLookUp>();
        //Find the most watched movies
        ArrayList<RatingLookUp> allCounts = new ArrayList<RatingLookUp>();
        for (Movie movie: allMovies){
            double rating = movie.calAvgMovieRating();
            int cnt = movie.getViewCount();
            if (cnt != 0){
                String id = movie.getId();
                allRatings.add(new RatingLookUp(id, rating));
                allCounts.add(new RatingLookUp(id, cnt));
            }
        }
        Collections.sort(allRatings, Collections.reverseOrder());
        Collections.sort(allCounts, Collections.reverseOrder());
        ArrayList<String> top_50 = new ArrayList<String>();
        for (int i = 0; i< 50; i++){
            top_50.add(allRatings.get(i).getLookUpId());
            top_50.add(allCounts.get(i).getLookUpId());
        }
        // Random shuffle for top 20 movies
        Random rand = new Random();
        HashSet<String> chosenMovie = new HashSet<String>();
        while (chosenMovie.size() < 20){
            int randomInd = rand.nextInt(100);
            chosenMovie.add(top_50.get(randomInd));
        }
        ArrayList<String> movieList = new ArrayList<String>(chosenMovie);
        // get movie Titles for easy access
        ArrayList<String> movieTitles = new ArrayList<String>();
        for (String id: movieList){
            movieTitles.add(MovieDatabase.getMovie(id).getTitle());
        }
        ArrayList<ArrayList<String>> forRating = new ArrayList<ArrayList<String>>();
        forRating.add(movieList);
        forRating.add(movieTitles);
        return forRating;
    }

    public void UpdateNewUser(){
        ArrayList<ArrayList<String>> moviesForRating = getMovieForRating();
        ArrayList<String> movieId = moviesForRating.get(0);
        ArrayList<String> movieTitle = moviesForRating.get(1);
        // fix at 20 movies
        System.out.println("Please rate the following movies on a scale of 0 to 10 (0 is the worst and 10 is the best).");
        System.out.println("If you haven't watched the movie, please enter -1");
        for (int i=0; i < 20; i++){
            System.out.println("Please rate the movie: <<"+ movieTitle.get(i)+">>");
            double score = userInput.nextDouble();
            if (score != -1.0) {
                newUser.addRating(movieId.get(i), score);
            }
        }
        UserDatabase.updateUserInfo(newUser);
    }
}

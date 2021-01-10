package com.mindy.dossett;

import java.util.HashMap;
import java.util.ArrayList;
import static java.util.stream.Collectors.toCollection;

/*
This object represents a single User, a record from UserDatabase
 */

public class User {
    private String userId;
    private HashMap<String, MovieRating> infoMap;

    public User(String aUserId){
        userId = aUserId;
        infoMap = new HashMap<String, MovieRating>();
    }

    public void addRating(String movieId,double rating, long rateTime){
        infoMap.put(movieId, new MovieRating(movieId, rating,rateTime));
    }

    public boolean hasRating(String movieId){
        return infoMap.containsKey(movieId);
    }

    public String getUserId() {
        return userId;
    }

    public double getRating(String movieId){
        if (infoMap.containsKey(movieId)){
            return infoMap.get(movieId).getRatingValue();
        }
        return -1;
    }

    public int numRatings(){
        return infoMap.size();
    }

    public ArrayList<String> getMoviesRated(){
        return infoMap.keySet().stream().collect(toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", numOfRatings =" + infoMap.size() +
                '}';
    }
}

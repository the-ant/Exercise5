package com.khtn.hang.excercise_week5;


public class Constants {

    public static final String HEADER_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";

    public static final String URL_NOW_PLAYING_API = "https://api.themoviedb.org/3/movie/now_playing?" +
            "api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String URL_TOP_RATE_API = "https://api.themoviedb.org/3/movie/top_rated?" +
            "api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public static final String TAG_TODO_LIST = "Todo List";
    public static final String TAG_DONE = "Done";

    public static final String PRIORITY_LOW = "LOW";
    public static final String PRIORITY_NORMAL = "NORMAL";
    public static final String PRIORITY_HIGH = "HIGH";

    public static final int PRIORITY_LOW_ID = 1;
    public static final int PRIORITY_NORMAL_ID = PRIORITY_LOW_ID + 1;
    public static final int PRIORITY_HIGH_ID = PRIORITY_NORMAL_ID + 1;
}

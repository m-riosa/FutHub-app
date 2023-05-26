package com.example.futhub.models;

import java.util.ArrayList;

public class FixtureResponse {
    public String get;
    public Parameters parameters;
    public ArrayList<Object> errors;
    public int results;
    public Paging paging;
    public ArrayList<Response> response;
    public ArrayList<FixtureData> data;

}

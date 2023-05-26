package com.example.futhub;

import com.example.futhub.models.FixtureResponse;

public interface ResponseListener {
    void didFetch(FixtureResponse response, String message);
    void didError(String message);
}

package com.example.mainactivity;

public enum State {
    HasData,    // success

    Loading,    // process
                // failure
    HasNoData,
    NetworkError,
    ServerError
}
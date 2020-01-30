package com.example.mainactivity.data.network;

public enum State {
    HasData,    // success

    Loading,    // process
                // failure
    HasNoData,
    NetworkError,
    ServerError
}
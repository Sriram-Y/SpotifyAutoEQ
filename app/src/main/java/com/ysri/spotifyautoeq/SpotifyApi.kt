package com.ysri.spotifyautoeq

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotifyApi {
    @GET("/v1/audio-features")
    suspend fun getTrackWithAudioFeatures(@Path("id") id: String): Response<Track>
}
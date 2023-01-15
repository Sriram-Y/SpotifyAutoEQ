package com.ysri.spotifyautoeq

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector.ConnectionListener
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track


class MainActivity : AppCompatActivity() {
    private val CLIENT_ID = "13684737619449b59f2aa606ed3f9b2c"
    private val REDIRECT_URI = "http://localhost:5555"
    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams,
            object : ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Log.d("MainActivity", "Connection Established!")

                    // Now you can start interacting with App Remote
                    connected()
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", throwable.message, throwable)

                    // Something went wrong when attempting to connect! Handle errors here
                }
            })
    }

    private fun connected() {
        // Subscribe to PlayerState
        // Subscribe to PlayerState
        mSpotifyAppRemote!!.playerApi
            .subscribeToPlayerState()
            .setEventCallback { playerState: PlayerState ->
                val track: Track? = playerState.track
                if (track != null) {
                    val currentSongInfoView: TextView = findViewById<TextView>(R.id.currentSongInfoView)
                    val trackInfo =  "<b>" + track.name + "</b>"
                    currentSongInfoView.text = Html.fromHtml(trackInfo, 0)

                    val currentArtistInfoView: TextView = findViewById<TextView>(R.id.currentArtistInfoView)
                    val artistInfo = "<b>" + track.artist.name + "</b>"
                    currentArtistInfoView.text = Html.fromHtml(artistInfo, 0)

                    
                }
            }
    }

    override fun onStop() {
        super.onStop()
        // And we will finish off here.
    }
}
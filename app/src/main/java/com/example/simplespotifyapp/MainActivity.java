package com.example.simplespotifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.simplespotifyapp.FindMeans.findMeanOfFloatArrayList;


public class MainActivity extends AppCompatActivity {
    static boolean getStuff = true;
    private static final String CLIENT_ID = "7406a90fa7c24810a377cccb6bf542fc";
    private static final String REDIRECT_URI = "http://localhost/";
    private SpotifyAppRemote mSpotifyAppRemote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGoClick(View view){
        EditText token = findViewById(R.id.Token);
        EditText playlist = findViewById(R.id.playlistId);
        String tokenId = token.getText().toString();
        System.out.println(tokenId);
        String playlistId = playlist.getText().toString();
        SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step

        api.setAccessToken(tokenId);
        SpotifyService spotify = api.getService();
        spotify.getTopTracks(new Callback<Pager<kaaes.spotify.webapi.android.models.Track>>() {

            @Override
            public void success(Pager<kaaes.spotify.webapi.android.models.Track> trackPager, Response response) {
                Log.d("Track success", String.valueOf(trackPager.items));
                // declare arraylists
                ArrayList<Float> acousticnesses = new ArrayList<Float>();
                ArrayList<Float> danceabilities = new ArrayList<Float>();
                ArrayList<Float> energies = new ArrayList<Float>();
                ArrayList<Float> instrumentalnesses = new ArrayList<Float>();
                ArrayList<Float> loudnesses = new ArrayList<Float>();
                ArrayList<Float> speechinesses = new ArrayList<Float>();
                ArrayList<Float> tempos = new ArrayList<Float>();
                ArrayList<Float> valences = new ArrayList<Float>();
                int i = 0;
                while (i<trackPager.items.size()){
                    kaaes.spotify.webapi.android.models.Track item = trackPager.items.get(i);
                    String trackId = item.id;
                    //System.out.println(item.name);
                    int finalI = i;
                    spotify.getTrackAudioFeatures(trackId, new Callback<AudioFeaturesTrack>() {
                        @Override
                        public void success(AudioFeaturesTrack audioFeaturesTrack, Response response) {
                            // save features to arraylists
                            float acousticness = audioFeaturesTrack.acousticness;
                            float danceability = audioFeaturesTrack.danceability;
                            float energy = audioFeaturesTrack.energy;
                            float instrumentalness = audioFeaturesTrack.instrumentalness;
                            float loudness = audioFeaturesTrack.loudness;
                            float speechiness = audioFeaturesTrack.speechiness;
                            float tempo = audioFeaturesTrack.tempo;
                            float valence = audioFeaturesTrack.valence;
                            acousticnesses.add(acousticness);
                            danceabilities.add(danceability);
                            energies.add(energy);
                            instrumentalnesses.add(instrumentalness);
                            loudnesses.add(loudness);
                            speechinesses.add(speechiness);
                            tempos.add(tempo);
                            valences.add(valence);
                            //System.out.println(acousticnesses);
                            //System.out.println(finalI);
                            //System.out.println(getStuff);
                            //System.out.println(trackPager.items.size()-1);
                            if (finalI >= trackPager.items.size()-1 & getStuff == true){
                                System.out.println(acousticnesses);
                                System.out.println(danceabilities);
                                System.out.println(energies);
                                System.out.println(instrumentalnesses);
                                System.out.println(loudnesses);
                                System.out.println(speechinesses);
                                System.out.println(tempos);
                                System.out.println(valences);
                                //System.out.println(finalI);
                                float meanAcousticness = findMeanOfFloatArrayList(acousticnesses);
                                float meanDanceability = findMeanOfFloatArrayList(danceabilities);
                                float meanEnergy = findMeanOfFloatArrayList(energies);
                                float meanInstrumentalness = findMeanOfFloatArrayList(instrumentalnesses);
                                float meanLoudness = findMeanOfFloatArrayList(loudnesses);
                                float meanSpeechiness = findMeanOfFloatArrayList(speechinesses);
                                float meanTempo = findMeanOfFloatArrayList(tempos);
                                float meanValence = findMeanOfFloatArrayList(valences);
                                System.out.println(meanAcousticness);
                                System.out.println(meanDanceability);
                                System.out.println(meanEnergy);
                                System.out.println(meanInstrumentalness);
                                System.out.println(meanLoudness);
                                System.out.println(meanSpeechiness);
                                System.out.println(meanTempo);
                                System.out.println(meanValence);
                                getStuff = false;
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("failure acousticness", error.toString());
                        }
                    });
                                    /*System.out.println(acousticnesses);
                                    System.out.println("test");*/
                    i++;
                }
                //System.out.println(acousticnesses);
                //System.out.println("test");
                                /*for (int i=0; i<trackPager.items.size(); i++) {
                                    kaaes.spotify.webapi.android.models.Track item = trackPager.items.get(i);
                                    String trackId = item.id;
                                    //System.out.println(item.name);
                                    spotify.getTrackAudioFeatures(trackId, new Callback<AudioFeaturesTrack>() {
                                        @Override
                                        public void success(AudioFeaturesTrack audioFeaturesTrack, Response response) {
                                            // save features to arraylists
                                            float acousticness = audioFeaturesTrack.acousticness;
                                            acousticnesses.add(acousticness);
                                            System.out.println(acousticnesses);

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d("failure acousticness", error.toString());
                                        }
                                    });
                                    System.out.println(acousticnesses);
                                } */

                                /*System.out.println(acousticnesses);
                                float meanAcousticness = findMeanOfFloatArrayList(acousticnesses);
                                System.out.println(meanAcousticness);*/


            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Track failure", error.toString());
            }
        });
    }



    /*@Override
    protected void onStart() {
        super.onStart();
        // We will start writing our code here.
        // Set the connection parameters
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                        // Now you can start interacting with App Remote
                        connected();
                        SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step

                        api.setAccessToken("BQD_Bg1TLlaaMjObyNzxlRn9bqMOedG8iaTWoIeguuOAqD0as479u64wJTI_oXSFuW8gxhbD3_Na_6I34KOA-MJLaRM6bjZhzTFBrAK7f6kzRE8gNFEI_EqbDa7zGsnJL091w7GztP9BrNKlI47ptieethEmivLexBINGJ5jD8OHybDC6VFYPdG5W3nhP6_9_OqLEbAs3kDq-HLDk1dfFO9Ayz8k1cuMOfTUWHpm2BdAH0guPMuR-gJWkn1AVvU0tHWYfMqIzDB8UEvOULpSy8I-k5w1S4xbQaGFvYb5");
                        SpotifyService spotify = api.getService();

                        spotify.getAlbumTracks("1TkwzY3l4LqAfrQwBAx45Q", new Callback<Pager<kaaes.spotify.webapi.android.models.Track>>() {

                            @Override
                            public void success(Pager<kaaes.spotify.webapi.android.models.Track> trackPager, Response response) {
                                Log.d("Track success", String.valueOf(trackPager.items));
                                // declare arraylists
                                ArrayList<Float> acousticnesses = new ArrayList<Float>();
                                ArrayList<Float> danceabilities = new ArrayList<Float>();
                                ArrayList<Float> energies = new ArrayList<Float>();
                                ArrayList<Float> instrumentalnesses = new ArrayList<Float>();
                                ArrayList<Float> loudnesses = new ArrayList<Float>();
                                ArrayList<Float> speechinesses = new ArrayList<Float>();
                                ArrayList<Float> tempos = new ArrayList<Float>();
                                ArrayList<Float> valences = new ArrayList<Float>();
                                int i = 0;
                                while (i<trackPager.items.size()){
                                    kaaes.spotify.webapi.android.models.Track item = trackPager.items.get(i);
                                    String trackId = item.id;
                                    //System.out.println(item.name);
                                    int finalI = i;
                                    spotify.getTrackAudioFeatures(trackId, new Callback<AudioFeaturesTrack>() {
                                        @Override
                                        public void success(AudioFeaturesTrack audioFeaturesTrack, Response response) {
                                            // save features to arraylists
                                            float acousticness = audioFeaturesTrack.acousticness;
                                            float danceability = audioFeaturesTrack.danceability;
                                            float energy = audioFeaturesTrack.energy;
                                            float instrumentalness = audioFeaturesTrack.instrumentalness;
                                            float loudness = audioFeaturesTrack.loudness;
                                            float speechiness = audioFeaturesTrack.speechiness;
                                            float tempo = audioFeaturesTrack.tempo;
                                            float valence = audioFeaturesTrack.valence;
                                            acousticnesses.add(acousticness);
                                            danceabilities.add(danceability);
                                            energies.add(energy);
                                            instrumentalnesses.add(instrumentalness);
                                            loudnesses.add(loudness);
                                            speechinesses.add(speechiness);
                                            tempos.add(tempo);
                                            valences.add(valence);
                                            //System.out.println(acousticnesses);
                                            //System.out.println(finalI);
                                            //System.out.println(getStuff);
                                            //System.out.println(trackPager.items.size()-1);
                                            if (finalI >= trackPager.items.size()-1 & getStuff == true){
                                                System.out.println(acousticnesses);
                                                System.out.println(danceabilities);
                                                System.out.println(energies);
                                                System.out.println(instrumentalnesses);
                                                System.out.println(loudnesses);
                                                System.out.println(speechinesses);
                                                System.out.println(tempos);
                                                System.out.println(valences);
                                                //System.out.println(finalI);
                                                float meanAcousticness = findMeanOfFloatArrayList(acousticnesses);
                                                float meanDanceability = findMeanOfFloatArrayList(danceabilities);
                                                float meanEnergy = findMeanOfFloatArrayList(energies);
                                                float meanInstrumentalness = findMeanOfFloatArrayList(instrumentalnesses);
                                                float meanLoudness = findMeanOfFloatArrayList(loudnesses);
                                                float meanSpeechiness = findMeanOfFloatArrayList(speechinesses);
                                                float meanTempo = findMeanOfFloatArrayList(tempos);
                                                float meanValence = findMeanOfFloatArrayList(valences);
                                                System.out.println(meanAcousticness);
                                                System.out.println(meanDanceability);
                                                System.out.println(meanEnergy);
                                                System.out.println(meanInstrumentalness);
                                                System.out.println(meanLoudness);
                                                System.out.println(meanSpeechiness);
                                                System.out.println(meanTempo);
                                                System.out.println(meanValence);
                                                getStuff = false;
                                            }

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d("failure acousticness", error.toString());
                                        }
                                    });
                                    /*System.out.println(acousticnesses);
                                    System.out.println("test");*/
                                    //i++;
                                //}
                                //System.out.println(acousticnesses);
                                //System.out.println("test");
                                /*for (int i=0; i<trackPager.items.size(); i++) {
                                    kaaes.spotify.webapi.android.models.Track item = trackPager.items.get(i);
                                    String trackId = item.id;
                                    //System.out.println(item.name);
                                    spotify.getTrackAudioFeatures(trackId, new Callback<AudioFeaturesTrack>() {
                                        @Override
                                        public void success(AudioFeaturesTrack audioFeaturesTrack, Response response) {
                                            // save features to arraylists
                                            float acousticness = audioFeaturesTrack.acousticness;
                                            acousticnesses.add(acousticness);
                                            System.out.println(acousticnesses);

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d("failure acousticness", error.toString());
                                        }
                                    });
                                    System.out.println(acousticnesses);
                                } */

                                /*System.out.println(acousticnesses);
                                float meanAcousticness = findMeanOfFloatArrayList(acousticnesses);
                                System.out.println(meanAcousticness);


                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("Track failure", error.toString());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Then we will write some more code here.
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

*/
}
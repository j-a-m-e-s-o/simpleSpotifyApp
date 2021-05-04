package com.example.simplespotifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.Track;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.net.HttpURLConnection;

import static com.example.simplespotifyapp.FindMeans.findMeanOfFloatArrayList;


public class MainActivity extends AppCompatActivity {
    //http request test
    private TextView httptest;
    //httpEnd
    static boolean getStuff = true;
    private static final String CLIENT_ID = "7406a90fa7c24810a377cccb6bf542fc";
    private static final String REDIRECT_URI = "http://localhost/";
    private SpotifyAppRemote SpotifyAppRemote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGoClick(View view) {
        EditText token = findViewById(R.id.Token);
        EditText playlist = findViewById(R.id.playlistId);
        EditText user = findViewById(R.id.userId);
        String tokenId = token.getText().toString();
        System.out.println(tokenId);
        String playlistId = playlist.getText().toString();
        String userId = user.getText().toString();

        //http request test
        httptest = findViewById(R.id.httpplaceholder);

        OkHttpClient client = new OkHttpClient();

        String url = "https://accounts.spotify.com/authorize?client_id=7406a90fa7c24810a377cccb6bf542fc&response_type=code&redirect_uri=http://localhost/&scope=user-read-private%20user-read-email&show_dialog=true";//

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                System.out.println("Response Received");
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            httptest.setText(myResponse);
                        }
                    });
                }
            }
        });
        //httpEnd



        SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step

        api.setAccessToken(tokenId);
        SpotifyService spotify = api.getService();
        spotify.getPlaylistTracks(userId, playlistId, new Callback<Pager<PlaylistTrack>>() {
            @Override
            public void success(Pager<PlaylistTrack> playlistTrackPager, Response response) {
                Log.e("TEST", "GOT the tracks in playlist");
                ArrayList<Track> trackItems = new ArrayList<Track>();
                List<PlaylistTrack> items = playlistTrackPager.items;
                for (PlaylistTrack pt : items) {
                    String id = pt.track.id;
                    spotify.getTrack(id, new Callback<Track>() {
                        @Override
                        public void success(Track track, Response response) {
                            trackItems.add(track);
                            System.out.println("So far " + trackItems.size() + " tracks have been added to the trackItems ArrayList.");
                            if (trackItems.size() == playlistTrackPager.items.size()){
                                ValueGetter.mainCode(trackItems, tokenId);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println("[2] Failed to get Track from PlaylistTrack");
                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TEST", "[1] Could not get playlist tracks");
                System.out.println("Not Working");
            }
        });

    }}
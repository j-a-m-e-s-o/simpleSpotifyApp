package com.example.simplespotifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.util.ArrayList;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.simplespotifyapp.FindMeans.findMeanOfFloatArrayList;

public class ValueGetter {
    public static void mainCode(ArrayList trackItems, String tokenId) {
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(tokenId);
        SpotifyService spotify = api.getService();
        System.out.println("Overall there are " + trackItems.size() + " tracks that have been added to the trackItems ArrayList.");
        //Log.d("Track success", String.valueOf(playlistTrackPager.items));
        // declare arraylists
        ArrayList<Float> acousticnesses = new ArrayList<Float>();
        ArrayList<Float> danceabilities = new ArrayList<Float>();
        ArrayList<Float> energies = new ArrayList<Float>();
        ArrayList<Float> instrumentalnesses = new ArrayList<Float>();
        ArrayList<Float> loudnesses = new ArrayList<Float>();
        ArrayList<Float> speechinesses = new ArrayList<Float>();
        ArrayList<Float> tempos = new ArrayList<Float>();
        ArrayList<Float> valences = new ArrayList<Float>();
        //System.out.println(playlistTrackPager.items.size());
                /*while (trackItems.size()!=playlistTrackPager.items.size()){
                    try{
                        Thread.sleep(1);
                    }catch(InterruptedException e){
                    }
                    System.out.println(trackItems.size());
                }*/
        int i = 0;
        while (i<trackItems.size()){
            System.out.println("test2");
            kaaes.spotify.webapi.android.models.Track item = (Track) trackItems.get(i);//this might not work
            System.out.println("test3");
            String trackId = item.id;
            System.out.println(item.name);
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
                    if (finalI >= trackItems.size()-1){
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
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("failure acousticness", error.toString());
                }
            });
            i++;
        }
    }
}

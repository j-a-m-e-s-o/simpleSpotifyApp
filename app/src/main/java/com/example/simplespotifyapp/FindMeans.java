package com.example.simplespotifyapp;

import java.util.ArrayList;

public class FindMeans {
    public static float findMeanOfFloatArrayList(ArrayList<Float> floats){
        float totalValue = 0;
        float totalAmount = 0;
        //System.out.println(floats.size());
        for (int i=0; i<floats.size(); i++){
            //System.out.println("test");
            totalValue+=floats.get(i);
            totalAmount+=1;
            //System.out.println(totalValue);
            //System.out.println(totalAmount);
        }

        float mean = totalValue / totalAmount;
        return mean;
    }
}

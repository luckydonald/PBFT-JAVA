package de.teamproject16.pbft;

import de.teamproject16.pbft.Messages.InitMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IngridBoldt on 29.09.16.
 */
public class Median {

    /** calculates the median of a given initStore list. This list has to only contain the current sequence number. **/
    public static double calculateMedian(List<InitMessage> initStore) throws InterruptedException {
        //TODO: Start Locking
        List<Double> floatStore = new LinkedList<>();
        for (InitMessage msg : initStore){
            floatStore.add(msg.value);
        }
        //TODO: End lock
        Collections.sort(floatStore);
        int calculate = floatStore.size()/2;
        return floatStore.get(calculate);
    }
/*
    public static void simulate(int count){
        for(int i = 0; i<count; i++){
            double v = Math.random()*10;
            value.add(v);
        }
    }

    public static void main (String[] args){
        simulate(4);
        System.out.println(value);
        System.out.println(calculateMedian());
    }
    */
}

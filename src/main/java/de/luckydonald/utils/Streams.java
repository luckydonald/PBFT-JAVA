package de.luckydonald.utils;

import de.luckydonald.utils.ObjectWithLogger;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collector + ObjectWithLogger
 *
 * @author luckydonald
 * @since 07.11.2016
 **/
public class Streams extends ObjectWithLogger {
    public static <T> Collector<T, ?, ArrayList<T>> toArrayList() {
        return Collectors.toCollection(ArrayList::new);
    }
}
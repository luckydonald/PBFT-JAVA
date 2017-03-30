package de.luckydonald.utils;

import de.luckydonald.utils.ObjectWithLogger;

/**
 * Shortcuts for user IO.
 *
 * @author luckydonald
 * @since 30.03.2017
 **/
public class UserInput extends ObjectWithLogger {

    public static boolean stringIsTrue(String input) {
        switch ( input.toLowerCase()) {
            case "yes":
            case "y":
            case "ja":
            case "j":
            case "true":
            case "t":
            case "1":
                return true;
            default:
                return false;
        }
    }

}
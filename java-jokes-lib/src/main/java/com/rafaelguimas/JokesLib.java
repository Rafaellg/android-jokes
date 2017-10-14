package com.rafaelguimas;

import java.util.Random;

public class JokesLib {

    public static String getJoke() {
        int jokeNumber = new Random().nextInt(5);

        switch (jokeNumber) {
            case 0:
                return "iOS > Android";
            case 1:
                return "Windows Phone > Android";
            case 2:
                return "Tizen > Android";
            case 3:
                return "Symbian > Android";
            default:
                return "BlackBerry OS > Android";
        }
    }
}

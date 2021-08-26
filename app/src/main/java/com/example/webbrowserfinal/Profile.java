package com.example.webbrowserfinal;

import android.graphics.Color;


/**
 * Profil użytkownika, przechowuje ustawienia szaty graficznej i języka.
 */
public class Profile {

    /**
     * Kolor tekstu
     */
    int colorText = Color.BLACK;
    /**
     * Kolor ikon
     */
    int colorIcon = Color.BLACK;
    /**
     * Kolor przycisków
     */
    int colorButton = Color.LTGRAY;
    /**
     * Kolor tła
     */
    int colorBackground = Color.WHITE;
    /**
     * Czcionka
     */
    int font = 0;
    /**
     * Rozmiar malej czcionki
     */
    int smallFontSize = 10;
    /**
     * Rozmiar średniej czcionki
     */
    int mediumFontSize = 20;
    /**
     * Rozmiar dużej czcionki
     */
    int bigFontSize = 40;
    /**
     * Czy czcionka pogrubiona
     */
    int bold = 0;
    /**
     * Czy czcionka pochylona
     */
    int italic = 0;
    /**
     * Język
     */
    int language = 0;
    /**
     * Adblock
     */
    int adblock = 0;
}

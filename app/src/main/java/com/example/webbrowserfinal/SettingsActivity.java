package com.example.webbrowserfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * Activity ustawień
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Kolor tekstu
     */
    public static int colorText = Color.BLACK;
    /**
     * Kolor ikon
     */
    public static int colorIcon = Color.BLACK;
    /**
     * Kolor przycisków
     */
    public static int colorButton = Color.LTGRAY;
    /**
     * Kolor tła
     */
    public static int colorBackground = Color.WHITE;
    /**
     * Czcionka
     */
    public static int font = 0;
    /**
     * Rozmiar malej czcionki
     */
    public static int smallFontSize = 10;
    /**
     * Rozmiar średniej czcionki
     */
    public static int mediumFontSize = 20;
    /**
     * Rozmiar dużej czcionki
     */
    public static int bigFontSize = 40;
    /**
     * Czy czcionka pogrubiona
     */
    public static int bold = 0;
    /**
     * Czy czcionka pochylona
     */
    public static int italic = 0;
    /**
     * Język
     */
    public static int language = 0;
    /**
     * Adblock
     */
    public static int adblock = 0;
    /**
     * Ilość profili
     */
    public static int amountOfProfiles = 1;
    /**
     * Obecny profil
     */
    public static int currentProfile = 0;
    /**
     * Lista profili
     */
    public static ArrayList<Profile> profiles = new ArrayList<>();
    /**
     * Główny layout activity
     */
    LinearLayout settingsLayout;
    /**
     * TextView tytułu ectivity
     */
    TextView titleTextView;
    /**
     * Przycisk zmiany koloru tekstu
     */
    Button colorTextButton;
    /**
     * Przycisk zmiany koloru ikon
     */
    Button colorIconButton;
    /**
     * Przycisk zmiany koloru przycisków
     */
    Button colorButtonButton;
    /**
     * Przeycisk zmiany koloru tła
     */
    Button colorBackgroundButton;
    /**
     * TextView napis czcionka
     */
    TextView fontTextView;
    /**
     * Spinner wyboru czcionki
     */
    Spinner fontSpinner;
    /**
     * TextView małej czcionki
     */
    TextView smallFontTextView;
    /**
     * Spinner wyboru rozmiaru małej czcionki
     */
    Spinner smallFontSizeSpinner;
    /**
     * TextView średniej czcionki
     */
    TextView mediumFontTextView;
    /**
     * Spinner wyboru rozmiaru średniej czcionki
     */
    Spinner mediumFontSizeSpinner;
    /**
     * TextView dużej czcionki
     */
    TextView bigFontTextView;
    /**
     * Spinner wyboru rozmiaru dużej czcionki
     */
    Spinner bigFontSizeSpinner;
    /**
     * CheckBox pogrubienia czcionki
     */
    CheckBox boldCheckBox;
    /**
     * CheckBox pochylenia czcionki
     */
    CheckBox italicCheckBox;
    /**
     * TextView języka
     */
    TextView languageTextView;
    /**
     * Spiner wyboru języka
     */
    Spinner languageSpinner;
    /**
     * TextView profilu
     */
    TextView profileTextView;
    /**
     * Spinner profili
     */
    Spinner profileSpinner;
    /**
     * Przycisk dodawania nowego profilu
     */
    Button addNewProfileButton;
    /**
     * Przycisk usuwania obecnego profilu
     */
    Button deleteCurrentProfileButton;
    /**
     * Adblock switch
     */
    Switch adblockSwitch;
    /**
     * Lista czcionek
     */
    ArrayList<String> fonts;
    /**
     * Lista rozmiarów małej czcionki
     */
    ArrayList<Integer> smallFontSizes;
    /**
     * Lista rozmiarów śedniej czcionki
     */
    ArrayList<Integer> mediumFontSizes;
    /**
     * Lista rozmiaró dużej czcionki
     */
    ArrayList<Integer> bigFontSizes;
    /**
     * Lista języków
     */
    ArrayList<String> languages;
    /**
     * Lista nazw użytkowników
     */
    ArrayList<String> profiles_names;
    /**
     * Adapter spinnera wyboru profilu użytkownika
     */
    ArrayAdapter<String> profilesAdapter;

    /**
     * Przesłonięta metoda onCreate z klasy nadrzędnej AppCompatActivity.
     * Wykonuje metodę super przesyłając do niej bundle z zapisanym stanem activity.
     * Ustawia widok activity.
     * Inicjalizuje przyciski.
     * Odświeża widok poprzez metodę refeshView()
     *
     * @param savedInstanceState bundle zapisanego stanu activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsLayout = findViewById(R.id.settings_layout);
        titleTextView = findViewById(R.id.settings_titleTextView);
        colorTextButton = findViewById(R.id.settings_colorTextButton);
        colorIconButton = findViewById(R.id.settings_colorIconButton);
        colorButtonButton = findViewById(R.id.settings_colorButtonButton);
        colorBackgroundButton = findViewById(R.id.settings_colorBackgroundButton);
        fontTextView = findViewById(R.id.settings_fontTextView);
        fontSpinner = findViewById(R.id.settings_fontSpinner);
        smallFontTextView = findViewById(R.id.settings_smallFontSizeTextView);
        smallFontSizeSpinner = findViewById(R.id.settings_smallFontSizeSpinner);
        mediumFontTextView = findViewById(R.id.settings_mediumFontSizeTextView);
        mediumFontSizeSpinner = findViewById(R.id.settings_mediumFontSizeSpinner);
        bigFontTextView = findViewById(R.id.settings_bigFontSizeTextView);
        bigFontSizeSpinner = findViewById(R.id.settings_bigFontSizeSpinner);
        boldCheckBox = findViewById(R.id.settings_boldFontCheckBox);
        italicCheckBox = findViewById(R.id.settings_italicFontCheckBox);
        languageTextView = findViewById(R.id.settings_languageTextView);
        languageSpinner = findViewById(R.id.settings_languageSpinner);
        profileTextView = findViewById(R.id.settings_profile1TextView);
        profileSpinner = findViewById(R.id.settings_profileSpinner);
        addNewProfileButton = findViewById(R.id.settings_addNewProfileButton);
        deleteCurrentProfileButton = findViewById(R.id.settings_deleteCurrentProfileButton);
        adblockSwitch = findViewById(R.id.settings_adblockSwitch);


        createSpinnersValues();
        initializeSpinnersAdapters();
        setValues();
        refreshView();
        initializeButtons();
        initializeSpinners();
        initializeCheckBoxes();


    }

    /**
     * Metoda odświeżająca widok. Ustawia kolory poszczególnych przycisków na kolory reprezentujące
     * kolory komponentów odpowiedzialnych za dany przycisk. Jeżeli wybrany kolor jest za jasny,
     * ustawia kolor tekstu danego przycisku na czarny, jeżeli kolor jest ciemny ustawia kolor tekstu
     * na biały
     */
    private void refreshView() {
        setValues();
        setColors();
        setFontSizes();
        setFonts();
        setLanguage();
    }


    /**
     * Ustawianie kolorów elementów interfejsu graficznego.
     */
    private void setColors() {
        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}};
        if (profiles.get(currentProfile).colorText < -11400694) {
            colorTextButton.setTextColor(-1);
        } else {
            colorTextButton.setTextColor(-16777216);
        }

        if (profiles.get(currentProfile).colorIcon < -11400694) {
            colorIconButton.setTextColor(-1);
        } else {
            colorIconButton.setTextColor(-16777216);
        }

        if (profiles.get(currentProfile).colorButton < -11400694) {
            colorButtonButton.setTextColor(-1);
        } else {
            colorButtonButton.setTextColor(-16777216);
        }

        if (profiles.get(currentProfile).colorBackground < -11400694) {
            colorBackgroundButton.setTextColor(-1);
        } else {
            colorBackgroundButton.setTextColor(-16777216);
        }

        colorTextButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorText}));
        colorIconButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorIcon}));
        colorButtonButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorButton}));
        colorBackgroundButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorBackground}));
        addNewProfileButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorButton}));
        addNewProfileButton.setTextColor(profiles.get(currentProfile).colorText);
        deleteCurrentProfileButton.setBackgroundTintList(new ColorStateList(states, new int[]{profiles.get(currentProfile).colorButton}));
        deleteCurrentProfileButton.setTextColor(profiles.get(currentProfile).colorText);

        titleTextView.setTextColor(profiles.get(currentProfile).colorText);
        settingsLayout.setBackgroundColor(profiles.get(currentProfile).colorBackground);

        fontTextView.setTextColor(profiles.get(currentProfile).colorText);
        smallFontTextView.setTextColor(profiles.get(currentProfile).colorText);
        mediumFontTextView.setTextColor(profiles.get(currentProfile).colorText);
        bigFontTextView.setTextColor(profiles.get(currentProfile).colorText);
        languageTextView.setTextColor(profiles.get(currentProfile).colorText);
        profileTextView.setTextColor(profiles.get(currentProfile).colorText);

        fontSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (fontSpinner.getSelectedView() != null) {
            ((TextView) fontSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }
        smallFontSizeSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (smallFontSizeSpinner.getSelectedView() != null) {
            ((TextView) smallFontSizeSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }
        mediumFontSizeSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (mediumFontSizeSpinner.getSelectedView() != null) {
            ((TextView) mediumFontSizeSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }
        bigFontSizeSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (bigFontSizeSpinner.getSelectedView() != null) {
            ((TextView) bigFontSizeSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }
        languageSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (languageSpinner.getSelectedView() != null) {
            ((TextView) languageSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }
        profileSpinner.setBackgroundColor(profiles.get(currentProfile).colorButton);
        if (profileSpinner.getSelectedView() != null) {
            ((TextView) profileSpinner.getSelectedView()).setTextColor(profiles.get(currentProfile).colorText);
        }

        boldCheckBox.setTextColor(profiles.get(currentProfile).colorText);
        italicCheckBox.setTextColor(profiles.get(currentProfile).colorText);
        adblockSwitch.setTextColor(profiles.get(currentProfile).colorText);
    }

    /**
     * Ustawia wielkość czcionek wszystkich elementów interfesju graficznego.
     */
    private void setFontSizes() {
        titleTextView.setTextSize(profiles.get(currentProfile).bigFontSize);
        colorTextButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        colorIconButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        colorButtonButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        colorBackgroundButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        fontTextView.setTextSize(profiles.get(currentProfile).mediumFontSize);
        smallFontTextView.setTextSize(profiles.get(currentProfile).smallFontSize);
        mediumFontTextView.setTextSize(profiles.get(currentProfile).mediumFontSize);
        bigFontTextView.setTextSize(profiles.get(currentProfile).bigFontSize);
        boldCheckBox.setTextSize(profiles.get(currentProfile).smallFontSize);
        italicCheckBox.setTextSize(profiles.get(currentProfile).smallFontSize);
        languageTextView.setTextSize(profiles.get(currentProfile).mediumFontSize);
        profileTextView.setTextSize(profiles.get(currentProfile).mediumFontSize);
        addNewProfileButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        deleteCurrentProfileButton.setTextSize(profiles.get(currentProfile).mediumFontSize);
        adblockSwitch.setTextSize(profiles.get(currentProfile).smallFontSize);

        if (fontSpinner.getSelectedView() != null) {
            ((TextView) fontSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }

        if (smallFontSizeSpinner.getSelectedView() != null) {
            ((TextView) smallFontSizeSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }

        if (mediumFontSizeSpinner.getSelectedView() != null) {
            ((TextView) mediumFontSizeSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }

        if (bigFontSizeSpinner.getSelectedView() != null) {
            ((TextView) bigFontSizeSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }

        if (languageSpinner.getSelectedView() != null) {
            ((TextView) languageSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }

        if (profileSpinner.getSelectedView() != null) {
            ((TextView) profileSpinner.getSelectedView()).setTextSize(profiles.get(currentProfile).smallFontSize);
        }
    }

    /**
     * Tworzy obiekt czcionki klasy Typeface na podstawie ustawień profilu i go zwraca
     * @return utworzony obiekt typeface
     */
    public static Typeface generateTypeface(){
        int style = Typeface.NORMAL;
        if ((profiles.get(currentProfile).italic == 1) && profiles.get(currentProfile).bold == 1){
            style = Typeface.BOLD_ITALIC;
        } else if (profiles.get(currentProfile).bold == 1){
            style = Typeface.BOLD;
        } else if (profiles.get(currentProfile).italic == 1){
            style = Typeface.ITALIC;
        }

        Typeface font = Typeface.DEFAULT;

        switch (profiles.get(currentProfile).font){
            case 1:
                font = Typeface.MONOSPACE;
                break;
            case 2:
                font = Typeface.SANS_SERIF;
                break;
            case 3:
                font = Typeface.SERIF;
                break;
            default:
                break;
        }

        return Typeface.create(font, style);
    }

    /**
     * Ustawianie czcionek do elementów interfejsu. Na podstawie zapisanych danych w profilu użytkownika
     * tworzy odpowiednią czcionkę, następnie ustawią ją na wszystkie elementy interfejsu.
     */
    private void setFonts(){
        Typeface typeface = generateTypeface();

        titleTextView.setTypeface(typeface);

        colorTextButton.setTypeface(typeface);
        colorIconButton.setTypeface(typeface);
        colorButtonButton.setTypeface(typeface);
        colorBackgroundButton.setTypeface(typeface);

        fontTextView.setTypeface(typeface);
        smallFontTextView.setTypeface(typeface);
        mediumFontTextView.setTypeface(typeface);
        bigFontTextView.setTypeface(typeface);
        boldCheckBox.setTypeface(typeface);
        italicCheckBox.setTypeface(typeface);
        languageTextView.setTypeface(typeface);
        profileTextView.setTypeface(typeface);
        addNewProfileButton.setTypeface(typeface);
        deleteCurrentProfileButton.setTypeface(typeface);
        adblockSwitch.setTypeface(typeface);

        if (fontSpinner.getSelectedView() != null) {
            ((TextView) fontSpinner.getSelectedView()).setTypeface(typeface);
        }

        if (smallFontSizeSpinner.getSelectedView() != null) {
            ((TextView) smallFontSizeSpinner.getSelectedView()).setTypeface(typeface);
        }

        if (mediumFontSizeSpinner.getSelectedView() != null) {
            ((TextView) mediumFontSizeSpinner.getSelectedView()).setTypeface(typeface);
        }

        if (bigFontSizeSpinner.getSelectedView() != null) {
            ((TextView) bigFontSizeSpinner.getSelectedView()).setTypeface(typeface);
        }

        if (languageSpinner.getSelectedView() != null) {
            ((TextView) languageSpinner.getSelectedView()).setTypeface(typeface);
        }

        if (profileSpinner.getSelectedView() != null) {
            ((TextView) profileSpinner.getSelectedView()).setTypeface(typeface);
        }
    }


    /**
     * Zmiana wartości pól tekstowych w zależności od wybranego jezyka
     */
    private void setLanguage(){
        if (profiles.get(currentProfile).language == 1){
            titleTextView.setText("Ustawienia");
            colorTextButton.setText("Kolor tekstu");
            colorIconButton.setText("Kolor ikon");
            colorButtonButton.setText("Kolor przycisków");
            colorBackgroundButton.setText("Kolor tła");
            fontTextView.setText("Czcionka");
            smallFontTextView.setText("Rozmiar małej czcionki");
            mediumFontTextView.setText("Rozmiar średniej czcionki");
            bigFontTextView.setText("Rozmiar dużej czcionki");
            boldCheckBox.setText("Pogrubiona");
            italicCheckBox.setText("Pochylona");
            languageTextView.setText("Język");
            profileTextView.setText("Profile");
            addNewProfileButton.setText("Dodaj nowy profil");
            deleteCurrentProfileButton.setText("Usuń obecny profil");
        } else {
            titleTextView.setText("Settings");
            colorTextButton.setText("Text color");
            colorIconButton.setText("Icon color");
            colorButtonButton.setText("Button color");
            colorBackgroundButton.setText("Background color");
            fontTextView.setText("Font");
            smallFontTextView.setText("Small font size");
            mediumFontTextView.setText("Medium font size");
            bigFontTextView.setText("Big font size");
            boldCheckBox.setText("Bold");
            italicCheckBox.setText("Italic");
            languageTextView.setText("Language");
            profileTextView.setText("Profiles");
            addNewProfileButton.setText("Add new profile");
            deleteCurrentProfileButton.setText("Delete profile");
        }

    }

    /**
     * Tworzenie wartości wyboru spinnerów.
     */
    void createSpinnersValues() {
        fonts = new ArrayList<>();
        fonts.add("Default");
        fonts.add("Monospace");
        fonts.add("Sans Serif");
        fonts.add("Serif");

        smallFontSizes = new ArrayList<>();
        mediumFontSizes = new ArrayList<>();
        bigFontSizes = new ArrayList<>();

        for (int i = 0; i <= 40; i++) {
            smallFontSizes.add(i);
            mediumFontSizes.add(i);
            bigFontSizes.add(i);
        }

        languages = new ArrayList<>();
        languages.add("English");
        languages.add("Polish");

        profiles_names = new ArrayList<>();
        for (int i = 0; i < profiles.size(); i++) {
            profiles_names.add("Profile " + (i + 1));
        }
    }

    /**
     * Metoda ustawiająca na przyciskach OnClickListenery z odpowiednio dla przcyisku przesłoniętymi
     * metodami odpowiedzialnymi za akcje przycisków. Wszystkie przyciski wywołują metodę
     * openColorPicker przesyłając do niej powiązany z przyciskiem kolor, metoda ta
     * otwiera menu wyboru koloru.
     */
    private void initializeButtons() {
        colorTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(v, profiles.get(currentProfile).colorText);
            }
        });

        colorIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(v, profiles.get(currentProfile).colorIcon);
            }
        });

        colorButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(v, profiles.get(currentProfile).colorButton);
            }
        });

        colorBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(v, profiles.get(currentProfile).colorBackground);
            }
        });

        addNewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profiles.add(new Profile());
                profiles_names.clear();
                for (int i = 0; i < profiles.size(); i++) {
                    profiles_names.add("Profile " + (i + 1));
                }
                profilesAdapter.notifyDataSetChanged();
                setValues();
            }
        });
        deleteCurrentProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profiles.size() > 1) {
                    profiles.remove(currentProfile);
                    currentProfile = profiles.size() - 1;
                    profiles_names.clear();
                    for (int i = 0; i < profiles.size(); i++) {
                        profiles_names.add("Profile " + (i + 1));
                    }
                    profilesAdapter.notifyDataSetChanged();
                    setValues();
                }
            }
        });
        adblockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    profiles.get(currentProfile).adblock = 1;
                } else {
                    profiles.get(currentProfile).adblock = 0;
                }
            }
        });
    }

    /**
     * Inicjalizacja adapterów spinnerów. Ustawia layouty adapterów spinnerów i same adaptery do
     * odpowiednich spinnerów.
     */
    private void initializeSpinnersAdapters(){
        ArrayAdapter<String> fontsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fonts);
        fontsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontsAdapter);

        ArrayAdapter<Integer> smallFontSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smallFontSizes);
        smallFontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallFontSizeSpinner.setAdapter(smallFontSizeAdapter);

        ArrayAdapter<Integer> mediumFontSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mediumFontSizes);
        mediumFontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediumFontSizeSpinner.setAdapter(mediumFontSizeAdapter);

        ArrayAdapter<Integer> bigFontSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bigFontSizes);
        bigFontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigFontSizeSpinner.setAdapter(bigFontSizeAdapter);


        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languagesAdapter);

        profilesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, profiles_names);
        profilesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(profilesAdapter);
    }


    /**
     * Inicjalizacja spinnerów. Ustawia akcje zmiany wartości odpowiednich parametrów do
     * których przyporządkowane są dane spinnery.
     */
    private void initializeSpinners() {

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profiles.get(currentProfile).font = position;
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        smallFontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profiles.get(currentProfile).smallFontSize = smallFontSizes.get(position);
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mediumFontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profiles.get(currentProfile).mediumFontSize = mediumFontSizes.get(position);
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bigFontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profiles.get(currentProfile).bigFontSize = bigFontSizes.get(position);
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profiles.get(currentProfile).language = position;
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentProfile = position;
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Inicjalizacja checkboxów, ustawia listenery zmiany ich stanów i akcje odpowiadające zmianom.
     */
    void initializeCheckBoxes() {
        boldCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    profiles.get(currentProfile).bold = 1;
                } else {
                    profiles.get(currentProfile).bold = 0;
                }
                refreshView();

            }
        });

        italicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    profiles.get(currentProfile).italic = 1;
                } else {
                    profiles.get(currentProfile).italic = 0;
                }
                refreshView();
            }
        });
    }


    /**
     * Ustawia interfejs wyboru ustawień według zapisanych wartości. Ustawia stan przycisków i spinnerów.
     */
    void setValues() {
        fontSpinner.setSelection(profiles.get(currentProfile).font);

        smallFontSizeSpinner.setSelection(smallFontSizes.indexOf(profiles.get(currentProfile).smallFontSize));
        mediumFontSizeSpinner.setSelection(mediumFontSizes.indexOf(profiles.get(currentProfile).mediumFontSize));
        bigFontSizeSpinner.setSelection(bigFontSizes.indexOf(profiles.get(currentProfile).bigFontSize));

        if (profiles.get(currentProfile).bold == 1) {
            boldCheckBox.setChecked(true);
        } else {
            boldCheckBox.setChecked(false);
        }

        if (profiles.get(currentProfile).italic == 1) {
            italicCheckBox.setChecked(true);
        } else {
            italicCheckBox.setChecked(false);
        }

        languageSpinner.setSelection(profiles.get(currentProfile).language);
        profileSpinner.setSelection(currentProfile);

        if (profiles.get(currentProfile).adblock == 1){
            adblockSwitch.setChecked(true);
        } else {
            adblockSwitch.setChecked(false);
        }
    }

    /**
     * Metoda służąca do wybierania koloru. Tworzy obiekt klasy AmbilWarnaDialog,
     * odpowiedzialny za wywołanie menu wyboru koloru,
     * ustawia na nim Listenera nasłuchującego akcji.
     * Na podstawie widoku który wywołał metodę, czyli przycisku,
     * ustawiany jest wybrany w menu kolor do pola odpowiedzialnego za dany kolor.
     * Po wyborze koloru wywoływana jest metoda odpowiedzialna za odświeżenie kolorów.
     *
     * @param v     widok z którego wywołana została metoda, w tym przypadku widok przycisku
     * @param color kolor który ma być zmieniony
     */
    private void openColorPicker(final View v, int color) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                switch (v.getId()) {
                    case R.id.settings_colorTextButton:
                        profiles.get(currentProfile).colorText = color;
                        break;
                    case R.id.settings_colorIconButton:
                        profiles.get(currentProfile).colorIcon = color;
                        break;
                    case R.id.settings_colorButtonButton:
                        profiles.get(currentProfile).colorButton = color;
                        break;
                    case R.id.settings_colorBackgroundButton:
                        profiles.get(currentProfile).colorBackground = color;
                        break;
                }
                refreshView();
            }
        });
        colorPicker.show();
    }


}

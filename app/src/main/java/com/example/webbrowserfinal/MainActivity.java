package com.example.webbrowserfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Główne Activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * LinearLayout w którym mieszczą się karty.
     */
    LinearLayout tabsLayout;
    /**
     * LinearLayout w którym mieszczą się przyciski.
     */
    LinearLayout buttonsLayout;
    /**
     * Pasek wyszukiwania
     */
    SearchView searchView;
    /**
     * Przycisk dodający nową kartę
     */
    Button addTabButton;
    /**
     * Przycisk menu kart
     */
    Button tabsButton;
    /**
     * Przycisk powrotu z menu zakładek
     */
    Button goBackFromFavouritesButton;
    /**
     * Spinner zapisanych zakładek
     */
    NDSpinner settingsSpinner;
    /**
     * Lista przechowująca karty
     */
    ArrayList<View> tabs = new ArrayList<>();
    /**
     * Indeks aktualnej karty
     */
    int currentTab;
    /**
     * Lista zapisanych zakładek
     */
    List<String> favouritesUrls = new ArrayList<>();
    /**
     * Adapter listy menu
     */
    ArrayAdapter<String> arrayAdapter;
    /**
     * Adapter listy zakładek
     */
    ArrayAdapter<String> favouritesAdapter;
    /**
     * Lista zakładek
     */
    ListView listView;
    /**
     * RecyclerView widoku kart
     */
    RecyclerView recyclerView;
    /**
     * Adapter RecyclerView widoku kart
     */
    RecyclerViewAdapter recyclerViewAdapter;
    /**
     * Obecna karta
     */
    View currentTabView;
    /**
     * Czy nastąpiło pierwsze wybranie Spinnera menu
     */
    boolean firstSpinnerClick = true;
    /**
     * Lista blacklist przechowująca potencjalne reklamowe adresy url
     */
    public static ArrayList<String> blacklist = new ArrayList<>();
    /**
     * Wątek pobierający czarną listę adresów url z reklamami
     */
    public static Thread downloadBlackListThread;

    /**
     * Przesłonięta metoda onCreate, tworząca activity.
     * Uruchamia metodę super do której przesyła zapisany stan activity.
     * Ustawia layout activity. Przypisuje do poszczególne layouty do tabsLayout i buttonsLayout.
     * Uruchamia metody odpowiedzialne za załadowanie zapisanych ustawień, inicjalizacje przycisków,
     * odświeżenie widoku, dodanie nowej zakładki, odświeżenie widoku zakładek.
     *
     * @param savedInstanceState zapisany stan activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloadBlackListThread = new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/easylist/easylist/master/easylist/easylist_adservers.txt"); //My text file location
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String str;
                    while ((str = in.readLine()) != null) {
                        if (str.startsWith("||")){
                            str = str.replace("||", "");
                            str = str.replace("^$third-party", "");
                            str = str.replace("^", "");
                            str = str.replace("\n", "");
                            blacklist.add(str);
                        }
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        downloadBlackListThread.start();

        setContentView(R.layout.activity_main);

        tabsLayout = findViewById(R.id.tabsLayout);
        buttonsLayout = findViewById(R.id.buttonsLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(tabs, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listView = findViewById(R.id.listView);

        loadSettings();
        loadFavourites();
        initializeButtons();
        refreshView();
        addTab();
        refresh();

    }

    /**
     * Metoda zapisująca ustawienia. Używa do tego celu klas SharedPreferences oraz Editor.
     * Umieszcza w obiekcie editor w postaci intów kolory tła, przycisków, ikon i tekstu,
     * następnie zapisuje je za pomocą metody apply() działającej w tle.
     */
    private void saveSettings() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("currentProfile", SettingsActivity.currentProfile);
        editor.putInt("amountOfProfiles", SettingsActivity.profiles.size());

        for (int i = 0; i < SettingsActivity.profiles.size(); i++) {
            editor.putInt("colorBackground" + i, SettingsActivity.profiles.get(i).colorBackground);
            editor.putInt("colorButton" + i, SettingsActivity.profiles.get(i).colorButton);
            editor.putInt("colorIcon" + i, SettingsActivity.profiles.get(i).colorIcon);
            editor.putInt("colorText" + i, SettingsActivity.profiles.get(i).colorText);

            editor.putInt("font" + i, SettingsActivity.profiles.get(i).font);
            editor.putInt("smallFontSize" + i, SettingsActivity.profiles.get(i).smallFontSize);
            editor.putInt("mediumFontSize" + i, SettingsActivity.profiles.get(i).mediumFontSize);
            editor.putInt("bigFontSize" + i, SettingsActivity.profiles.get(i).bigFontSize);
            editor.putInt("bold" + i, SettingsActivity.profiles.get(i).bold);
            editor.putInt("italic" + i, SettingsActivity.profiles.get(i).italic);

            editor.putInt("language" + i, SettingsActivity.profiles.get(i).language);

            editor.putInt("adblock" + i, SettingsActivity.profiles.get(i).adblock);
        }
        editor.apply();
    }

    /**
     * Metoda wczytująca ustawienia przeglądarki. Analogicznie do metody saveSettings użyto interfejsu SharedPreferences.
     * Pobiera z obiektu sharedPref wartości, a następnie przypisuje je do pól klasy SettingsActivity.
     */
    private void loadSettings() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        SettingsActivity.currentProfile = sharedPref.getInt("currentProfile", SettingsActivity.currentProfile);
        SettingsActivity.amountOfProfiles = sharedPref.getInt("amountOfProfiles", SettingsActivity.amountOfProfiles);

        for (int i = 0; i < SettingsActivity.amountOfProfiles; i++) {
            Profile profile = new Profile();
            profile.colorBackground = sharedPref.getInt("colorBackground" + i, SettingsActivity.colorBackground);
            profile.colorButton = sharedPref.getInt("colorButton" + i, SettingsActivity.colorButton);
            profile.colorIcon = sharedPref.getInt("colorIcon" + i, SettingsActivity.colorIcon);
            profile.colorText = sharedPref.getInt("colorText" + i, SettingsActivity.colorText);

            profile.font = sharedPref.getInt("font" + i, SettingsActivity.font);
            profile.smallFontSize = sharedPref.getInt("smallFontSize" + i, SettingsActivity.smallFontSize);
            profile.mediumFontSize = sharedPref.getInt("mediumFontSize" + i, SettingsActivity.mediumFontSize);
            profile.bigFontSize = sharedPref.getInt("bigFontSize" + i, SettingsActivity.bigFontSize);
            profile.bold = sharedPref.getInt("bold" + i, SettingsActivity.bold);
            profile.italic = sharedPref.getInt("italic" + i, SettingsActivity.italic);

            profile.language = sharedPref.getInt("language" + i, SettingsActivity.language);

            profile.adblock = sharedPref.getInt("adblock" + i, SettingsActivity.adblock);

            SettingsActivity.profiles.add(profile);
        }

        if (SettingsActivity.profiles.size() == 1){
            Profile profile1 = new Profile();
            profile1.language = 1;
            profile1.font = 1;
            profile1.bold = 1;
            profile1.italic = 1;
            profile1.colorText = Color.WHITE;
            profile1.colorButton = Color.BLACK;
            profile1.colorIcon = Color.WHITE;
            profile1.colorBackground = Color.BLACK;
            SettingsActivity.profiles.add(profile1);
            Profile profile2 = new Profile();
            profile2.font = 2;
            profile2.bold = 1;
            profile2.italic = 1;
            profile1.smallFontSize = 20;
            profile2.mediumFontSize = 30;
            profile2.bigFontSize = 40;
            SettingsActivity.profiles.add(profile2);
            SettingsActivity.amountOfProfiles = 3;
        }
    }


    /**
     * Zapisywanie zakłądek, metoda wywoływana po każdej modyfikacji listy zakładek.
     */
    private void saveFavourites() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> urlsSet = new HashSet<>();
        urlsSet.addAll(favouritesUrls);
        editor.putStringSet("favourites", urlsSet);
        editor.apply();
    }

    /**
     * Wczytuwanie zakładek, metoda wywoływana przy uruchomieniu aplikacji.
     */
    private void loadFavourites() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        Set<String> urlsSet = sharedPref.getStringSet("favourites", null);
        if (urlsSet != null) {
            favouritesUrls.addAll(urlsSet);
        }
    }

    /**
     * Metoda odświeżająca widok. Ustawia kolory komponentów.
     */
    private void refreshView() {
        Profile profile = SettingsActivity.profiles.get(SettingsActivity.currentProfile);

        tabsButton.setTextColor(profile.colorText);
        addTabButton.setTextColor(profile.colorText);

        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}};

        int[] colorsIcon = new int[]{profile.colorIcon};
        tabsButton.setCompoundDrawableTintList(new ColorStateList(states, colorsIcon));
        addTabButton.setCompoundDrawableTintList(new ColorStateList(states, colorsIcon));


        int[] colorButton = new int[]{profile.colorButton};
        tabsButton.setBackgroundTintList(new ColorStateList(states, colorButton));
        addTabButton.setBackgroundTintList(new ColorStateList(states, colorButton));

        buttonsLayout.setBackgroundColor(profile.colorBackground);
        tabsLayout.setBackgroundColor(profile.colorBackground);
        recyclerView.setBackgroundColor(profile.colorBackground);

        refresh();

    }

    /**
     * Inicjalizuje przyciski oraz ustawia im OnClickListenera odpowiadającego za obsługę kliknięcia,
     * z przesłoniętą metodą onClick.
     * Ustawia także akcje związane ze spinnerem obsługi zakładek.
     */
    private void initializeButtons() {
        searchView = findViewById(R.id.searchView);
        addTabButton = findViewById(R.id.addTabButton);
        settingsSpinner = findViewById(R.id.settingsSpinner);
        tabsButton = findViewById(R.id.tabsButton);
        goBackFromFavouritesButton = findViewById(R.id.goBackFromFavouritesButton);


        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WebView webView = currentTabView.findViewById(R.id.webView);
                webView.loadUrl(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        addTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tab = createTabView();
                tabs.add(tab);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });


        tabsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabsLayout.getVisibility() == View.VISIBLE) {
                    switchToRecyclerView();
                } else {
                    switchToTabs(currentTabView);
                }


            }
        });


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingsSpinner.setAdapter(arrayAdapter);

        favouritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favouritesUrls);
        listView.setAdapter(favouritesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchToTabs(currentTabView);
                WebView webView = tabs.get(currentTab).findViewById(R.id.webView);
                webView.loadUrl(parent.getAdapter().getItem(position).toString());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                favouritesUrls.remove(parent.getAdapter().getItem(position).toString());
                favouritesAdapter.notifyDataSetChanged();
                saveFavourites();
                return false;
            }
        });


        settingsSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    refreshFavourites();
                }
                return false;
            }
        });


        settingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstSpinnerClick) {
                    firstSpinnerClick = false;
                } else {
                    String url = ((MyWebView) currentTabView.findViewById(R.id.webView)).getUrl();

                    if (position == 0 && tabsLayout.getVisibility() == View.VISIBLE) {
                        if (favouritesUrls.contains(url)) {
                            favouritesUrls.remove(url);
                        } else {
                            favouritesUrls.add(url);
                        }
                        saveFavourites();

                    } else if (position == 1) {
                        switchToFavourites();
                    } else if (position == 2) {
                        switchToSettings();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        goBackFromFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTabs(currentTabView);
            }
        });

    }

    /**
     * Przełącza na widok wyboru kart przeglądarki
     */
    void switchToRecyclerView() {
        if (currentTabView.getParent() != null) {
            ((ViewGroup) currentTabView.getParent()).removeView(currentTabView);
        }

        recyclerViewAdapter.notifyDataSetChanged();

        tabsLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        searchView.setVisibility(View.GONE);
        addTabButton.setVisibility(View.VISIBLE);

    }

    /**
     * Przełącza na widok obecnej karty
     *
     * @param tab karta na którą ma przełączyć
     */
    void switchToTabs(View tab) {

        if (tab.getParent() != null) {
            ((ViewGroup) tab.getParent()).removeView(tab);
        }

        tabsLayout.addView(tab);
        currentTabView = tab;

        goBackFromFavouritesButton.setVisibility(View.GONE);
        tabsLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        searchView.setVisibility(View.VISIBLE);
        addTabButton.setVisibility(View.GONE);
        tabsButton.setVisibility(View.VISIBLE);
        settingsSpinner.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
    }

    /**
     * Przełącza do widoku zakładek/ulubionych
     */
    void switchToFavourites() {
        searchView.setVisibility(View.GONE);
        addTabButton.setVisibility(View.GONE);
        tabsLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tabsButton.setVisibility(View.GONE);
        settingsSpinner.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        goBackFromFavouritesButton.setVisibility(View.VISIBLE);


    }

    /**
     * Przełącza do aktywności ustawień
     */
    void switchToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    /**
     * Przesłonięta metoda Activity. Utuchamiania przy powrocie do aktywności.
     * Odświeża widok aktywności i zapisuje ustawienia przeglądarki
     */
    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
        saveSettings();
    }

    private void refreshFavourites() {
        String url = ((MyWebView) tabs.get(currentTab).findViewById(R.id.webView)).getUrl();
        arrayAdapter.clear();
        if (!favouritesUrls.contains(url)) {
            if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).language == 1) {
                arrayAdapter.add("Dodaj do ulubionych");
            } else {
                arrayAdapter.add("Add to favourites");
            }
        } else {
            if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).language == 1) {
                arrayAdapter.add("Usuń z ulubionych");
            } else {
                arrayAdapter.add("Remove from favourites");
            }
        }
        if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).language == 1) {
            arrayAdapter.add("Ulubione");
            arrayAdapter.add("Ustawienia");
        } else {
            arrayAdapter.add("Favourites");
            arrayAdapter.add("Settings");
        }
    }


    /**
     * Metoda dodająca nową zakładkę. Tworzy obiekt zakładki za pomocą metody createTabView,
     * dodaje go do listy zakładek, dodaje do tabsLayout, oraz ustawia aktualny indeks na ostatni.
     */
    private void addTab() {
        View tab = createTabView();
        tabs.add(tab);
        tabsLayout.addView(tab);
        currentTabView = tab;
    }


    /**
     * Metoda odświeżająca zakładki. Ustawia im nazwy na podstawie kolejności i wątku w którym działają.
     */
    private void refresh() {
        for (int i = 0; i < tabs.size(); i++) {
            TextView tabTextView = tabs.get(i).findViewById(R.id.tabTextView);
            MyWebView myWebView = tabs.get(i).findViewById(R.id.webView);
            Profile profile = SettingsActivity.profiles.get(SettingsActivity.currentProfile);
            tabTextView.setTextColor(profile.colorText);
            tabTextView.setTextSize(profile.smallFontSize);
            tabTextView.setTypeface(SettingsActivity.generateTypeface());
            if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).language == 1) {
                tabTextView.setText(String.format(getResources().getString(R.string.tab_name_polish), i + 1, myWebView.getThreadName()));
            } else {
                tabTextView.setText(String.format(getResources().getString(R.string.tab_name_english), i + 1, myWebView.getThreadName()));
            }
            refreshFavourites();
        }
    }

    /**
     * Metoda tworząca widok zakładki. Tworzy widok za na podstawie layoutu zakładki.
     * Definiuje i inicjalizuje widgety zakładki, to jest MyWebView i SearchView.
     * Ustawia OnQueryListener na obiekcie serachView, z przesłoniętą metodą onQueryTextSubmit,
     * uruchamianą gdy użytkownik zatwierdzi wyszukiwanie, która steruje obiektem webView.
     *
     * @return widok zakładki
     */
    private View createTabView() {
        final View customTabView = LayoutInflater.from(this).inflate(R.layout.tab, null);
        final MyWebView webView = customTabView.findViewById(R.id.webView);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.webView && event.getAction() == MotionEvent.ACTION_UP) {

                    if (recyclerView.getVisibility() == View.VISIBLE) {
                        switchToTabs(customTabView);
                        return true;
                    }
                }

                return false;
            }
        });


        return customTabView;
    }


}

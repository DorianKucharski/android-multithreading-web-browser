package com.example.webbrowserfinal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerViewAdapter widoku RecyclerView odpowiedzialnego za wyświetlanie otwartych kart.
 * Rozszerza klasę RecyclerView.Adapter
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    /**
     * Lista widoków otwartych kart
     */
    private ArrayList<View> views;
    /**
     * Kontekst aplikacji
     */
    private Context context;

    /**
     * Konstruktor. Przyjmuje listę widoków kart przeglądarki i kontekst aplikacji
     * @param views
     * @param context
     */
    public RecyclerViewAdapter(ArrayList<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    /**
     * Tworzenie widoku otwartej karty. Używa niestadardowego layoutu recyclerview_item który to
     * zawiera w sobie layout otwartej karty przeglądarki oraz pasek z jej tytułem i przyciskiem do
     * jej zamknięcia
     * @param parent rodzic widoku o klasie ViewGroup
     * @param viewType rodzaj widoku, nieużywane
     * @return viewHolder elementu recyclerView
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Wiązanie viewHolderów z adapterem recyclerView. Ustawia element ViewHolder na podstawie viewHoldera
     * przesłanego jako parametr i jego pozycji w liście recyclerViewAdaptera, na tej podstawie
     * wiąże dane widoków viewHolder z danami z listy adaptera. Ustawia przycisk usuwania karty,
     * napis określający nazwę strony. Ustawia także wysokość karty w widoku przeglądania kart.
     * @param holder viewHolder elementu adaptera recyclerView
     * @param position pozycja widoku na liście widoków adaptera RecyclerView odpowiadającego mu viewHoldera
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Profile profile = SettingsActivity.profiles.get(SettingsActivity.currentProfile);

        int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}};
        int[] colorButton = new int[]{profile.colorButton};
        int[] colorsIcon = new int[]{profile.colorIcon};

        holder.button.setBackgroundTintList(new ColorStateList(states, colorButton));
        holder.button.setCompoundDrawableTintList(new ColorStateList(states, colorsIcon));
        holder.textView.setTextColor(profile.colorText);
        holder.textView.setTextSize(profile.smallFontSize);
        holder.textView.setTypeface(SettingsActivity.generateTypeface());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                views.remove(position);
                refresh();
                notifyDataSetChanged();
            }
        });


        if(views.get(position).getParent() != null) {
            ((ViewGroup)views.get(position).getParent()).removeView(views.get(position));
        }
        holder.tabLinearLayout.addView(views.get(position));


        WebView webView = views.get(position).findViewById(R.id.webView);
        holder.textView.setText(webView.getTitle());
        refresh();

        holder.linearLayout.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels/2;

    }

    /**
     * Odświeżanie widoku karty przeglądarki. Metoda ustawia szatę graficzną karty, oraz jej numer
     * i jej wątek na pasku stanu.
     */
    private void refresh() {
        for (int i = 0; i < views.size(); i++) {
            TextView tabTextView = views.get(i).findViewById(R.id.tabTextView);
            MyWebView myWebView = views.get(i).findViewById(R.id.webView);
            Profile profile = SettingsActivity.profiles.get(SettingsActivity.currentProfile);
            tabTextView.setTextColor(profile.colorText);
            tabTextView.setTextSize(profile.smallFontSize);
            tabTextView.setTypeface(SettingsActivity.generateTypeface());

            if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).language == 1){
                tabTextView.setText(String.format(context.getResources().getString(R.string.tab_name_polish), i + 1, myWebView.getThreadName()));
            } else {
                tabTextView.setText(String.format(context.getResources().getString(R.string.tab_name_english), i + 1, myWebView.getThreadName()));
            }

        }
    }


    /**
     * Zwraca ilość elementów adaptera RecyclerView
     * @return ilość elementów
     */
    @Override
    public int getItemCount() {
        return views.size();
    }

    /**
     * Wewnętrzna klasa ViewHolder, rozszerza klasę RecyclerView.ViewHolder. Zawiera elementy
     * widoku karty przeglądarki w widoku recyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * Widok wewnętrzny elementu ViewHolder
         */
        View itemView;
        /**
         * LinearLayout główny
         */
        LinearLayout linearLayout;
        /**
         * TextView nazwy otwartej strony
         */
        TextView textView;
        /**
         * Button służacy do zamknięcia karty
         */
        Button button;
        /**
         * Linear Layout w którym umieszczona zostaje otwarta karta
         */
        LinearLayout tabLinearLayout;

        /**
         * Konstruktor. Przyjmuje widok elementu ViewHolder itemView i na jego podstawie znajduje
         * wewnętrzne elementy widoku, które przypisuje do pól klasy.
         * @param itemView widok elementu
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            linearLayout = itemView.findViewById(R.id.recyclerViewItem);
            textView = itemView.findViewById(R.id.recyclerview_item_textView);
            button = itemView.findViewById(R.id.recyclerview_item_button);
            tabLinearLayout = itemView.findViewById(R.id.recyclerview_item_tab_layout);

        }



    }
}

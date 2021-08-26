package com.example.webbrowserfinal;


import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSpinner;


/**
 * Rozszerzona klasa Spinner, w przeciwieństwie do standardowej klasy umożliwia wywołanie
 * Listenera wyboru elementu w przypadku wybrania tego samego elementu powtórnie.
 * Standardowa klasa Spinner Android nie wywołuje żadnej akcji jeśli dany element jest wybrany drugi
 * raz.
 */
public class NDSpinner extends AppCompatSpinner {

    /**
     * Konstruktor
     * @param context kontekst
     */
    public NDSpinner(Context context)
    { super(context); }

    /**
     * Konstruktor
     * @param context kontekst
     * @param attrs set atrybutów
     */
    public NDSpinner(Context context, AttributeSet attrs)
    { super(context, attrs); }

    /**
     * Konstruktor
     * @param context kontekst
     * @param attrs set atrybutów
     * @param defStyle styl
     */
    public NDSpinner(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }


    /**
     * Przesłonięta metoda ustawiająca selekcje obiektu menu selektora, wywołuuje metodę super,
     * oraz ręcznie wywołuje listener kliknięcia na obiekcie menu, który był już wcześniej wybrany
     * @param position pozycja elementu w menu spinnera
     * @param animate animacja jeśli true
     */
    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    /**
     * Przesłonięta metoda ustawiająca selekcje obiektu menu selektora, wywołuuje metodę super,
     * oraz ręcznie wywołuje listener kliknięcia na obiekcie menu, który był już wcześniej wybrany
     * @param position pozycja elementu w menu spinnera
     */
    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}
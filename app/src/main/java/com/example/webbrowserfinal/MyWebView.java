package com.example.webbrowserfinal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Klasa dziedzicząca z WebView. Widget przeglądarki odpowiadający za wyświetlanie treści stron.
 */
public class MyWebView extends WebView {
    /**
     * Konteks
     */
    Context context;
    /**
     * Detektor gestów
     */
    GestureDetector gd;
    /**
     * Klient przeglądarki
     */
    MyWebViewClient myWebViewClient;


    /**
     * Konstrutor. Odwołuje sie do konstruktora super. Inicjalizuje detektor gestów, uruchamia
     * metodę init odpowiedzialną za utworzenie klienta przeglądarki.
     *
     * @param context kontekts
     * @param attrs   atrybuty
     */
    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        gd = new GestureDetector(context, sogl);
        init();
    }


    /**
     * Metoda inicjalizuje klienta przeglądarki ustawia go na klienta widgetu przeglądarki,
     * właczą klientowi obsługę JavaScryptu, pamięci cache, oraz otwiera stronę google.pl
     */
    private void init() {
        myWebViewClient = new MyWebViewClient();
        setWebViewClient(myWebViewClient);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        loadUrl("http://google.pl");
    }

    /**
     * Metoda pobierająca nazwę wątku klienta przeglądarki
     * @return nazwa wątku
     */
    public String getThreadName() {
        return myWebViewClient.getThreadName();
    }

    /**
     * Przesłonięta metoda loadUrl odpowiadająca za otwieranie URLi. Sprawdza przesłany string
     * czy jest poprawnym adresem url sprawdzając czy posiada domenę, oraz czy posiada na początku protokół,
     * jeżeli podany string nie posiada protokołu, dopisuje do niego protokół,
     * jeżeli nie posiada domeny wyszukuje podany string w wyszukiwarce googla.
     * @param url poprawny url, url bez protokołu, lub fraza do wyszukania
     */
    @Override
    public void loadUrl(String url) {
        String[] domains = {".ac", ".ad", ".aero", ".ae", ".af", ".ag", ".ai", ".al", ".am", ".an",
                ".ao", ".aq", ".arpa", ".ar", ".asia", ".as", ".at", ".au", ".aw", ".ax", ".az",
                ".ba", ".bb", ".bd", ".be", ".bf", ".bg", ".bh", ".biz", ".bi", ".bj", ".bm", ".bn",
                ".bo", ".br", ".bs", ".bt", ".bv", ".bw", ".by", ".bz", ".cat", ".ca", ".cc", ".cd",
                ".cf", ".cg", ".ch", ".ci", ".ck", ".cl", ".cm", ".cn", ".coop", ".com", ".co",
                ".cr", ".cu", ".cv", ".cx", ".cy", ".cz", ".de", ".dj", ".dk", ".dm", ".do", ".dz",
                ".ec", ".edu", ".ee", ".eg", ".er", ".es", ".et", ".eu", ".fi", ".fj", ".fk", ".fm",
                ".fo", ".fr", ".ga", ".gb", ".gd", ".ge", ".gf", ".gg", ".gh", ".gi", ".gl", ".gm",
                ".gn", ".gov", ".gp", ".gq", ".gr", ".gs", ".gt", ".gu", ".gw", ".gy", ".hk", ".hm",
                ".hn", ".hr", ".ht", ".hu", ".id", ".ie", ".il", ".im", ".info", ".int", ".in",
                ".io", ".iq", ".ir", ".is", ".it", ".je", ".jm", ".jobs", ".jo", ".jp", ".ke",
                ".kg", ".kh", ".ki", ".km", ".kn", ".kp", ".kr", ".kw", ".ky", ".kz", ".la", ".lb",
                ".lc", ".li", ".lk", ".lr", ".ls", ".lt", ".lu", ".lv", ".ly", ".ma", ".mc", ".md",
                ".me", ".mg", ".mh", ".mil", ".mk", ".ml", ".mm", ".mn", ".mobi", ".mo", ".mp",
                ".mq", ".mr", ".ms", ".mt", ".museum", ".mu", ".mv", ".mw", ".mx", ".my", ".mz",
                ".name", ".na", ".nc", ".net", ".ne", ".nf", ".ng", ".ni", ".nl", ".no", ".np",
                ".nr", ".nu", ".nz", ".om", ".org", ".pa", ".pe", ".pf", ".pg", ".ph", ".pk", ".pl",
                ".pm", ".pn", ".pro", ".pr", ".ps", ".pt", ".pw", ".py", ".qa", ".re", ".ro", ".rs",
                ".ru", ".rw", ".sa", ".sb", ".sc", ".sd", ".se", ".sg", ".sh", ".si", ".sj", ".sk",
                ".sl", ".sm", ".sn", ".so", ".sr", ".st", ".su", ".sv", ".sy", ".sz", ".tc", ".td",
                ".tel", ".tf", ".tg", ".th", ".tj", ".tk", ".tl", ".tm", ".tn", ".to", ".tp",
                ".travel", ".tr", ".tt", ".tv", ".tw", ".tz", ".ua", ".ug", ".uk", ".um", ".us",
                ".uy", ".uz", ".va", ".vc", ".ve", ".vg", ".vi", ".vn", ".vu", ".wf", ".ws", ".xn"};

        for (String domain : domains) {
            if (url.contains(domain) || url.endsWith(domain + "/")) {
                if (url.startsWith("https://") || url.startsWith("http://")) {
                    super.loadUrl(url);
                    return;
                } else {
                    super.loadUrl("https://" + url);
                    return;
                }
            }
        }

        super.loadUrl("https://www.google.com/search?q=" + url);
    }


    /**
     * Przesłonięta metoda obsługująca zdarzenia dotyku.
     * Wykonuje metodę super, przesyłając do niej zdarzenie, oraz wykonuje metodę onTouchEvent,
     * obiektu detektora gestów i zwraca jego wynik.
     * @param event zdarzenie dotyku
     * @return true jeśli nastąpiło obsługiwane przez detektora gestów zdarzenie, jeśli nie - false
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return gd.onTouchEvent(event);
    }

    /**
     * Przesłonięta metoda obsługi przycisków. Zwraca wynik metody super, ponadto obsługuje przycisk
     * wstecz, fizyczny lub emulowany, w przypadku wciśnięcia przycisku cofa do poprzednio odwiedzanej strony,
     * jeśli taka istnieje.
     * @param keyCode
     * @param event
     * @return true jeśli zdarzenie jest obsługiwane, false - jeśli nie
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (canGoBack()) {
                    goBack();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * Obiekt klasy SimpleOnGestureListener wkomponowanej w klasę GestureDetektor.
     * Prosty detektor gestów, który służy do utworzenia głównego detektora gestów, obsługującego
     * gesty widgetu przeglądarki.
     * Analizuje gesty przesuwania palcem w lewo i w prawo, jeśli przesunięcie nastąpiło, było
     * odpowiedni długie, oraz było dłuższe w osi X niż w osi Y to następuje odpowiedznia do niego akcja.
     * Taka walidacja geometryczna jest konieczna by uniknąć nieporządanego wykonywania akcji,
     * w wypadku gestów pionowych, lub diagonalnych.
     * W przypadku przesunięcia w lewo, przeglądarka wykonuje metodę goForward(),
     * w przypadku przesunięcia w prawo - goBack(). Metody te odpowiadają za nawigacje.
     *
     */
    GestureDetector.SimpleOnGestureListener sogl = new GestureDetector.SimpleOnGestureListener() {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        public boolean onDown(MotionEvent event) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            if (canGoBack()) {
                                goBack();
                            }
                        } else {
                            if (canGoForward()) {
                                goForward();
                            }
                        }
                        result = true;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    };


}

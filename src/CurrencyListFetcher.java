import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyListFetcher {


    // lädt die Liste aller verfügbaren Währungen  als JSON Objekt ab
    public static JSONObject getCurrencies(String apiUrl) throws Exception {


        //Json Daten über Hilfsmethode laden
        return fetchJson(new URL(apiUrl));
    }


    //Wechselkurs von Währung in Währung
    public static double getExchangerate(String from, String to,String apiUrl)throws Exception {


        // Erstellt URL mit basiswährung
        String urlStr = apiUrl + from.toLowerCase() + ".json";

        JSONObject obj = fetchJson(new URL(urlStr));

        //Extrahiert das Objekt mit den Kursen für die Zielwährung
        JSONObject umrechnungsrate =obj.getJSONObject(from.toLowerCase());

        //Gibt den Kurs zur gewünschten Zielwährung zurück
        return umrechnungsrate.getDouble(to.toLowerCase());
    }


    // Hilfsmethode zum Laden und Parsen von JSON von einer URL
    private static JSONObject fetchJson(URL url) throws Exception {
        // HTTP-Verbindung öffnen
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // GET-Anfrage

        // Antwort einlesen (Zeile für Zeile)
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // Liest den kompletten Inhalt in einen StringBuilder
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close(); // Stream schließen

        // Wandelt den JSON-String in ein JSONObject um und gibt es zurück
        return new JSONObject(response.toString());
    }

}

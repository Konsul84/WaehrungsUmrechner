import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainFrame extends JFrame {
    public JComboBox comboBoxFromCurrencies;
    public JComboBox comboBoxToCurrencies;
    public JPanel mainPanel;
    private JTextField textFieldCurrValueFrom;
    private JTextField textFieldCurrValueTo;
    private JLabel fromCurrencys;
    private JLabel toCurrencys;


    Map<String,String>map =new HashMap<>();
    public MainFrame() {
        super("Währungsumrechner");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();

        try {
            JSONObject currencies = CurrencyListFetcher.getCurrencies("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json");
            Iterator<String> keys = currencies.keys();
            while (keys.hasNext()) {
                String code = keys.next();
                String name = currencies.getString(code);

                // label setzen
                comboBoxFromCurrencies.addItem(name);
                // label setzen
                comboBoxToCurrencies.addItem(name);

                // code name Paar in Map speichern
                map.put(name,code);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());

        }
        // Setzt die WährungsCOde Labels auf die Währung auch bevor eine eingabe gemacht wurde
        initializeCodeCurrencyLabels();
        comboBoxFromCurrencies.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ConvertCurrency();
            }
        });
        comboBoxToCurrencies.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ConvertCurrency();
            }
        });
        textFieldCurrValueFrom.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                ConvertCurrency();
            }

            public void removeUpdate(DocumentEvent e) {

            }

            public void changedUpdate(DocumentEvent e) {
                ConvertCurrency();
            }
        });
    }


    // Setzt beim erstellen des Frames die COdeLabels auf die richtige Währung
    private void initializeCodeCurrencyLabels() {

        String nameFrom = comboBoxFromCurrencies.getSelectedItem().toString();
        String nameTo =comboBoxToCurrencies.getSelectedItem().toString();

        //Setzt die Labels auf den Code per Hashmapabfrage

        fromCurrencys.setText(map.get(nameFrom));
        toCurrencys.setText(map.get(nameTo));
    }


    private void ConvertCurrency() {
        try{
            // Holt den namen der auf dem Dropdown steht um ihn anschliessend als Schlüssel der Hashmap abfrage zu nutzen um den "code" zu bekommen
            //mit dem dann weiter gearbeiett wird
            String fromDropdownName = comboBoxFromCurrencies.getSelectedItem().toString();
            String toDropDownName =comboBoxToCurrencies.getSelectedItem().toString();

            // Speichert den Währungscode in die variablen from und to
            String from =map.get(fromDropdownName);
            String to=map.get(toDropDownName);

            //Schreibt den Währungscode in die Labels neben den Dropdown Menus
            initializeCodeCurrencyLabels();

            double exchangeRate = CurrencyListFetcher.getExchangerate(from,to, "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" );

            double amount = Double.parseDouble(textFieldCurrValueFrom.getText());

            double result = amount * exchangeRate;

            textFieldCurrValueTo.setText(String.format("%.2f",result));

        }catch(Exception e){

        }
    }
    private String getCodeFrom(){
        try{
            // Holt den namen der auf dem Dropdown steht um ihn anschliessend als Schlüssel der Hashmap abfrage zu nutzen um den "code" zu bekommen
            //mit dem dann weiter gearbeiett wird
            String fromDropdownName = comboBoxFromCurrencies.getSelectedItem().toString();

            //Schreibt den Währungscode in die Labels neben den Dropdown Menus
            fromCurrencys.setText(map.get(fromDropdownName));

            // Gibt den Währungscode zurück
            return map.get(fromDropdownName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private String getCodeTo(){
        try{
            // Holt den namen der auf dem Dropdown steht um ihn anschliessend als Schlüssel der Hashmap abfrage zu nutzen um den "code" zu bekommen
            //mit dem dann weiter gearbeiett wird
            String toDropdownName = comboBoxToCurrencies.getSelectedItem().toString();

            //Schreibt den Währungscode in die Labels neben den Dropdown Menus
            fromCurrencys.setText(map.get(toDropdownName));

            // Gibt den Währungscode zurück
            return map.get(toDropdownName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}

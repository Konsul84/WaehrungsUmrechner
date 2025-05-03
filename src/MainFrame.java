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
import java.util.Iterator;

public class MainFrame extends JFrame {
    public JComboBox comboBoxFromCurrencies;
    public JComboBox comboBoxToCurrencies;
    public JPanel mainPanel;
    private JTextField textFieldCurrValueFrom;
    private JTextField textFieldCurrValueTo;


    public MainFrame() {
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
                comboBoxFromCurrencies.addItem(code);
                // label setzen
                comboBoxToCurrencies.addItem(code);
                // label setzen
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        comboBoxFromCurrencies.addItemListener(new ItemListener() {
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


    private void ConvertCurrency() {
        try{
            String from = comboBoxFromCurrencies.getSelectedItem().toString();
            String to = comboBoxToCurrencies.getSelectedItem().toString();
            double exchangeRate = CurrencyListFetcher.getExchangerate(from,to, "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" );

            double amount = Double.parseDouble(textFieldCurrValueFrom.getText());

            double result = amount * exchangeRate;

            textFieldCurrValueTo.setText(String.format("%.2f",result));

        }catch(Exception e){

        }
    }


}

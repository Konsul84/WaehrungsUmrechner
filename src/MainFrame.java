import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                comboBoxFromCurrencies.addItem(name);
                comboBoxToCurrencies.addItem(name);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        comboBoxFromCurrencies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConvertCurrency();
            }
        });
    }


    private void ConvertCurrency() {
        try{
            String from = comboBoxFromCurrencies.getSelectedItem().toString();
            String to = comboBoxToCurrencies.getSelectedItem().toString();
            double exchangeRate = CurrencyListFetcher.getExchangerate(from,to, "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json" );

            double amount = Double.parseDouble(textFieldCurrValueFrom.getText());

            double result = amount * exchangeRate;

            textFieldCurrValueTo.setText(String.format("%.2f",result));

        }catch(Exception e){

        }
    }


}

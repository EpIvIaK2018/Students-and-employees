package currencies;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Currencies {
    public static Currencies instance;
    private float currencyOfDollar;
    private float currencyOfEuro;
    public static Currencies getInstance() {
        if(instance == null){
            return new Currencies();
        }
        return null;
    }
    private Currencies() {

    }
    // Загрузка валют из интернета
    public void loadingCurrencies(){
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            Document document = documentBuilder.parse(String.valueOf(url));
            NodeList mainNode = document.getDocumentElement().getChildNodes();
            //String D = document.getDocumentElement().getChildNodes().item(10).getLastChild().getTextContent().toString();
            //String E = document.getDocumentElement().getChildNodes().item(11).getLastChild().getTextContent().toString();
            //System.out.println(D + " $$$$" + E);
            //Node dollars = document.getDocumentElement().getAttributeNode("Name");
            for(int i = 0; i < mainNode.getLength(); i++){
                Node temp = mainNode.item(i);
                if(temp.getChildNodes().item(1).getTextContent().equals("USD".trim())){
                    System.out.println(temp.getChildNodes().item(4).getTextContent());
                    currencyOfDollar = Float.parseFloat(temp.getChildNodes().item(4).getTextContent().replace(',', '.'));
                }
                if(temp.getChildNodes().item(1).getTextContent().equals("EUR".trim())){
                    System.out.println(temp.getChildNodes().item(4).getTextContent());
                    currencyOfEuro = Float.parseFloat(temp.getChildNodes().item(4).getTextContent().replace(',', '.'));
                }
            }


            //Node D = document.getElementById("folder31");
            //System.out.println(D.getFirstChild());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Неудача");
        }
    }

    public String getDollarsCurrency(){
        return String.valueOf(currencyOfDollar);
    }
    public String getEuroCurrency(){
        return String.valueOf(currencyOfEuro);
    }
}

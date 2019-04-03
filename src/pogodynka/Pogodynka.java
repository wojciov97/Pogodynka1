package pogodynka;

import net.miginfocom.swing.MigLayout;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Pogodynka extends JFrame{



    private static String wurl = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&mode=xml&apikey=b98d901cc8d2e7d3c14c7e071e309871";


    class MyHandler extends DefaultHandler {
        Map<String, Boolean> elements = new HashMap<String, Boolean>();

        DataEntry newentry;

        public MyHandler(DataEntry newEntry) {
            this.newentry=newEntry;
        }



        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if (elements.get("country"))
                newentry.setCountry(new String(ch,start,length));
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            elements.put(qName, true);
            if (qName.equals("city"))
                newentry.setCity(attributes.getValue("name"));
            if (qName.equals("temperature"))
                newentry.setTemperature(Float.parseFloat(attributes.getValue("value")));
            if (qName.equals("pressure"))
                newentry.setPressure(Float.parseFloat(attributes.getValue("value")));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            elements.put(qName, false);
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Pogodynka okno = new Pogodynka();
                    okno.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();}
            }
        });
    }
    private JTable WeatherTable;
    private JPanel panel1;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextField kraj;
    private JTextField miasto;
    private JTextField cinienie;
    private JTextField temperatura;
    DataEntry entry;


    public Pogodynka(){

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100,100,500,300);
    panel1 = new JPanel();
    panel1.setBorder(new EmptyBorder(5,5,5,5));
    setContentPane(panel1);
    panel1.setLayout(new MigLayout("", "[grow]", "[grow][fill][][]"));


    button1 = new JButton("Check weather");
    kraj = new JTextField();
    miasto = new JTextField();
    temperatura = new JTextField();
    cinienie = new JTextField();
    WeatherTable = new JTable();
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        entry = getNewWeatherDataFromUrl();
        miasto.setText(entry.getCity());
        kraj.setText(entry.getCountry());
        cinienie.setText(Float.toString(entry.getPressure()));
        temperatura.setText(Float.toString(entry.getTemperature()-273));
        }
    });

    panel1.add(button1,"flowx,cell 0 1,alignx left,aligny center");
    panel1.add(kraj,"cell 0 0 1,grow");

    panel1.add(miasto,"cell 1 0 1,grow");
    panel1.add(cinienie,"cell 2 0 1,grow");
    panel1.add(temperatura,"cell 3 0 1,grow");
    }

    private DataEntry getNewWeatherDataFromUrl() {
        DataEntry newEntry = new DataEntry();
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(wurl, new MyHandler(newEntry));

        } catch (Exception e) {
            newEntry.setCity("Error occured");
        }
        return newEntry;
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Mosse
 */
public class ControlClass {
    
    static String url = "http://lyrics.wikia.com/api.php";
        
    public static ArrayList<String> song(String artist){
        
        HttpClient client = new DefaultHttpClient();
        //keyword = getRandomKeyword();
        String responseBody = null;

        HttpGet request = new HttpGet(url + "?func=getSong&artist="+artist+"&fmt=xml");
        try {
            HttpResponse response = client.execute(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException ex) {
            Logger.getLogger(ControlClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        return processXML(responseBody);
        
    }
    
    private static ArrayList<String> processXML(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            doc.getDocumentElement().normalize();
            
            Element rootElement = doc.getDocumentElement();
            NodeList artistResponse = rootElement.getElementsByTagName("albums");
            Element e = (Element) artistResponse.item(0);
            NodeList songs = e.getElementsByTagName("songs");
            e = (Element) songs.item(0);
            
            NodeList l = e.getElementsByTagName("item");
            
            String text;
            
            for(int i=0; i<l.getLength(); i++){
                Element e1 = (Element) l.item(i);
                text = e1.getTextContent();
                list.add(text);
            }
            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ControlClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException e){
            list = new ArrayList<>();
        }
        
        return list;
    }
    
}

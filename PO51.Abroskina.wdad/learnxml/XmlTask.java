package learnxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lenovo on 25.10.2017.
 */
public class XmlTask {
    private final File file;
    private final Document document;
    private final String path;

    public XmlTask(String path) throws Exception {
        this.path = path;
        file = new File(this.path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(file);
        document.normalize();
    }


    public int earningsTotal(String officiantSecondName, Calendar calendar) throws TransformerException {
        int summa = 0;
        int day, month, year;
        Calendar thisCalendar = new GregorianCalendar();
        NodeList dateList = document.getElementsByTagName("date");
        NodeList orderList;
        NodeList bufList;
        int length = dateList.getLength();
        Element element;
        Element order;
        String secondName;
        for (int i = 0; i < length; i++) {
            element = (Element) dateList.item(i);
            day = Integer.parseInt(element.getAttribute("day"));
            month = Integer.parseInt(element.getAttribute("month"));
            year = Integer.parseInt(element.getAttribute("year"));
            thisCalendar.set(day, month, year);
            if (calendar.equals(thisCalendar)) {
                orderList = element.getElementsByTagName("order");
                for (int k = 0; k < orderList.getLength(); k++) {
                    order = (Element) orderList.item(i);
                    element = (Element) element.getElementsByTagName("officiant").item(0);
                    secondName = element.getAttribute("secondname");
                    if (officiantSecondName.equals(secondName)) {

                        bufList = order.getElementsByTagName("item");
                        for (int j = 0; j < bufList.getLength(); j++) {
                            element = (Element) bufList.item(j);
                            summa += Integer.parseInt(element.getAttribute("cost"));
                        }

                        if (order.getElementsByTagName("totalcost").getLength() == 0) {
                            element = document.createElement("totalcost");
                            element.setTextContent(Integer.toString(summa));
                            order.appendChild(element);
                            writeXmlFile();

                        } else if (Integer.parseInt(order.getElementsByTagName("totalcost").item(0).getTextContent()) != summa) {
                            order.getElementsByTagName("totalcost").item(0).setTextContent(Integer.toString(summa));
                            writeXmlFile();
                        }
                    }
                }
            }
        }
        return summa;
    }

    public void removeDay(Calendar calendar) throws TransformerException {
        int day, month, year;
        Calendar thisCalendar = new GregorianCalendar();
        NodeList dateList = document.getElementsByTagName("date");
        Element element;
        int length = dateList.getLength();
        for (int i=0; i<length; i++) {
            element = (Element) dateList.item(i);
            day = Integer.parseInt(element.getAttribute("day"));
            month = Integer.parseInt(element.getAttribute("month"));
            year = Integer.parseInt(element.getAttribute ("year"));
            thisCalendar.set(day, month, year);
            if (calendar.equals(thisCalendar)) {
                Element d = (Element) document.getElementsByTagName("restaurant").item(0);
                d.removeChild(element);
                writeXmlFile();
            }
        }

    }


    public void changeOfficiantName(String oldFirstName, String oldSecondName,
                                    String newFirstName, String newSecondName) throws TransformerException{
        NodeList officiantList = document.getElementsByTagName("officiant");
        Element officiant;
        int length = officiantList.getLength();
        for (int i=0; i<length; i++){
            officiant = (Element) officiantList.item(i);
            if (officiant.getAttribute("firstName").equals(oldFirstName) && officiant.getAttribute("secondName").equals(oldSecondName)) {
                officiant.setAttribute("firstName", newFirstName);
                officiant.setAttribute("secondName", newSecondName);
                writeXmlFile();
            }
        }

    }

    private void writeXmlFile() throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(path));
        t.transform(source, result);
    }



}

package learnxml;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lenovo on 25.10.2017.
 */
public class TestXmlTask {
    public static void TestXmlTask(String[] str) throws Exception {
        XmlTask xml = new XmlTask("PO51.Abroskina.wdad\\learnxml\\test2.xml");
        String oldFirstName = "Ivan";
        String oldSecondName = "Petrov";
        String newFirstName = "Petr";
        String newSecondName = "Sidorov";
        Calendar calendar = new GregorianCalendar(17, 06, 2017);
        System.out.println(xml.earningsTotal(newFirstName, calendar));
        xml.changeOfficiantName(oldFirstName, oldSecondName, newFirstName, newSecondName);
        calendar.clear();
        calendar.set(17,06,2017);
        xml.removeDay(calendar);
    }
}
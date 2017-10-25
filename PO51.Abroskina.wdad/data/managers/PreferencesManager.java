package data.managers;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class PreferencesManager {
    private static volatile PreferencesManager instance;
    private final Document document;
    private static final String PATH_XML = "PO51\\Abroskina\\wdad\\resources\\configuration\\appconfig.xml";

    private PreferencesManager() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(PATH_XML));
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
            }
        return instance;
    }

    public String getCreateregistry() {
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    public void setCreateregistry(String createregistry) {
        document.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }

    public String getRegistryaddress() {
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    public void setRegistryaddress(String registryaddress) {
        document.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }

    public String getRegistryport() {
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }
    public void setRegistryport(String registryport) {
        document.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }

    public String getPolicypath() {
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }
    public void setPolicypath(String policypath) {
        document.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }

    public String getUsecodebaseonly() {
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    public void setUsecodebaseonly(String usecodebaseonly) {
        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }

    public String getClassprovider() {
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    public void setClassprovider(String classprovider) {
        document.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }


    public void writeXml() throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(PATH_XML));
        t.transform(source, result);
    }
}


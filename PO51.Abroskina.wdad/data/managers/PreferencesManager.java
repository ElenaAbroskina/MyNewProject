package data.managers;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.PreferencesConstantManager;

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.FILE;

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
    @Deprecated
    public String getCreateregistry() {
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }
    @Deprecated
    public void setCreateregistry(String createregistry) {
        document.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }
    @Deprecated
    public String getRegistryaddress() {
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    public void setRegistryaddress(String registryaddress) {
        document.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }
    @Deprecated
    public String getRegistryport() {
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryport(String registryport) {
        document.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }
    @Deprecated
    public String getPolicypath() {
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }
    @Deprecated
    public void setPolicypath(String policypath) {
        document.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }
    @Deprecated
    public String getUsecodebaseonly() {
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    @Deprecated
    public void setUsecodebaseonly(String usecodebaseonly) {
        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }
    @Deprecated
    public String getClassprovider() {
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    @Deprecated
    public void setClassprovider(String classprovider) {
        document.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }


    public void writeXmlFile() throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(PATH_XML));
        t.transform(source, result);
    }

    public void setProperty(String key, String value) {
        document.getElementsByTagName(getTag(key)).item(0).setTextContent(value);
             }
    public String getProperty(String key) {
        return document.getElementsByTagName(getTag(key)).item(0).getTextContent();
            }
    public void setProperties(Properties prop) {
                 Enumeration e = prop.elements();
                 Enumeration keys = prop.keys();
                 while(e.hasMoreElements())
                 {
                    document.getElementsByTagName(getTag((String) keys.nextElement())).item(0).setTextContent((String) e.nextElement());
                 }
             }
      public Properties getProperties() {
                Properties prop = new Properties();
                prop.put(PreferencesConstantManager.CREATEREGISTRY, document.getElementsByTagName(getTag(PreferencesConstantManager.CREATEREGISTRY)).item(0).getTextContent());
                prop.put(PreferencesConstantManager.CLASSPROVIDER, document.getElementsByTagName(getTag(PreferencesConstantManager.CLASSPROVIDER)).item(0).getTextContent());
                prop.put(PreferencesConstantManager.POLICYPATH, document.getElementsByTagName(getTag(PreferencesConstantManager.POLICYPATH)).item(0).getTextContent());
                prop.put(PreferencesConstantManager.REGISTRYADDRESS, document.getElementsByTagName(getTag(PreferencesConstantManager.REGISTRYADDRESS)).item(0).getTextContent());
                prop.put(PreferencesConstantManager.USECODEBASEONLY, document.getElementsByTagName(getTag(PreferencesConstantManager.USECODEBASEONLY)).item(0).getTextContent());
                prop.put(PreferencesConstantManager.REGISTRYPORT, document.getElementsByTagName(getTag(PreferencesConstantManager.REGISTRYPORT)).item(0).getTextContent());

                   return prop;
             }
      public void addBindedObject(String name, String className) {
                 Element element = (Element) document.createElement("bindedobject");
                 element.setAttribute("name", name);
                 element.setAttribute("class", className);
                 document.getElementsByTagName("server").item(0).appendChild(element);
             }
      public void removeBindedObject(String name) {
                 NodeList nodeList = document.getElementsByTagName("bindedobject");
                 Element element;
                 for (int i=0; i<nodeList.getLength(); i++)
                    {
                    element = (Element) nodeList.item(i);
                    if (element.getAttribute("name").equals(name))
                        {
                    document.getElementsByTagName("server").item(0).removeChild(element);
                     }
                }
             }

              public String getExecutorName() {
                 Element el = (Element) document.getElementsByTagName("bindedobject").item(0);
                 return el.getAttribute("name");
             }

              private String getTag(String s) {
                 String[] sa = s.split("\\.");
                 return sa[sa.length - 1];
             }
 }



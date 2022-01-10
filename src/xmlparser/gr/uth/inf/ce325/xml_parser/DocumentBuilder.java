/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.*;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class DocumentBuilder extends Object{
    
    public DocumentBuilder(){
    
    }

    public String getDocumentAsString(String location){
        
        URL url;
        //IF URL
        try {
            url = new URL(location);
            try {
                url = new URL(location);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()) );

                String inputline = "", stringXml = "";
                while ((inputline = in.readLine()) != null) {
                    stringXml = stringXml + inputline;
                }
                in.close();
                return stringXml;
            } catch(IOException ex) {
              System.out.println("Error while reading or writing from URL");
            }
        } catch (MalformedURLException ex) {
            //System.out.println("Not a URL");
        }
        
        
        //IF FILE  
        try {
            File file = new File (location);
            FileReader fReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fReader);
            String inputLine;
            StringBuffer strDocument = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                strDocument.append(inputLine);
            }
            fReader.close();
            return strDocument.toString();
        }
        catch(FileNotFoundException ex) {
            System.out.println("The specified file was not found at "+ location);
            return "";
        }   
        catch(IOException ex) {
            System.out.println("IOException occured while reading from file "+location);
        }    
        return "Nothing to return..";
        
    }
    
    public Document getDocument(String location){
        String str;
        str = getDocumentAsString(location);

        if(str != null) {
            return parseDocument(str);    
        }
        
        return null;
    }
    
    public Document parseDocument(String documentStr){
        String textNode = "";
        Document doc;
        doc = new Document();
        Document docNode;
        docNode = new Document();
        Pattern nodeP = Pattern.compile("<(/?)([a-zA-Z_]+)([a-zA-Z_0-9:]*)([^>]*)(/?)>");
        Matcher nodeM = nodeP.matcher(documentStr);
        Pattern textp = Pattern.compile("([^<]*)<");
        Matcher nodeMtext = textp.matcher(documentStr);
        Node node = new Node("", "");
        Node prevNode = new Node("", "");
        
        List<Attribute> attributes = new LinkedList<Attribute>();
        List<Namespace> nameSpaces = new LinkedList<>();

        int parsedTill = 0;
        while (nodeM.find(parsedTill)) {
            parsedTill = nodeM.end();

            if (nodeM.group(1).length() > 0){
                if (prevNode.getName().equals(nodeM.group(2))) {
                    if (prevNode.getParent() == null) {
                        //eimaste stin riza teleiwse to parse
                        break;
                    }
                    //System.out.println("1.Setting " + prevNode.getParent().getName() + " as prevNode");
                    prevNode= prevNode.getParent();
                    node = prevNode;
                } else if (node.getName().equals(nodeM.group(2))) {
                    //System.out.println("1_5.Setting " + node.getParent().getName() + " as Node");
                    node= node.getParent();
                }
                continue;
            } else {
                if (!node.getName().equals(nodeM.group(2))) {
                    //System.out.println("2.Setting " + node.getName() + " as prevNode");
                    prevNode = node;
                }
                
                //ara o komvos exei paidia
            }
            
            if (nodeM.group(3).length() > 0) {
                //xrisi constructor gia setname k set textnode
                node = new Node(nodeM.group(3).replace(":", ""),textNode);
                node.setNamespace(doc.getNamespace(nodeM.group(2)));
                node.setParentNode(prevNode);
                if (prevNode.getName() != null) {
                    //System.out.println("Add as child the node : " + node.getName() + " to the prevNode: " + prevNode.getName());
                    prevNode.addChild(node);
                }
            } else {
                /* PARSING NAMESPACES */
                docNode = namespaceMatch(nodeM.group(4));
                //System.out.println(docNode.getNamespaces());
                /* ADDED NAMESPACES TO NODE */
                //aplos komvos only attributes
                if (docNode.getNamespaces().isEmpty()) {
                    node = new Node(nodeM.group(2), textNode);
                    node.setParentNode(prevNode);
                    if (prevNode.getName() != null) {
                        //System.out.println("Add as child the node : " + node.getName() + " to the prevNode: " + prevNode.getName());
                        prevNode.addChild(node);
                    }
                } else {
                    //ara vrikame rootnode
                    if (doc.getRootNode() == null) {
                        doc = docNode;
                        node = new Node(doc, nodeM.group(2), textNode);
                        node.setParentNode(null);
                        doc.setRootNode(node);
                        //System.out.println("To onoma tis rizas: " + node.getName());
                        prevNode = node;
                    } else {
                        node = new Node(nodeM.group(2), textNode);
                        node.setParentNode(prevNode);
                        if (prevNode.getName() != null) {
                            //System.out.println("Add as child the node : " + node.getName() + " to the prevNode: " + prevNode.getName());
                            prevNode.addChild(node);
                        }
                        //node pou exei namespaces kai den einai root
                        Iterator<Namespace> it = docNode.getNamespaces().listIterator();
                        while( it.hasNext() ) {
                          Namespace nM = it.next();
                          doc.addNamespace(nM);
                        }    
                    } 
                }
            }   
            
            /* PARSING ATTRIBUTES */
            attributes = parseNodeAttributes(nodeM.group(4));
            if (attributes != null){
                //den xreiazetai auto, apla xrisi constructor
                Iterator<Attribute> it = attributes.listIterator();                
                while( it.hasNext() ) {
                    Attribute nextattr = it.next();
                    node.addAttribute(nextattr);
                }
            }
            if (nodeM.group(4).length() > 0) {
                if ((nodeM.group(4).substring(nodeM.group(4).length() - 1)).equals("/")) {
                    //System.out.println("3.Setting " + node.getParent().getName() + " as Node");
                    node = node.getParent();
                }
            }
            
            if (nodeMtext.find(parsedTill) && nodeMtext.group(1).trim().length() > 0) {
                // skip last character, gettin slash "/" : self-closing node
                parsedTill = nodeMtext.end(1);
                node.setText(nodeMtext.group(1));
            }
        }

        return doc;
    }
    
    private List<Attribute> parseNodeAttributes(String str) {
        List<Attribute> result = new LinkedList<>();
        
        Pattern attrP = Pattern.compile(" ([a-zA-z_]+)=\"([\\p{Alnum}\\p{Punct}]+)\"");
        Matcher attrM = attrP.matcher(str);
        int found = 0;
        while(attrM.find() ) {
            if (attrM.group(1).length() > 0) {
                Attribute attr = new Attribute(attrM.group(1), attrM.group(2));
                result.add(attr);
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        
        return result;
      }
    
    public Document namespaceMatch(String str) {
        Pattern nmP = Pattern.compile("xmlns:([a-zA-z_]+)([a-zA-Z_0-9]*)(=\")([\\p{Alnum}\\p{Punct}]+)(\")([\\s]*)");
        Matcher nmM = nmP.matcher(str);
        Document doc;
        doc = new Document();
        Namespace namespace = null;
        List<Namespace> test;
        test = new LinkedList();
        
        while( nmM.find() ) {
            if (nmM.group(1).length() > 0) {
                namespace = new Namespace(nmM.group(1), nmM.group(4));
                doc.addNamespace(namespace);
                test = doc.getNamespaces();
                Iterator<Namespace> it = test.listIterator();
                while( it.hasNext() ) {
                  Namespace st = it.next();
                  //System.out.println(st.prefix + " " + st.uri);
                }
            }
        
        }
        return doc;
    }
    
}
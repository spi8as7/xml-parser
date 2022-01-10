/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser;

import java.util.regex.*;
import java.util.List;
import java.util.LinkedList;
import java.util.*;

public class Document extends Object{
    private Node rootNode;
    private List<Namespace> nameSpace;
    
    
    
    public Document() {
        nameSpace = new LinkedList<>();
    }
    
    public Document(Node rootNode) {
        this();
        this.rootNode = rootNode;
    }
    
    protected void addNamespace(Namespace namespace) {
        nameSpace.add(namespace);
    }
    
    public Namespace getNamespace(String prefix) {
        //search gia to prefix
        Iterator<Namespace> it = nameSpace.listIterator();                
        while( it.hasNext() ) {
            Namespace nextNode = it.next();
            if ( prefix.equals(nextNode.getPrefix()) ) {
                return nextNode;
            }
        }
        return null;
    }
    
    public List<Namespace> getNamespaces() {
        return nameSpace;
    }
    
    public Node getRootNode() {
        return rootNode;
    }
    
    public boolean namespacePrefixExists(String prefix) {
        return getNamespace(prefix) != null;
    }
    
    protected void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }
    
     public String toXMLString() {
        return this.toXMLString("");
    }
    
    public String toXMLString(String identStr) {
          
       String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
       if (identStr.length() > 0) {
           str += "\n";
       }
       str += rootNode.toXMLString(0, identStr);
       return str;
    }
    
}


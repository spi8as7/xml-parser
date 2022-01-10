/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser;

import java.util.LinkedList;
import java.util.*;

/**
 *
 * @author jim
 */
public class Node extends Object{
    private String nameNode;
    private String textNode;
    private Node parent;
    private List<Node> children;
    private List<Attribute> attributes;
    private Namespace nameSpace;
    private Document document;
    

    /***************CONSTRUCTORS*********************/
    
    public Node(){
        children = new LinkedList<>();
        attributes = new LinkedList<>();
        document =  new Document();
    }
    
    public Node(String name) {
        this();
        nameNode=name;
    }
    
    public Node(String name,String text) {
        this(name);
        textNode=text;
    }
    
    public Node(Document doc,String name, String text){
        this(name,text);
        document=doc;
    }
    
    public Node(String name,String text,Node parent) {
        this(name,text);
        this.parent=parent;
    }
    
    public Node(Document doc,String name, String text,Node parent) {
        this(name,text,parent);
        document=doc;
    }
    
    public Node(String name,String text,Node parent,List<Attribute> attrs) {
        this(name,text,parent);
        attributes=attrs;
    }
    
    public Node(Document doc,String name,String text,Node parent,List<Attribute> attrs) {
        this(name,text,parent,attrs);
        document=doc;
    }
    
    public Node(String name,String text,Node parent,List<Attribute> attrs,Namespace nm) {
        this(name,text,parent,attrs);
        nameSpace=nm;
    }
    
    public Node(Document doc,String name,String text,Node parent,List<Attribute> attrs,Namespace nm) {
        this(doc,name,text,parent,attrs);
        nameSpace=nm;
    }
    
    public Node(String name,String text,List<Attribute> attrs,Namespace nm) {
        this(name,text);
        attributes=attrs;
        nameSpace=nm;
    }
    
    public Node(Document doc,String name,String text,List<Attribute> attrs,Namespace nm) {
        this(name,text,attrs,nm);
        document=doc;
    }
    
    /**************** METHODS**************/
    
    public void addAttribute(Attribute attr) {
        attributes.add(attr);
    }
    
    public void addAttributes(int index,Attribute attr) {
        attributes.add(index,attr);
    }
    
    public void addChild(Node child){
        children.add(child);
    }
            
    public void addChild(int index,Node child){
        children.add(index,child);
    }
    
    public Attribute getAttribute(int index) {
        return attributes.get(index);
    }
    
    public List<Attribute> getAttributes() {
        return attributes;
    }
    
    public Node getChild(int index) {
        return children.get(index);
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    public Attribute getFirstAttribute() {
        return attributes.get(0);
    }
    
    public Node getFirstChild() {
        return children.get(0);
    }
    
    public String getName(){
        return nameNode;
    }
    
    public Namespace getNamespace() {
        return nameSpace;
    }
    
    public Attribute getNextAttribute() {
        return attributes.iterator().next();
    }
    
    public Node getNextChild() {
        return children.iterator().next();
    }
    
    public Node getParent() {
        return parent;
    }
    
    public String getText() {
        return textNode;
    }
    
    public void setName(String name) {
        nameNode=name;
    }
    
    public void setNamespace(Namespace n) {
        nameSpace=n;
    }
    
    public void setParentNode(Node parent) {
        this.parent=parent;
    }
    
    public void setText(String text) {
        textNode=text;
    }

    public String toXMLString() {
        return this.toXMLString(0, "");
    }
    
    public String toXMLString(int depth,String identStr){
        String str="";
        if (identStr.length() > 0) {
            for(int i=0;i < depth ; i++) {
                str = str + identStr;
            }
        }
        str = str + "<";
        /********* IF ROOT **********/ 
        if (parent == null) {
            str = str + nameNode;
            if( document.getNamespaces() != null ) {
                //System.out.println(document.getNamespaces());
                Iterator<Namespace> it = document.getNamespaces().listIterator();     
                while( it.hasNext() ) {
                    Namespace nm = it.next();
                    str = str + " " + nm.toXMLString();
                }
            }
        } else {
            if (nameSpace != null){
                str = str + nameSpace.getPrefix() + ":";
            }
            str= str + nameNode;
        }

        if(attributes != null) {
            for(Attribute attr:attributes){
                str= str + " " + attr.toXMLString();
            }
        }
        if ( (textNode == null || textNode.isEmpty() ) && ( children == null || children.isEmpty()) ) {
            str= str + "/>";
        } else {
            str = str  + ">";               
            if( textNode != null ) {
                str= str + textNode;
            } 
            if( children != null && !children.isEmpty() ) {
                Iterator<Node> it = children.listIterator();                
                while( it.hasNext() ) {
                    if (identStr.length() > 0) {
                        str += "\n";
                    }
                    Node node=it.next();
                    String strChild= node.toXMLString(depth + 1, identStr);
                    str= str + strChild;
                }
            }
            if (children != null && !children.isEmpty() && identStr.length() > 0) {
                str += "\n";
                for(int i=0;i < depth ; i++) {
                    str = str + identStr;
                }
            }
            str= str + "</" + nameNode + ">";
        }
        
        return str;
    }
}
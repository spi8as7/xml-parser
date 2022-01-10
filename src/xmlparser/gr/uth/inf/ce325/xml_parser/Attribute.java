/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser;

public class Attribute extends Object{
    private Document doc;
    private String name;
    private String value;
    private Namespace nameSpace;

    /***************CONSTRUCTORS*********************/
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public Attribute(String name, String value, Document doc) {
        this(name, value);
        this.doc = doc;
    }
    
    public Attribute(String name, String value, Namespace nm) {
        this(name, value);
        nameSpace = nm;
    }
    
    public Attribute(String name, String value, Document doc, Namespace nm) {
        this(name, value, doc);
        nameSpace = nm;
    }
    
    /**************** METHODS**************/
    //GET
    public String getName() {
        return name;
    }

    public String getValue()/*find*/ {
        return value;
    }

    public Namespace getNamespace(){
        return nameSpace;
    }
    
    //SET
    public void setName( String name ) {
        this.name = name;  
    }

    public void setValue( String value ) {
        this.value = value;
    }
    
    public void setNamespace(Namespace nm){
        this.nameSpace = nm;
    }

    public String toXMLString() {
        return  getName() + "=" + "\"" + getValue() + "\"";
    }
}
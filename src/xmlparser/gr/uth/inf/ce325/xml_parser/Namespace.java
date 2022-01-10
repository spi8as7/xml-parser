/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser;

public class Namespace extends Object{
    String prefix;
    String uri;
    
    public Namespace(String prefix, String uri){
        this.prefix = prefix;
        this.uri = uri;
    }
    
    public String getPrefix(){
        return prefix;
    }
    
    public String getURI(){
        return uri;
    }
    
    public String toXMLString(){
        //System.out.println(getPrefix() + "AEK " + getURI());
        return "xmlns" +  ":" + getPrefix() + "=" + "\"" + getURI() + "\"";
    }
}
/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

import java.util.Iterator;
import xmlparser.gr.uth.inf.ce325.xml_parser.*;

public class XMLParserTest {

  public static void main(String args[]) {
    DocumentBuilder docBuilder = new DocumentBuilder( ); //line 1
    Document doc = new Document();
    doc = docBuilder.getDocument( "nba-statistics.xml" ); //line 2
    Iterator<Namespace> it = doc.getNamespaces().listIterator();
    while( it.hasNext() ) {
        Namespace st = it.next();
        System.out.println("STIN main " + st.getPrefix() + " " + st.getURI());
    }
   // if (doc.getRootNode().getFirstChild().getName() != null) {
    //    System.out.println("exei prwto paidi to: " + doc.getRootNode().getFirstChild().getName());
    //}
    
    System.out.println( doc.toXMLString("  ") );                //line 3
  }
}
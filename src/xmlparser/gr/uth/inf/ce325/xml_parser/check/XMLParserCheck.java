/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOY      AEM : 1608      */
/*                                                  */
/****************************************************/

package xmlparser.gr.uth.inf.ce325.xml_parser.check;

import xmlparser.gr.uth.inf.ce325.xml_parser.*;
import java.util.*;

/**
 * This class is a checking class for your XML API.
 */
 
public class XMLParserCheck {

	private int verbocity = 0;
	/**
	 * XMLParserCheck constructor. Verbocity level is 1.
	 */
	public XMLParserCheck() { verbocity = 1; }
	
	/**
	 * XMLParserCheck constructor
	 * @param v the verbocity level, 0 or 1 as following.
	 * <ul>
	 * <li>0 : show only errors </li>
	 * <li>1 : show errors and information about the nodes that are checked. </li>
	 * </ul>
	 */
	public XMLParserCheck(int v) { verbocity = v; }
	
	/**
	 * Constructs a new node from 'name', 'text', 'nm' and 'attrs' parameters and checkes whether the 
	 * constructed node is equal with the 'node' parameter.
	 * @param node the Node object to compare against.
	 * @param name the name of the constructed node.
	 * @param text the text of the constructed node.
	 * @param nm the Namespace object that the Node point to (null if no Namespace exists).
	 * @param attrs the list of attributes of the Node
	 */ 
	public void checkNode(Node node, String name, String text, Namespace nm, List<Attribute> attrs) {
		if( verbocity > 0 )
			System.out.println("######## Checking Node \""+node.getName()+"\" ######################");
		Node _node = new Node(name, text, attrs, nm);
		compareNodes(node, _node);
		if(verbocity > 1 )
			System.out.println("######## Checked Node \""+node.getName()+"\" ######################");
	}	
	
	/** 
	 * Compares two {@link Namespace} objects and returns true if Namespace fields are equal.
	 * @param nm1 the first Namespace object.
	 * @param nm2 the second Namespace object.
	 */
	public boolean compareNamespaces(Namespace nm1, Namespace nm2) {
		
		if( nm1.getPrefix().equals(nm2.getPrefix()) && nm1.getURI().equals(nm2.getURI()) ) {
			if(verbocity > 1) 
				System.out.println("Namespace "+nm1.getPrefix()+" equals "+nm2.getPrefix() );
			return true;
		}
		System.out.println("\nNamespace ERROR: Namespace "+nm1.getPrefix()+" NOT equal with "+nm2.getPrefix()+"\n" );
		return false;
	}
	
	/** 
	 * Compares two {@link Attribute} objects and returns true if Attribute fields are equal.
	 * @param attr1 the first Attribute object.
	 * @param attr2 the second Attribute object.
	 */
	public boolean compareAttributes(Attribute attr1, Attribute attr2) {
		if( attr1.getNamespace().equals(attr2.getNamespace()) && 
				attr1.getName().equals(attr2.getName()) &&
				attr1.getValue().equals(attr2.getValue()) ) {
			if( verbocity > 1 ) 
				System.out.println("Attribute "+attr1.getName()+" equals "+attr2.getName() );
			return true;
		}
		System.out.println("\nAttribute ERROR: Attribute "+attr1.getName()+" NOT equal with "+attr2.getName()+"\n" );
		return false;
	}
		
	/** 
	 * Compares two {@link Node} objects and returns true if Node fields are equal.
	 * It recursively checks attributes and Namespace. If no attributes or Namespace 
	 * is defined in any of the two objects this check is bypassed.
	 * @param n1 the first Node object.
	 * @param n2 the second Node object.
	 */
	public boolean compareNodes(Node n1, Node n2) {
		if( n1.getName().equals(n2.getName()) && 
				n1.getText().equals(n2.getText())  ) {
				
			List<Attribute> l1 = n1.getAttributes();
			List<Attribute> l2 = n1.getAttributes();
			if( (l1 == null && l2 != null) || (l1!= null && l2 == null) ) {
				System.out.println("\nNode ERROR (Attribute mathing failure - 1): Node "+n1.getName()+" NOT equal with "+n2.getName() +"\n");
				return false;
			}
			if( l1!=null && l2!=null ) {
				Iterator<Attribute> it1 = l1.iterator();
				Iterator<Attribute> it2 = l2.iterator();
				
				while( it1.hasNext() && it2.hasNext() ) {
					Attribute attr1 = it1.next();
					Attribute attr2 = it2.next();
					if( attr1!=null && attr2!=null && !attr1.equals(attr2) ) {
						System.out.println("\nNode ERROR (Attribute mathing failure - 2): Node "+n1.getName()+" NOT equal with "+n2.getName()+"\n" );
						return false;
					}
				}
				if( it1.hasNext() || it2.hasNext() ) {
					System.out.println("\nNode ERROR (Attribute mathing failure - 3): Node "+n1.getName()+" NOT equal with "+n2.getName()+"\n" );
					return false;
				}
			}
			Namespace nm1 = n1.getNamespace();
			Namespace nm2 = n2.getNamespace();
			if( nm1!=null && nm2!=null && !nm1.equals(nm2) ) {
				System.out.println("\nNode ERROR (Namespace failure): Node "+n1.getName()+" NOT equal with "+n2.getName() +"\n");
				return false;
			}
			return true;
		}
		if( !n1.getName().equals(n2.getName()) ) {
			System.out.println("\nNode ERROR (Node name mismatch): Node "+n1.getName()+" NOT equal with "+n2.getName()+".\n" );
		}
		if( !n1.getText().equals(n2.getText()) ) {
			System.out.println("\nNode ERROR (Node text mismatch): Node "+n1.getName()+" NOT equal with "+n2.getName()+"\n" );
		}
		return false;
	}
	
}
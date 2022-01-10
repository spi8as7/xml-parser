/****************************************************/
/*                     TEAM                         */
/*                                                  */
/*      DIMITRIOS GREASIDIS         AEM : 1624      */
/*                                                  */
/*      STEFANOS PAPANASTASIOU      AEM : 1608      */
/*                                                  */
/****************************************************/

import xmlparser.gr.uth.inf.ce325.xml_parser.*;
import xmlparser.gr.uth.inf.ce325.xml_parser.check.*;
import java.util.*;
 
public class XMLParserTest01 {
 
	public static void main(String args[]) {
		DocumentBuilder docBuilder = new DocumentBuilder( );
		Document doc = docBuilder.getDocument( "top-stories.xml" );
		//System.out.println( doc.toString() );            
		
		/* Print Namespaces */
                /*System.out.println("############# Namespaces ############");
                List<Namespace> nms = doc.getNamespaces();       
                Iterator<Namespace> nmIt = nms.listIterator();                
                while( nmIt.hasNext() ) {
                    Namespace nextNode = nmIt.next();
                    System.out.println("xmlns:" + nextNode.getPrefix() + " = " + nextNode.getURI());
                }*/
                
		List<Namespace> nms = doc.getNamespaces();
		ListIterator<Namespace> nmIt = nms.listIterator();
		System.out.println("############# Namespaces ############");
		while(nmIt.hasNext()) {
			System.out.println( nmIt.next().toXMLString() );
		}
		
		Node root = doc.getRootNode();
		XMLParserCheck xmlp = null;
		if( args.length > 1 && !args[1].isEmpty() ) {
			xmlp = new XMLParserCheck(Integer.parseInt(args[1]));
		}
		else {
			xmlp = new XMLParserCheck();
		}
		
		List<Attribute> attrs = new LinkedList<Attribute>();
		attrs.add( new Attribute("version", "2.0") );
		xmlp.checkNode(root, "rss", "", null, attrs );
		
		List<Node> rootChildren = root.getChildren();
		Iterator<Node> it = rootChildren.iterator();
		Iterator<Node> itt = null;
		Node subRootNode = it.next();
		xmlp.checkNode(subRootNode, "channel", "", null, null);
		List<Node> mainNodes = subRootNode.getChildren();
		it = mainNodes.iterator();
		xmlp.checkNode(it.next(), "title", "BBC News - Home", null , null);
		xmlp.checkNode(it.next(), "link", "http://www.bbc.co.uk/news/#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa", null, null);
		xmlp.checkNode(it.next(), "description", "The latest stories from the Home section of the BBC News web site.", null, null );
		it.next(); //language
		xmlp.checkNode(it.next(), "lastBuildDate", "Tue, 03 Mar 2015 10:09:06 GMT", null, null );
		xmlp.checkNode(it.next(), "copyright", "Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.", null, null );
		xmlp.checkNode(it.next(), "image", "", null, null);
		xmlp.checkNode(it.next(), "ttl", "5", null, null);
		attrs.clear();
		attrs.add( new Attribute("href", "http://feeds.bbci.co.uk/news/rss.xml") );
		attrs.add( new Attribute("rel", "self") );
		attrs.add( new Attribute("type", "application/rss+xml") );
		xmlp.checkNode(it.next(), "link", "", doc.getNamespace("atom"), attrs );
		
		List<Node> nodes = it.next().getChildren();
		itt = nodes.iterator();
		xmlp.checkNode( itt.next(), "title", "Body parts found in Becky search", null, null);
		xmlp.checkNode( itt.next(), "description", "Police investigating the disappearance of Bristol teenager Becky Watts find body parts.", null, null );
		xmlp.checkNode( itt.next(), "link", "http://www.bbc.co.uk/news/uk-england-31709056#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa", null, null );
		
		attrs.clear();
		attrs.add( new Attribute("isPermaLink", "false") );
		xmlp.checkNode( itt.next(), "guid", "http://www.bbc.co.uk/news/uk-england-31709056", null, attrs );
		xmlp.checkNode( itt.next(), "pubDate", "Tue, 03 Mar 2015 10:16:47 GMT", null, null);
		
		attrs.clear();
		attrs.add( new Attribute("width","66") );
		attrs.add( new Attribute("height","49") );
		attrs.add( new Attribute("url", "http://news.bbcimg.co.uk/media/images/81360000/png/_81360920_breaking_image_large-3.png") );
		xmlp.checkNode(itt.next(), "thumbnail", "", doc.getNamespace("media"), attrs);
		attrs.clear();
		attrs.add( new Attribute("width","144") );
		attrs.add( new Attribute("height","81") );
		attrs.add( new Attribute("url", "http://news.bbcimg.co.uk/media/images/81360000/png/_81360921_breaking_image_large-3.png") );
		xmlp.checkNode(itt.next(), "thumbnail", "", doc.getNamespace("media"), attrs);
		/**/
		
		if(!it.hasNext()) System.out.println("No other elements to iterate");
				
		nodes.clear();
		nodes = it.next().getChildren();
		itt = nodes.iterator();
		
		xmlp.checkNode( itt.next(), "title", "Jail threat for sex abuse 'neglect'", null, null);
		xmlp.checkNode( itt.next(), "description", "Teachers, councillors and social workers in England and Wales who fail to protect children from sexual exploitation could face up to five years in jail under proposals being unveiled by the PM.", null, null );
		xmlp.checkNode( itt.next(), "link", "http://www.bbc.co.uk/news/uk-31691061#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa", null, null );
		
		attrs.clear();
		attrs.add( new Attribute("isPermaLink", "true") );
		xmlp.checkNode( itt.next(), "guid", "http://www.bbc.co.uk/news/uk-31691061", null, attrs );
		xmlp.checkNode( itt.next(), "pubDate", "Tue, 03 Mar 2015 10:14:58 GMT", null, null);
		
		attrs.clear();
		attrs.add( new Attribute("width","66") );
		attrs.add( new Attribute("height","49") );
		attrs.add( new Attribute("url", "http://news.bbcimg.co.uk/media/images/81356000/jpg/_81356152_454599682-2.jpg") );
		xmlp.checkNode(itt.next(), "thumbnail", "", doc.getNamespace("media"), attrs);
		attrs.clear();
		attrs.add( new Attribute("width","144") );
		attrs.add( new Attribute("height","81") );
		attrs.add( new Attribute("url", "http://news.bbcimg.co.uk/media/images/81356000/jpg/_81356153_454599682-2.jpg") );
		xmlp.checkNode(itt.next(), "thumbnail", "", doc.getNamespace("media"), attrs);
		
	}
	
	
	
}
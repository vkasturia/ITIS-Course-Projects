/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package combinedwithlsteindist;

/**
 *
 * @author Vaibhav Kasturia (vbh18kas@gmail.com)
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CombinedwithLSteinDist {

  public int getLevenshteinDistance(String s, String t) {
      if (s == null || t == null) {
          throw new IllegalArgumentException("Strings must not be null");
      }


      int n = s.length(); // length of s
      int m = t.length(); // length of t

      if (n == 0) {
          return m;
      } else if (m == 0) {
          return n;
      }

      if (n > m) {
          // swap the input strings to consume less memory
          String tmp = s;
          s = t;
          t = tmp;
          n = m;
          m = t.length();
      }

      int p[] = new int[n+1]; //'previous' cost array, horizontally
      int d[] = new int[n+1]; // cost array, horizontally
      int _d[]; //placeholder to assist in swapping p and d

      // indexes into strings s and t
      int i; // iterates through s
      int j; // iterates through t

      char t_j; // jth character of t

      int cost; // cost

      for (i = 0; i<=n; i++) {
          p[i] = i;
      }

      for (j = 1; j<=m; j++) {
          t_j = t.charAt(j-1);
          d[0] = j;

          for (i=1; i<=n; i++) {
              cost = s.charAt(i-1)==t_j ? 0 : 1;
              // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
              d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
          }

          // copy current distance counts to 'previous row' distance counts
          _d = p;
          p = d;
          d = _d;
      }

      // our last action in the above loop was to switch d and p, so p now 
      // actually has the most recent cost counts
      return p[n];
  }

  
  public static void main(String argv[]) {

    try {
        
        CombinedwithLSteinDist x = new CombinedwithLSteinDist();
	
        File fXmlFile = new File("./dblptry.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
        Map<String, ArrayList<String>> dblpmap = new HashMap<String, ArrayList<String>>();  
        
	NodeList nList = doc.getElementsByTagName("result");
			
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			System.out.println("Author Name : " + eElement.getElementsByTagName("literal").item(0).getTextContent());
			System.out.println("Publication Name : " + eElement.getElementsByTagName("uri").item(0).getTextContent());
			
                        String author_name = eElement.getElementsByTagName("literal").item(0).getTextContent();
                        String publication_name=eElement.getElementsByTagName("uri").item(0).getTextContent();
                        
                        ArrayList<String> publicationlist=dblpmap.get(author_name);
                        if(publicationlist==null){
                            publicationlist = new ArrayList<String>();
                            dblpmap.put(author_name, publicationlist);
                        }
                        publicationlist.add(publication_name);
                        dblpmap.put(author_name,publicationlist);  
                        

		}
                
	}
        File fXmlFile2 = new File("/Users/vaibhav/desktop/project_final/laktry.xml");
	DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
	Document doc2 = dBuilder2.parse(fXmlFile2);

        doc2.getDocumentElement().normalize();

	System.out.println("Root element :" + doc2.getDocumentElement().getNodeName());
			
        //Map<String, ArrayList<String>> dblpmap = new HashMap<String, ArrayList<String>>();  
        Map<String, String> lakmap = new HashMap<String, String>();
        
	NodeList nList2 = doc2.getElementsByTagName("result");
			
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList2.getLength(); temp++) {

		Node nNode = nList2.item(temp);
				
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			System.out.println("Author_Name_lak : " + eElement.getElementsByTagName("literal").item(0).getTextContent());
			System.out.println("Organization Name : " + eElement.getElementsByTagName("literal").item(1).getTextContent());
                        System.out.println("Num_lak_publications : " + eElement.getElementsByTagName("literal").item(2).getTextContent());
			System.out.println("Lak_publications: " + eElement.getElementsByTagName("literal").item(3).getTextContent());
                        
			String author_name_lak = eElement.getElementsByTagName("literal").item(0).getTextContent();
                        String organization_name = "Organization Name: " + eElement.getElementsByTagName("literal").item(1).getTextContent() +" ";
                        String num_lak_publications = "Num_lak_publications: " + eElement.getElementsByTagName("literal").item(2).getTextContent()+" ";
                        String lak_publications = "LAK_Publications:" + eElement.getElementsByTagName("literal").item(3).getTextContent()+" ";
                        String lakcombined = organization_name + num_lak_publications + lak_publications;
                       
                        lakmap.put(author_name_lak, lakcombined);
                        

		}
                
	}
    
        //Comparing LAK Authors and DBLP Authors and combining results for the same author
        for(Map.Entry<String, String> entry2: lakmap.entrySet()){
            for(Map.Entry<String, ArrayList<String>> entry: dblpmap.entrySet()){
                int dist = x.getLevenshteinDistance(entry2.getKey(), entry.getKey());
                if(entry2.getKey().equals(entry.getKey()) || dist==1){
                    String Author = entry2.getKey();
                    String LAKDetails = entry2.getValue();
                    ArrayList<String> dblppublicationlist = new ArrayList<String>();
                    dblppublicationlist = entry.getValue();
                    String dblppublicationliststring = "";
                    for(String s: dblppublicationlist){
                       dblppublicationliststring += s + "\t";
                    }
                    
                    System.out.println("@Author:" + Author);
                    System.out.println("@LAKDetails"+ LAKDetails);
                    System.out.println("@DBLPPublications:"+ dblppublicationliststring);
                    
                }
            }
        }
        
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}

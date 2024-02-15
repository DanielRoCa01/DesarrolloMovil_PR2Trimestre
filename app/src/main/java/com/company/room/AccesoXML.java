package com.company.room;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AccesoXML implements Acceso {

private String direccionArchico;
	
	
	public AccesoXML(String direccionArchico) {
		super();
		this.direccionArchico = direccionArchico;
	}
	@Override
	public ArrayList<Manga> leer() {
		// TODO Auto-generated method stub
		ArrayList<Manga> listaMangas=new ArrayList<Manga> ();
		try {
		listaMangas.addAll(getRoot(iniciarDoc(new File(direccionArchico)),"manga"));
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		return listaMangas;
		
	}

	@Override
	public boolean escribir(ArrayList<Manga> listaMangas) {
		// TODO Auto-generated method stub
		Document doc=iniciarDoc();
		processRoot(listaMangas,doc);
		return crearDocumento(doc);

	}
	private Document iniciarDoc(File file) {
		try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  Document doc = dBuilder.parse(file);
			  return doc;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private Document iniciarDoc() {
		try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  Document doc = dBuilder.newDocument();
			  return doc;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private ArrayList<Manga>  getRoot(Document doc,String etiqueta) {
		NodeList nList = doc.getElementsByTagName(etiqueta);
		ArrayList<Manga> reservas=new ArrayList<Manga>();
		for (int i = 0; i < nList.getLength(); i++) {
			  Node node = nList.item(i);

			  if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element eElement = (Element) node;

			    if(eElement.hasChildNodes()) {
			    	Manga res=getElemento(node);
			    	for(String s:res.toValores())
			    	{
			    		System.out.print(s);
			    	}
			    	System.out.println();
			      reservas.add(res);
			      }
			    }
			  }
		return reservas;
			}
	
	private Manga getElemento(Node node) {
		NodeList nl = node.getChildNodes();
		Element eElement = (Element) node;
		String[] valores= new String[Manga.ATRIBUTOS.length];
		
	      for(int j=0; j<Manga.ATRIBUTOS.length; j++) {
	    	  if(eElement.getElementsByTagName(Manga.ATRIBUTOS[j]).getLength()>0) {

	    		  valores[j]=eElement.getElementsByTagName(Manga.ATRIBUTOS[j]).item(0).getTextContent();
	    	  }
	    	  else {
	    		  valores[j]="";
	    	  }
	      }
	      return new Manga(valores[0],valores[1],valores[2],valores[3],valores[4],Double.parseDouble(valores[5]),Long.parseLong(valores[6]),valores[7]);
		}
	private void processRoot(ArrayList<Manga> mangas,Document doc) {
		 Element eRaiz = doc.createElement("mangas");
		  doc.appendChild(eRaiz);
		  
		  for(Manga res:mangas) {
			  processElemento(eRaiz, doc,res);
		  }
	}
	private void processElemento( Element eRaiz,Document doc,Manga manga) {
		Element eManga = doc.createElement("manga");
		  eRaiz.appendChild(eManga);
		// definimos cada uno de los elementos y le asignamos un valor
		for(int j=0; j<Manga.ATRIBUTOS.length; j++) {
			 Element enombre = doc.createElement(Manga.ATRIBUTOS[j]);
			  enombre.appendChild(doc.createTextNode(manga.toValores()[j]));
			  eManga.appendChild(enombre);
	      }
		  
	}
	
	private boolean crearDocumento(Document doc) {
		try {
		 // clases necesarias finalizar la creación del archivo XML
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = transformerFactory.newTransformer();
		  DOMSource source = new DOMSource(doc);
		 
			  StreamResult result = new StreamResult(new File(direccionArchico));
			  transformer.transform(source, result);
		  
		 
		  return true;
		} catch(NullPointerException e) {
		  e.printStackTrace();
		  return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		
	}

}

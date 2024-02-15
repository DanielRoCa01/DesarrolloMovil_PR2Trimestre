package com.company.room;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MyAnimeList {
	
	ArrayList<Manga> listaMangas=new ArrayList<Manga>();
	HashSet<String> hs=new HashSet<String>();
	public boolean leer(Acceso ac) {
		ArrayList<Manga> lm=ac.leer();
		if(lm!=null) {
		for(Manga m:lm) {
			if(!hs.contains(m.getTitulo())) {
				listaMangas.add(m);
				hs.add(m.getTitulo());
			}
		}
		return true;
		}
		return false;

	}
	public boolean escribir(Acceso ac) {
		return ac.escribir(listaMangas);
	}
	public void limpiarDatos() {
		listaMangas=new ArrayList<Manga>();
		hs=new HashSet<String>();
	}
	public ArrayList<String[]>  toValues() {
		ArrayList<String[]> lValues=new ArrayList<String[]>();
	
		for(Manga m:listaMangas) {
			lValues.add(m.toValores());
		}
		return lValues;
	}

                            
        

}

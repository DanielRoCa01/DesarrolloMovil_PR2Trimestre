package com.company.room;

import java.util.ArrayList;

public interface Acceso {

	public ArrayList<Manga> leer();
		
	public boolean escribir (ArrayList<Manga> listaMangas);
}

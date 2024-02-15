package com.company.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccesoSQL implements Acceso{

	private static  Connection con;

	
	public  void conectar() {
		  String usr="root";
		  String password="B1HGDbC5eGc-b32E1bdEDe-23DA63dg1";
		  String urlCon="jdbc:mysql://root:B1HGDbC5eGc-b32E1bdEDe-23DA63dg1@viaduct.proxy.rlwy.net:18252/myAnimeList";
//		  String password="";
//		  String urlCon="jdbc:mysql://localhost/myAnimeList";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(urlCon,usr,password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<Manga> leer() {
		ArrayList<Manga> listaMangas=new ArrayList<Manga> ();
		
		try{
			conectar();
			
			listaMangas.addAll(consultar("SELECT * FROM mangas"));
			desconectar();
			return listaMangas;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean escribir(ArrayList<Manga> listaMangas) {
		String cadena="";
		for(Manga res:listaMangas)
		{
			if(listaMangas.indexOf(res)==0) 
			{
				cadena=cadena+res.toString();
			}
			else 
			{
			cadena=cadena+","+res.toString();
			}
		}
		if(!listaMangas.isEmpty()) {
			boolean bool=true;
			bool=ejecutarSentencia("TRUNCATE TABLE mangas");
			bool=ejecutarSentencia("INSERT INTO mangas( titulo, sinopsis,  volumenes,  estatus,  generos,  score,  popularity,urlPicture) VALUES "
					+cadena.replaceAll("'NULL'", "null"));
			return bool;
		}
		return false;
		// TODO Auto-generated method stub
		
	}
	public  boolean ejecutarSentencia(String consulta) {
		conectar();
		boolean bool=false;
		try {
			Statement st=con.createStatement();
//			if(consulta.toUpperCase().startsWith("SELECT")) {
//				return consultar(consulta);
//			}
			 bool=st.execute(consulta);
			
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		desconectar();
		return bool;
	}
	
	private  void desconectar() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  ArrayList<Manga> consultar(String tabla) {
		ArrayList<Manga> reservas= new ArrayList<Manga> ();
		try {
			Statement st=con.createStatement();
			ResultSet result= st.executeQuery(tabla);
			ResultSetMetaData rsmd = result.getMetaData();
			int numCols = rsmd.getColumnCount();
			while(result.next()) {
				if(result.getString(3)!=null) {
				reservas.add(new Manga(result.getString(2),result.getString(3),
						result.getString(4),result.getString(5),
						result.getString(6),result.getDouble(7),result.getLong(8),result.getString(9)));
				}
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		desconectar();
		return reservas;
	}


}

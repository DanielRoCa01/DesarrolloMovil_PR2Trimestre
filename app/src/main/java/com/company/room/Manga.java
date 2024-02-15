package com.company.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "Manga",
		indices = {@Index(value = {"titulo"}, unique = true)})
public class Manga {
	@PrimaryKey(autoGenerate = true)
	int id;
	public final static String[]ATRIBUTOS= {"titulo","sinopsis","volumenes","estatus","generos","score","popularity","urlPicture"};

	public String titulo;
	public String sinopsis;
	public String volumenes;
	public String estatus;
	public String generos;
	public Double score;
	public Long popularity;
	public String urlPicture;

	@Ignore

	public Manga(String titulo, String sinopsis, String volumenes, String estatus, String generos, Double score,Long popularity,String urlPicture) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.volumenes = volumenes;
		this.estatus = estatus;
		this.generos = generos;
		this.score = score;
		this.popularity = popularity;
		this.urlPicture=urlPicture;
	}
	public Manga(String titulo, String sinopsis) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;

	}
	public String getTitulo() {
		return titulo;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public String getVolumenes() {
		return volumenes;
	}
	public String getEstatus() {
		return estatus;
	}
	public String getGeneros() {
		return generos;
	}
	public Double getScore() {
		return score;
	}
	public Long getPopularity() {
		return popularity;
	}
	public String[] toValores() {
		String[] valores= {titulo, sinopsis,  volumenes,  estatus,  generos,  score.toString(),  popularity.toString(),urlPicture};
		return valores;
	}
	
	
	public String getUrlPicture() {
		return urlPicture;
	}

	public String toString() {
		return "(\'"+detectarNull(titulo)+"\',\'"+detectarNull(sinopsis).replace("'","")+"\',\'"+detectarNull(volumenes)+"\',\""+detectarNull(estatus)+
				"\",\'"+detectarNull(generos)+"\',"+detectarNull(score)+","+detectarNull(popularity)+",\""+urlPicture+"\")";
	}
	private String detectarNull(String pal) {
		return pal==""||pal==null?"NULL":pal;
	}
	private Long detectarNull(Long n) {
		
		return n==null?null:n;
	}
private Double detectarNull(Double n) {
		
		return n==null?null:n;
	}
	public boolean isNull() {
		return titulo==""&&sinopsis==""&&volumenes==""
				&&estatus==""&&score.toString()==""&&popularity.toString()==""
						&&generos==""&&urlPicture=="";
	}

	public void setScore(double aDouble) {
	}
}

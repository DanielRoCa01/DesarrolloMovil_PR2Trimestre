package com.company.room;



import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Database(entities = { Manga.class }, version = 6, exportSchema = false)
public abstract class ElementosBaseDeDatos extends RoomDatabase {

    private static volatile ElementosBaseDeDatos INSTANCIA;

    static ElementosBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ElementosBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    ElementosBaseDeDatos.class, "elementos.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }

    public abstract ElementosDao obtenerElementosDao();


    //...


    @Dao
    interface ElementosDao {
        @Query("SELECT * FROM Manga")
        LiveData<List<Manga>> obtener();

        @Insert
        void insertar(Manga elemento) throws Exception;

        @Update
        void actualizar(Manga elemento);

        @Delete
        void eliminar(Manga elemento);
        @Query("DELETE FROM Manga")
        void deleteAll(); // Método para borrar todos los registros de la tabla

        @Query("SELECT * FROM Manga ORDER BY score DESC")
        LiveData<List<Manga>> masValorados();

        @Query("SELECT * FROM Manga WHERE titulo LIKE '%' || :t || '%'")
        LiveData<List<Manga>> buscar(String t);
        @Query("SELECT COUNT(*) FROM Manga")
        int contarUsuarios();
    }
}
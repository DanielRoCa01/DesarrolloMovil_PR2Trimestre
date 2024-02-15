package com.company.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ElementosRepositorio {

    Executor executor = Executors.newSingleThreadExecutor();
    ElementosBaseDeDatos.ElementosDao elementosDao;
    interface Callback {
        void cuandoFinalice(List<Manga> elementos);
    }
    ElementosRepositorio(Application application){
        elementosDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerElementosDao();
    }



    ElementosRepositorio(){
           }

    LiveData<List<Manga>> obtener(){
        return elementosDao.obtener();
    }

    void insertar(Manga elemento) throws Exception{
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    elementosDao.insertar(elemento);
                } catch (Exception e) {

                }
            }
        });
    }

    void eliminar(Manga elemento) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elementosDao.eliminar(elemento);
            }
        });
    }

    public void actualizar(Manga elemento, Double valoracion) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elemento.score = valoracion;
                elementosDao.actualizar(elemento);
            }
        });
    }
    LiveData<List<Manga>> masValorados() {
        return elementosDao.masValorados();
    }

    LiveData<List<Manga>> buscar(String t) {
        return elementosDao.buscar(t);
    }
    int contar(){return elementosDao.contarUsuarios();}
    void deleteAll(){
        elementosDao.deleteAll();
    }
}

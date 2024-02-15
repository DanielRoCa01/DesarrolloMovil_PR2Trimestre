package com.company.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ElementosViewModel extends AndroidViewModel {

    ElementosRepositorio elementosRepositorio;

    private Executor executor;

    MutableLiveData<Manga> elementoSeleccionado = new MutableLiveData<>();

    public ElementosViewModel(@NonNull Application application) {
        super(application);



        elementosRepositorio = new ElementosRepositorio(application);
        executor = Executors.newSingleThreadExecutor();
    }

    public void borrarTodosLosUsuarios() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Operaciones de base de datos
                deleteAll();
            }
        });
    }
    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    LiveData<List<Manga>> resultadoBusqueda = Transformations.switchMap(terminoBusqueda, new Function<String, LiveData<List<Manga>>>() {
        @Override
        public LiveData<List<Manga>> apply(String input) {
            return elementosRepositorio.buscar(input);
        }
    });
    LiveData<List<Manga>> obtener(){
        return elementosRepositorio.obtener();
    }

    void insertar(Manga elemento) throws Exception {
        elementosRepositorio.insertar(elemento);
    }

    void eliminar(Manga elemento){
        elementosRepositorio.eliminar(elemento);
    }

    void actualizar(Manga elemento, Double valoracion){
        elementosRepositorio.actualizar(elemento, valoracion);
    }

    void seleccionar(Manga elemento){
        elementoSeleccionado.setValue(elemento);
    }

    MutableLiveData<Manga> seleccionado(){
        return elementoSeleccionado;
    }
    LiveData<List<Manga>> masValorados(){
        return elementosRepositorio.masValorados();
    }
    LiveData<List<Manga>> buscar(){
        return resultadoBusqueda;
    }

    void establecerTerminoBusqueda(String t){
        terminoBusqueda.setValue(t);
    }
    int contar(){return elementosRepositorio.contar();}
    void deleteAll(){
        elementosRepositorio.deleteAll();
    }
}

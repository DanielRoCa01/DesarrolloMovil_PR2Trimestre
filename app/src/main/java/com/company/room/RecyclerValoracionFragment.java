package com.company.room;

import androidx.lifecycle.LiveData;

import com.company.room.RecyclerElementosFragment;

import java.util.List;

public class RecyclerValoracionFragment extends RecyclerElementosFragment {
    @Override
    LiveData<List<Elemento>> obtenerElementos() {
        return elementosViewModel.masValorados();
    }
}
package com.company.room;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.company.room.RecyclerElementosFragment;
import com.company.room.databinding.FragmentRecyclerElementosBinding;

import java.util.List;

public class RecyclerValoracionFragment extends RecyclerElementosFragment {

    @Override
    LiveData<List<Manga>> obtenerElementos() {
        return elementosViewModel.masValorados();
    }


}
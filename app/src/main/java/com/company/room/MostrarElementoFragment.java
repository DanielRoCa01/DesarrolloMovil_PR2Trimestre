package com.company.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.company.room.databinding.FragmentMostrarElementoBinding;


public class MostrarElementoFragment extends Fragment {
    private FragmentMostrarElementoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarElementoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ElementosViewModel elementosViewModel = new ViewModelProvider(requireActivity()).get(ElementosViewModel.class);

        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Manga>() {
            @Override
            public void onChanged(Manga elemento) {
                binding.nombre.setText(elemento.titulo);
                binding.descripcion.setText(elemento.sinopsis);
                binding.valoracion.setText(elemento.score.toString());
                binding.estado.setText(elemento.estatus);
                binding.volumenes.setText(elemento.volumenes);
                binding.popularity.setText(elemento.popularity.toString());
                binding.generos.setText("Generos: "+elemento.generos);

                String url = "https://example.com/image.jpg";

                // Cargar la imagen desde la URL con Glide
                Glide.with(requireContext())
                        .load(elemento.urlPicture)
                        .into(binding.imagen);

            }
        });
    }
}

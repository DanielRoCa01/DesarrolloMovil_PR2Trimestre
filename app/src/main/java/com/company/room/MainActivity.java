package com.company.room;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.company.room.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
static List<Manga> json=new ArrayList<Manga>();
    ActivityMainBinding binding;
    private ElementosViewModel elementosViewModel;
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar el clic en el botón de regreso
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        elementosViewModel = new ViewModelProvider(this).get(ElementosViewModel.class);


        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment)).getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if (destination.getId() == R.id.nuevoElementoFragment
                        || destination.getId() == R.id.mostrarElementoFragment) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    binding.bottomNavView.setVisibility(View.GONE);
                } else {
                    binding.bottomNavView.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }

                if (destination.getId() == R.id.recyclerBuscarFragment){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    binding.searchView.setVisibility(View.VISIBLE);
                    binding.searchView.setIconified(false);
                    binding.searchView.requestFocusFromTouch();
                } else {
                    binding.searchView.setVisibility(View.GONE);


                }
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                elementosViewModel.establecerTerminoBusqueda(newText);
                return false;
            }

        });
       leerJson();
    }



    @Override
    protected void onStop() {
        super.onStop();
        elementosViewModel.deleteAll();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        elementosViewModel.deleteAll();
    }

    private  void leerJson(){

        String jsonString ="";
        try {
            // Obtener un InputStream desde el archivo en assets
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("mangas.json");

            // Leer el contenido del archivo
            String fileContent = FileHelper.readFromAssets(inputStream);

            // Mostrar el contenido en un TextView
             jsonString =fileContent;

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Manga> listaMangas=new ArrayList<Manga>();
        try {
            // Crear un array JSONArray a partir del JSON string
            JSONObject jsonA = new JSONObject(jsonString);
            JSONArray jsonArray = jsonA.getJSONArray("mangas");

            // Iterar sobre cada objeto JSON en el array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Hacer lo que quieras con los valores obtenidos
                String name = jsonObject.getString("title_ov");
                String sinopsis = jsonObject.getString("synopsis")==null?"":jsonObject.getString("synopsis");


                String volumes = jsonObject.getString("volumes");
                volumes=volumes==null?"":volumes;
                String status = jsonObject.getString("status");
                status=status==null?"":status;
                String generos = jsonObject.getString("generos");
                generos=generos==null?"":generos;
                Double score = jsonObject.getDouble("score");
                score=score==null?0:score;
                Long popularity = jsonObject.getLong("popularity");
                popularity=popularity==null?0:popularity;
                String picture_url = jsonObject.getString("picture_url");
                picture_url=picture_url==null?"":picture_url;
                json.add(new Manga(name,sinopsis,volumes,status,generos,score,popularity,picture_url));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
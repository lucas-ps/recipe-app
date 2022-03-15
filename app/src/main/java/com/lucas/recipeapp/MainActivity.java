package com.lucas.recipeapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lucas.recipeapp.adapters.RecipeAdapter;
import com.lucas.recipeapp.data.ApiRequestManager;
import com.lucas.recipeapp.databinding.ActivityMainBinding;
import com.lucas.recipeapp.listeners.RandomRecipeListener;
import com.lucas.recipeapp.models.RandomRecipeAPI;
import com.lucas.recipeapp.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ProgressDialog dialog;
    ApiRequestManager manager;
    RecipeAdapter recipeAdapter;
    SearchView keywordSearchView;
    RecyclerView keywordRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Autogenerated code for tabs
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        // Code for initialising recycler view
        manager = new ApiRequestManager(this);
        manager.getRandomRecipes(randomRecipeListener);

        keywordSearchView = (SearchView) findViewById(R.id.keywordsSearchView);
        keywordRecyclerView = (RecyclerView) findViewById(R.id.keywordsRecyclerView);


        // Autogenerated code for corner menu button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {
        @Override
        public void fetchedResponse(RandomRecipeAPI response, String message) {
            keywordRecyclerView = findViewById(R.id.keywordsRecyclerView);
            keywordRecyclerView.setHasFixedSize(true);
            keywordRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            recipeAdapter = new RecipeAdapter(MainActivity.this, response.recipes);
            keywordRecyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void errorMessage(String error) {
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };
}
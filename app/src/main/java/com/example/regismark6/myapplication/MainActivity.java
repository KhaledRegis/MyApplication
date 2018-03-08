package com.example.regismark6.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    static RecyclerView recyclerView;
    static movieAdapter adapter;
    static List<movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //recycle view instance,
        movieList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchview = (SearchView)item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                MovieTask mt = new MovieTask(getApplicationContext());
                mt.execute("Search",s);

               // Toast toast2 = Toast.makeText(getApplication(), s,Toast.LENGTH_LONG);
                //toast2.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /*
    the method below will move us to movieTask class which comtains async background process.
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.get_Top_Rated:
                MovieTask mt = new MovieTask(getApplicationContext());
                mt.execute("topRated");
               // Toast toast2 = Toast.makeText(getApplication(), ""+R.id.get_Top_Rated,Toast.LENGTH_LONG);
                //toast2.show();
                break;
            case R.id.favorites:
                MovieTask mt1 = new MovieTask(getApplicationContext());
                mt1.execute("favorites");

                break;
            case R.id.upcoming:
                MovieTask mt2 = new MovieTask(getApplicationContext());
                mt2.execute("upcoming");

                break;
        }
        return true;
    }
}

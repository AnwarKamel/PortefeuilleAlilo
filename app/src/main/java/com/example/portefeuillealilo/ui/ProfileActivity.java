package com.example.portefeuillealilo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.portefeuillealilo.R;
import com.example.portefeuillealilo.adapters.PortfRecyclerAdapter;
import com.example.portefeuillealilo.data.SharedPrefManager;
import com.example.portefeuillealilo.db.DatabaseHelper;
import com.example.portefeuillealilo.model.Portefeuille;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private AppCompatActivity activity = ProfileActivity.this;
    private AppCompatTextView textViewName;
    private AppCompatTextView tvName;
    private AppCompatTextView tvBalance;
    private AppCompatTextView tvDepenses;
    private AppCompatTextView tvRevenu;
    private RecyclerView recyclerViewUsers;
    private List<Portefeuille> listUsers;
    private PortfRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initViews();
        initObjects();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        tvName = (AppCompatTextView) findViewById(R.id.tvName);
        tvBalance = (AppCompatTextView) findViewById(R.id.tvBalance);
        tvDepenses = (AppCompatTextView) findViewById(R.id.tvDepenses);
        tvRevenu = (AppCompatTextView) findViewById(R.id.tvRevenu);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }



    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new PortfRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String name = SharedPrefManager.getInstance(this).getUser().getName();
        String email = SharedPrefManager.getInstance(this).getUser().getEmail();
        String depenses = SharedPrefManager.getInstance(this).getUser().getDepenses();
        String revenu = SharedPrefManager.getInstance(this).getUser().getRevenu();

        // Toast.makeText(activity, de.toString(), Toast.LENGTH_SHORT).show();
        // String emailFromIntent = getIntent().getStringExtra("EMAIL");

        String solde = databaseHelper.getUserByEmail(email).getSolde();

      //  textViewName.setText(email+ " | "+ solde);
        tvName.setText("Hello "+name);
        tvBalance.setText(solde);
        tvDepenses.setText(depenses);
        tvRevenu.setText(revenu);



        getDataFromSQLite();
    }


    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllPortf(SharedPrefManager.getInstance(activity).getUser().getId()));

                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_profile:
                startActivity(new Intent(activity, ProfileActivity.class));
                return true;

            case R.id.action_add_transaction:
                startActivity(new Intent(activity, AddTransactionActivity.class));
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        //   intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        //   intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
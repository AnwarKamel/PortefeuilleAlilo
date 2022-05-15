package com.example.portefeuillealilo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.portefeuillealilo.R;
import com.example.portefeuillealilo.data.SharedPrefManager;
import com.example.portefeuillealilo.db.DatabaseHelper;
import com.example.portefeuillealilo.helpers.InputValidation;
import com.example.portefeuillealilo.model.Portefeuille;
import com.example.portefeuillealilo.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddTransactionActivity extends AppCompatActivity {
    private AppCompatActivity activity = AddTransactionActivity.this;

    private RadioGroup radioTypeGroup;
    private RadioButton radioButton;

    private TextInputLayout editTitle;
    private TextInputLayout editSolde;
    private TextInputEditText edtTitle;
    private TextInputEditText edtSolde;

    private AppCompatButton btnAddTrans;
    private InputValidation inputValidation;


    String title;
    String solde;
    String type;
    private DatabaseHelper databaseHelper;
    private Portefeuille port;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        inputValidation = new InputValidation(activity);

        initViews();

        btnAddTrans.setOnClickListener(v -> {


            if (!inputValidation.isInputEditTextFilled(edtTitle, editTitle, "Name Required")) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(edtSolde, editSolde, "Solde Required")) {
                return;
            }

            int selectedId = radioTypeGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            Toast.makeText(AddTransactionActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

            int user_id = SharedPrefManager.getInstance(this).getUser().getId();


            title = edtTitle.getText().toString().trim();
            solde = edtSolde.getText().toString().trim();
            type = radioButton.getText().toString().trim();

            port.setTitle(title);
            port.setUser_id(user_id);
            port.setType(type);
            port.setSolde(solde);
            SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());

            String email = sharedPrefManager.getUser().getEmail().trim();
            Double balance = Double.parseDouble( sharedPrefManager.getUser().getSolde().trim());
            Double solde_double = Double.parseDouble(solde);
            Double newwBalance;

            Double depenses = Double.parseDouble( sharedPrefManager.getUser().getDepenses().trim());
            Double newwDepenses ;

            Double revenu = Double.parseDouble( sharedPrefManager.getUser().getRevenu().trim());
            Double newwRevenu ;


            if (type.equalsIgnoreCase("Depenses")) {

                newwBalance = balance - solde_double;
                newwDepenses = depenses + solde_double;
                newwRevenu = revenu;


            }else {

                newwBalance = balance + solde_double;
                newwRevenu = revenu + solde_double;
                newwDepenses = depenses;



            }

            String newwBalance_s = Double.toString(newwBalance);
            String newwDepenses_s = Double.toString(newwDepenses);
            String newwRevenu_s = Double.toString(newwRevenu);

            String user_id_ = String.valueOf(user_id);

           if (databaseHelper.addPortefi(port)) {
               Toast.makeText(getApplicationContext(), "done",
                       Toast.LENGTH_SHORT).show();
               databaseHelper.updateBalance(newwBalance_s,   newwDepenses_s, newwRevenu_s , user_id_);
               User user = databaseHelper.getUserByEmail(email);

               startActivity(new Intent(activity, ProfileActivity.class));

               sharedPrefManager.userLogin(user);

           } else {
               Toast.makeText(getApplicationContext(), "not done",
                       Toast.LENGTH_SHORT).show();
           }




        });

        initObjects();



    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        editSolde = (TextInputLayout) findViewById(R.id.editSolde);
        editTitle = (TextInputLayout) findViewById(R.id.editTitle);
        edtTitle = (TextInputEditText) findViewById(R.id.edtTitle);
        edtSolde = (TextInputEditText) findViewById(R.id.edtSolde);
        btnAddTrans = (AppCompatButton) findViewById(R.id.btnAddTrans);
        radioTypeGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
         port = new Portefeuille();

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
        switch (id) {
            case R.id.action_profile:
                startActivity(new Intent(activity, ProfileActivity.class));
                return true;

            case R.id.action_add_transaction:
                startActivity(new Intent(activity, AddTransactionActivity.class));
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
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
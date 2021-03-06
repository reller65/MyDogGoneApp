package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.util.HashMap;


import android.util.Log;

public class CreatePetProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText petName;
    private EditText species;
    private EditText breed;
    private EditText markings;
    private EditText rabiesTag ;
    private Switch bite;
    private EditText notes;
    private Button buttonRegister;
    private String userID;

    private static String TAG = "testingMessage";

    private static final String REGISTER_URL = "https://php.radford.edu/~team04/userRegistration/petRegister.php?user_id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

        userID = "";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }

        this.petName = (EditText) findViewById(R.id.petNameTextField);
        this.species = (EditText) findViewById(R.id.speciesEditText);
        this.breed = (EditText) findViewById(R.id.breedEditText);
        this.markings = (EditText) findViewById(R.id.markingsEditText);
        this.rabiesTag = (EditText) findViewById(R.id.rabiesEditText);
        this.bite = (Switch) findViewById(R.id.biteSwitch);
        this.notes = (EditText) findViewById(R.id.notesSection);

        buttonRegister = (Button) findViewById(R.id.createPetButton);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            addPetClicked();
        }
    }

    public void addPetClicked(){
        String pName = getPetName();
        String pSpecies = getSpecies();
        String pBreed = getBreed();
        String pMarkings = getMarkings();
        String pRabies = getRabies();
        String pBite = getBite();
        String pNotes = getNotes();
        String pPhoto = getPhoto();

        register(pName, pSpecies, pBreed , pPhoto, pMarkings, pRabies, pBite, pNotes, userID);
    }

    private void register(String pName, String pSpecies, String pBreed , String pPhoto, String pMarkings,
                          String pRabies, String pBite, String pNotes, final String userID) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CreatePetProfileActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("name",params[0]);
                data.put("species",params[1]);
                data.put("breed",params[2]);
                data.put("markings",params[3]);
                data.put("rabies_tag_num",params[4]);
                data.put("bite_status",params[5]);
                data.put("notes",params[6]);
                data.put("photo",params[7]);
                data.put("user_id",params[8]);

                return  ruc.sendPostRequest(REGISTER_URL+userID,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        Log.i(TAG, "before ru.execute");
        ru.execute(pName,pSpecies,pBreed,pMarkings,pRabies,pBite,pNotes,pPhoto, userID); //Pass bite as a string
        Log.i(TAG, "ru.execute success");
    }

    private String getPetName(){
        return petName.getText().toString().trim().toLowerCase();
    }

    private String getSpecies(){
        return species.getText().toString().trim().toLowerCase();
    }

    private String getBreed(){
        return breed.getText().toString().trim().toLowerCase();
    }

    private String getMarkings(){
        return markings.getText().toString().trim().toLowerCase();
    }

    private String getRabies(){
        return rabiesTag.getText().toString().trim().toLowerCase();
    }

    private String getBite(){
        if (bite.isChecked()) {return "true";}
        else {return "false";}
    }

    private String getNotes(){
        return notes.getText().toString().trim().toLowerCase();
    }

    private String getPhoto(){return "photo";}





}

package com.nestsoft.anjidictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {
    String[] fromLanguage = {"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech",
            "Welsh","Hindi","Urdu"};

    String[] toLanguage = {"To","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech",
            "Welsh","Hindi","Urdu"};

    int languageCode,fromlanguageCode,tolanguageCode = 0;


    int REQUEST_PERMISSION_CODE = 202;

    Switch switch_btn;
    Button translate_btn;
    LinearLayout spinnerLayout;
    TextView translated_tv;
    Spinner fromspinner, tospinner;
    EditText editText_title, editText_notes;
    ImageView mic;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        editText_notes = findViewById(R.id.edit_notes);

        switch_btn = findViewById(R.id.switch_btn);
        translate_btn = findViewById(R.id.translate_btn);
        spinnerLayout = findViewById(R.id.spinnerlayout);
        translated_tv = findViewById(R.id.translatedTv);
        mic = findViewById(R.id.mic);
        fromspinner = findViewById(R.id.fromspinner);
        tospinner = findViewById(R.id.tospinner);
        progressDialog = new ProgressDialog(TranslateActivity.this);


        fromspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromlanguageCode = getLanguageCode(fromLanguage[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter fromadapter = new ArrayAdapter(this,R.layout.text_layout,fromLanguage);
        fromadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromspinner.setAdapter(fromadapter);


        tospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tolanguageCode = getLanguageCode(toLanguage[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter toadapter = new ArrayAdapter(this,R.layout.text_layout,toLanguage);
        toadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tospinner.setAdapter(toadapter);

        translate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translated_tv.setText("");

                if (editText_notes.getText().toString().isEmpty()){
                    Toast.makeText(TranslateActivity.this, "Please Enter text to translate", Toast.LENGTH_SHORT).show();
                }else {
                    translateText(fromlanguageCode,tolanguageCode,editText_notes.getText().toString());
                }
            }
        });


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to convert to Text");
                try {
                    startActivityForResult(intent,REQUEST_PERMISSION_CODE);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TranslateActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    translate_btn.setVisibility(View.VISIBLE);
                    spinnerLayout.setVisibility(View.VISIBLE);
                    translated_tv.setVisibility(View.VISIBLE);
                }
                else {
                    translate_btn.setVisibility(View.GONE);
                    spinnerLayout.setVisibility(View.GONE);
                    translated_tv.setVisibility(View.GONE);

                }
            }
        });
    }

    private void translateText(int fromlanguageCode,int tolanguageCode,String source){
        translated_tv.setText("Downloading Models......");
        progressDialog.setMessage("Downloading Language Model");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromlanguageCode)
                .setTargetLanguage(tolanguageCode)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translated_tv.setText("Translating...");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        translated_tv.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TranslateActivity.this, "failed to translate "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(TranslateActivity.this, "Failed to download model "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private int getLanguageCode(String s) {
        int languageCode = 0;
        switch (s){
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;
                break;
            case "Afrikaans":
                languageCode = FirebaseTranslateLanguage.AF;
                break;
            case "Arabic":
                languageCode = FirebaseTranslateLanguage.AR;
                break;
            case "Belarusian":
                languageCode = FirebaseTranslateLanguage.BE;
                break;
            case "Bulgarian":
                languageCode = FirebaseTranslateLanguage.EN;
                break;
            case "Bengali":
                languageCode = FirebaseTranslateLanguage.BN;
                break;
            case "Catalan":
                languageCode = FirebaseTranslateLanguage.CA;
                break;
            case "Czech":
                languageCode = FirebaseTranslateLanguage.CS;
                break;
            case "Welsh":
                languageCode = FirebaseTranslateLanguage.EN;
                break;
            case "Hindi":
                languageCode = FirebaseTranslateLanguage.HI;
                break;
            case "Urdu":
                languageCode = FirebaseTranslateLanguage.UR;
                break;
            default:
                languageCode=0;
        }
        return languageCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_PERMISSION_CODE){
            if (resultCode==RESULT_OK && data !=null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText_notes.setText(result.get(0));
            }
        }
    }
}
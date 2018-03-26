package com.ozenix.achieveme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button mFireBaseButton;
    private DatabaseReference mDatabase;

    private EditText mNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseButton = findViewById(R.id.firebase_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = findViewById(R.id.name_field);

        mFireBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1 - Create Child in root object
                //2 - Assign  some value to the child object

                String name = mNameField.getText().toString().trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Name", name);

                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Stored...", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
    }
}

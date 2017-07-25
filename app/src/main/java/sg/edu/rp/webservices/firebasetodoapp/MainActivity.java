package sg.edu.rp.webservices.firebasetodoapp;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle, tvDate;
    private EditText etTitle, etDate;
    private Button btnAdd;
    private static final String TAG = "MainActivity";

    //1. Declare firebase variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootDatabaseReference;
//    private DatabaseReference messagePOJOReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvDate = (TextView) findViewById(R.id.textViewDate);
        etDate = (EditText) findViewById(R.id.edittextdate);
        etTitle = (EditText) findViewById(R.id.editTextTitle);
        btnAdd = (Button) findViewById(R.id.buttonAdd);


        //2. Get firebase database instance and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootDatabaseReference = firebaseDatabase.getReference();

        //3. add a value event listener to the "message"
        rootDatabaseReference.child("toDoItem/date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this method will get fired everytime the "message" value updates
                //in the real time database. We are getting our data back as a dataSnapshot
            //4. get the latest value from the datasnapshot - display on the UI using the edittext message
                String text = dataSnapshot.getValue(String.class);
                tvDate.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //this method will be invoked if there is any error
                Log.e(TAG, "Database error occured", databaseError.toException());
            }
        });

        rootDatabaseReference.child("toDoItem/title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this method will get fired everytime the "message" value updates
                //in the real time database. We are getting our data back as a dataSnapshot
                //4. get the latest value from the datasnapshot - display on the UI using the edittext message
                String text2 = dataSnapshot.getValue(String.class);
                tvTitle.setText(text2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //this method will be invoked if there is any error
                Log.e(TAG, "Database error occured", databaseError.toException());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseReference.child("toDoItem/date").setValue(etDate.getText().toString());
                rootDatabaseReference.child("toDoItem/title").setValue(etTitle.getText().toString());

            }
        });

    }
}

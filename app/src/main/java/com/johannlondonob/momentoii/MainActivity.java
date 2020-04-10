package com.johannlondonob.momentoii;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.johannlondonob.momentoii.adapter.EntidadAdapter;
import com.johannlondonob.momentoii.model.EntidadModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<EntidadModel> arrayList;
    private EntidadModel entidadModel;
    private ProgressBar progressBar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("entidad");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progressBarActivityMain);

        listView = findViewById(R.id.listView);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entidadModel = new EntidadModel();
                arrayList = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    entidadModel = child.getValue(EntidadModel.class);
                    arrayList.add(entidadModel);
                }
                listView.setAdapter(new EntidadAdapter(MainActivity.this, arrayList));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Imposible conectar con la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntidadModel entidadSelecta = (EntidadModel) parent.getItemAtPosition(position);
                if (entidadSelecta.get_id() != null && !entidadSelecta.get_id().equals("")) {
                    Intent detalleEntidad = new Intent(MainActivity.this, EntidadDetalleActivity.class);
                    detalleEntidad.putExtra("idEntidad", entidadSelecta.get_id());
                    detalleEntidad.putExtra("nombreEntidad", entidadSelecta.get_nombre());
                    startActivity(detalleEntidad);
                }
            }
        });

        FloatingActionButton fabRegistrarEntidad = findViewById(R.id.fabRegistrarEntidad);
        fabRegistrarEntidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistroEntidadActivity.class);
                startActivity(intent);
            }
        });
    }

    // Lo que sigue, dibuja un menú en la parte superior derecha que puede personalizarse
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.johannlondonob.momentoii.adapter.AccesoRemotoAdapter;
import com.johannlondonob.momentoii.model.AccesoRemotoModel;

import java.util.ArrayList;

public class AccesoRemotoActivity extends AppCompatActivity {

    private AccesoRemotoModel accesoRemotoModel;
    private String idEntidad, nombreEntidad, idAccesoRemoto;
    private ArrayList<AccesoRemotoModel> arrayList;
    private ProgressBar progressBarAccesoRemotoActivity;
    private ListView listViewAccesosRemotos;
    private FloatingActionButton fabAgregarAccesoRemoto;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_remoto);
        idEntidad = getIntent().getStringExtra("idEntidad");
        nombreEntidad = getIntent().getStringExtra("nombreEntidad");
        AccesoRemotoActivity.this.setTitle(nombreEntidad);
        accesoRemotoModel = new AccesoRemotoModel();

        final DatabaseReference reference = database.getReference("accesos_remotos/" + idEntidad);

        progressBarAccesoRemotoActivity = findViewById(R.id.progressBarAccesoRemotoActivity);
        fabAgregarAccesoRemoto = findViewById(R.id.fabAgregarAccesoRemoto);
        listViewAccesosRemotos = findViewById(R.id.listViewAccesoRemoto);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accesoRemotoModel = new AccesoRemotoModel();
                arrayList = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    accesoRemotoModel = child.getValue(AccesoRemotoModel.class);
                    arrayList.add(accesoRemotoModel);
                }

                if (arrayList.size() <= 0) {
                    progressBarAccesoRemotoActivity.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "No hay registros para mostrar. ¿Desea crear uno?", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Sí, seguro", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent registroAccesoRemoto = new Intent(AccesoRemotoActivity.this, RegistroAccesoRemotoActivity.class);
                            registroAccesoRemoto.putExtra("idEntidad", idEntidad);
                            registroAccesoRemoto.putExtra("nombreEntidad", nombreEntidad);
                            startActivity(registroAccesoRemoto);
                            finish();
                        }
                    });
                    snackbar.show();
                } else {
                    listViewAccesosRemotos.setAdapter(new AccesoRemotoAdapter(AccesoRemotoActivity.this, arrayList));
                    progressBarAccesoRemotoActivity.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AccesoRemotoActivity.this, "No se encontraron registros", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        listViewAccesosRemotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accesoRemotoModel = (AccesoRemotoModel) parent.getItemAtPosition(position);
                if (accesoRemotoModel.get_id() != null && !accesoRemotoModel.get_id().equals("")) {
                    idEntidad = accesoRemotoModel.get_idEntidad();
                    idAccesoRemoto = accesoRemotoModel.get_id();
                    Intent detalleAccesoRemoto = new Intent(AccesoRemotoActivity.this, AccesoRemotoDetalleActivity.class);
                    detalleAccesoRemoto.putExtra("idEntidad", idEntidad);
                    detalleAccesoRemoto.putExtra("idAccesoRemoto", idAccesoRemoto);
                    detalleAccesoRemoto.putExtra("nombreEntidad", nombreEntidad);
                    startActivity(detalleAccesoRemoto);
                }
            }
        });

        fabAgregarAccesoRemoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registroAccesoRemoto = new Intent(AccesoRemotoActivity.this, RegistroAccesoRemotoActivity.class);
                registroAccesoRemoto.putExtra("idEntidad", idEntidad);
                registroAccesoRemoto.putExtra("nombreEntidad", nombreEntidad);
                startActivity(registroAccesoRemoto);
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

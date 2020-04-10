package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.johannlondonob.momentoii.adapter.CopiaSeguridadAdapter;
import com.johannlondonob.momentoii.model.CopiaSeguridadModel;

import java.util.ArrayList;

public class CopiaSeguridadActivity extends AppCompatActivity {

    private String idEntidad, nombreEntidad;
    private CopiaSeguridadModel model;
    private ArrayList<CopiaSeguridadModel> list;
    private ListView listView;
    private ProgressBar progressBar;
    private FloatingActionButton fabAgregarCopiaSeguridad;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copia_seguridad);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            idEntidad = bundle.getString("idEntidad");
            nombreEntidad = bundle.getString("nombreEntidad");
            CopiaSeguridadActivity.this.setTitle(nombreEntidad);
        }

        listView = findViewById(R.id.listViewCopiaSeguridad);
        fabAgregarCopiaSeguridad = findViewById(R.id.fabAgregarCopiaSeguridad);
        progressBar = findViewById(R.id.progressBarCopiaSeguridadActivity);
        final DatabaseReference reference = database.getReference("copias_seguridad/" + idEntidad);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model = new CopiaSeguridadModel();
                list = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    model = (CopiaSeguridadModel) child.getValue(CopiaSeguridadModel.class);
                    list.add(model);
                }

                if (list.size() <= 0) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "No hay registros. ¿Desea crear uno?", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Sí, seguro", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CopiaSeguridadActivity.this, RegistroCopiaSeguridadActivity.class);
                            intent.putExtra("idEntidad", idEntidad);
                            intent.putExtra("nombreEntidad", nombreEntidad);
                            startActivity(intent);
                        }
                    });
                    snackbar.show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(new CopiaSeguridadAdapter(CopiaSeguridadActivity.this, list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CopiaSeguridadActivity.this, "No se pudo conectar con la base de datos", Toast.LENGTH_LONG).show();
            }
        });

        fabAgregarCopiaSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CopiaSeguridadActivity.this, RegistroCopiaSeguridadActivity.class);
                intent.putExtra("idEntidad", idEntidad);
                intent.putExtra("nombreEntidad", nombreEntidad);
                startActivity(intent);
                onBackPressed();
            }
        });

    }
}

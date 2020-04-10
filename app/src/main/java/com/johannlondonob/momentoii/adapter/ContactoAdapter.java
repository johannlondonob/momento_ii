package com.johannlondonob.momentoii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.johannlondonob.momentoii.R;
import com.johannlondonob.momentoii.model.ContactoModel;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {
    private ArrayList<ContactoModel> list;
    private ContactoModel model;
    private Context context;

    public ContactoAdapter(Context context, ArrayList<ContactoModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_list_contacto, parent, false);
        }

        model = list.get(position);
        TextView textViewNombres = itemView.findViewById(R.id.textViewNombresApellidosItemLista);
        TextView textViewCargo = itemView.findViewById(R.id.textViewCargoItemLista);
        TextView textViewTelefono1 = itemView.findViewById(R.id.textViewTelefonoUnoItemLista);
        TextView textViewTelefono2 = itemView.findViewById(R.id.textViewTelefonoDosItemLista);

        textViewNombres.setText(model.get_nombresApellidos());
        textViewCargo.setText(model.get_cargo());
        textViewTelefono1.setText((model.get_telefonoUno().equals("") ? "Sin asignar" : model.get_telefonoUno()));
        textViewTelefono2.setText((model.get_telefonoDos().equals("") ? "Sin asignar" : model.get_telefonoDos()));

        return itemView;
    }
}

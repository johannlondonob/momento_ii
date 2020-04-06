package com.johannlondonob.momentoii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.johannlondonob.momentoii.R;
import com.johannlondonob.momentoii.model.AccesoRemotoModel;

import java.util.ArrayList;

public class AccesoRemotoAdapter extends BaseAdapter {
    private ArrayList<AccesoRemotoModel> list;
    private AccesoRemotoModel model;
    private Context context;

    public AccesoRemotoAdapter(Context context, ArrayList<AccesoRemotoModel> list) {
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
            itemView = inflater.inflate(R.layout.item_list_acceso_remoto, parent, false);
        }

        model = list.get(position);
        TextView textViewNombreEquipoAccesoRemotoItemLista = itemView.findViewById(R.id.textViewNombreEquipoAccesoRemotoItemLista);
        TextView textViewUsuarioAccesoRemotoItemLista = itemView.findViewById(R.id.textViewUsuarioAccesoRemotoItemLista);
        textViewNombreEquipoAccesoRemotoItemLista.setText(model.get_nombreEquipo());
        // textViewUsuarioAccesoRemotoItemLista.setText(model.get_usuario());

        return itemView;
    }
}

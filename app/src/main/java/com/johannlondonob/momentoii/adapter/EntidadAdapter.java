package com.johannlondonob.momentoii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.johannlondonob.momentoii.R;
import com.johannlondonob.momentoii.model.EntidadModel;

import java.util.ArrayList;

public class EntidadAdapter extends BaseAdapter {
    private ArrayList<EntidadModel> list;
    private EntidadModel model;
    private Context context;

    public EntidadAdapter(Context context, ArrayList<EntidadModel> list) {
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
            itemView = inflater.inflate(R.layout.item_list_entidad, parent, false);
        }

        model = list.get(position);
        TextView textViewNombreEntidadItemLista = itemView.findViewById(R.id.textViewNombreEntidadItemLista);
        TextView textViewDireccionEntidadItemLista = itemView.findViewById(R.id.textViewDireccionEntidadItemLista);
        TextView textViewEmailEntidadItemLista = itemView.findViewById(R.id.textViewEmailEntidadItemLista);
        textViewNombreEntidadItemLista.setText(model.get_nombre());
        textViewDireccionEntidadItemLista.setText(model.get_direccion());
        textViewEmailEntidadItemLista.setText(model.get_email());

        return itemView;
    }
}

package com.example.e_vicemote.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.e_vicemote.Model.Rincian;
import com.example.e_vicemote.R;
import com.google.firebase.database.annotations.NotNull;


import java.util.List;



public class CustomAdapter3 extends ArrayAdapter<Rincian> {

    private Activity context;
    List<Rincian> ListData;

    public CustomAdapter3(Activity context, List<Rincian> ListData) {
        super(context, R.layout.data_rincian, ListData);
        this.context = context;
        this.ListData = ListData;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.data_rincian, null, true);

        TextView sparepart = (TextView) listViewItem.findViewById(R.id.sparepart);
        TextView harga = (TextView) listViewItem.findViewById(R.id.harga);
        TextView jumlah = (TextView) listViewItem.findViewById(R.id.jumlah);

        Rincian data = ListData.get(position);
        sparepart.setText(data.getSparepart());
        harga.setText(data.getHarga());
        jumlah.setText(data.getJumlah());

        return listViewItem;
    }
}
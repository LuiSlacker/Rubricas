package com.uninorte.rubricas.helper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.helper.EvaluacionDataModel;

import java.util.ArrayList;

public class CustomEvaluacionesAdapter extends ArrayAdapter<EvaluacionDataModel> implements View.OnClickListener {

    private ArrayList<EvaluacionDataModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtEstudiante;
        TextView txtNota;

    }

    public CustomEvaluacionesAdapter(ArrayList<EvaluacionDataModel> data, Context context) {
        super(context, R.layout.custom_listview_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EvaluacionDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_listview_item, parent, false);
            viewHolder.txtEstudiante = (TextView) convertView.findViewById(R.id.estudiante);
            viewHolder.txtNota = (TextView) convertView.findViewById(R.id.nota);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtEstudiante.setText(dataModel.getEstudiante());
        viewHolder.txtNota.setText(dataModel.getNota() == 0.0 ? "-" : dataModel.getNota()+"");
        return convertView;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        EvaluacionDataModel dataModel=(EvaluacionDataModel)object;

        /*switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }
}

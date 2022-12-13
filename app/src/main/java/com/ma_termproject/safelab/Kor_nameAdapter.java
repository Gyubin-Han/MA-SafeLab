package com.ma_termproject.safelab;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Kor_nameAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Kor_name> worldpopulationlist = null;
    private ArrayList<Kor_name> arraylist;

    public Kor_nameAdapter(Context context, ArrayList<Kor_name> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Kor_name>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView cas;
        TextView eng;
        TextView kor;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Kor_name getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.kor_item, null);
            // Locate the TextViews in listview_item.xml
            holder.cas = (TextView) view.findViewById(R.id.cas);
            holder.eng = (TextView) view.findViewById(R.id.country);
            holder.kor = (TextView) view.findViewById(R.id.population);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.cas.setText(worldpopulationlist.get(position).getRank());
        holder.eng.setText(worldpopulationlist.get(position).getCountry());
        holder.kor.setText(worldpopulationlist.get(position).getPopulation());

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, Kor_ItemView.class);
                // Pass all data rank
                intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
                // Pass all data flag
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (Kor_name kn : arraylist)
            {
                if (kn.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(kn);
                }
            }
        }
        notifyDataSetChanged();
    }

}
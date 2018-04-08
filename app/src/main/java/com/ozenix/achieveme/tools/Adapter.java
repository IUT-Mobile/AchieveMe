package com.ozenix.achieveme.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ozenix.achieveme.R;
import com.ozenix.achieveme.mechanisms.Achievement;
import com.ozenix.achieveme.tools.ViewHolder;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<Achievement> list;

    //ajouter un constructeur prenant en entrée une liste
    public Adapter(List<Achievement> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards, viewGroup, false);
        return new ViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque Achievements
    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int position) {
        Achievement Achievement = list.get(position);
        ViewHolder.bind(Achievement);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
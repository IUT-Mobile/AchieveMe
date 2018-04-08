package com.ozenix.achieveme.tools;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ozenix.achieveme.mechanisms.Achievement;
import com.ozenix.achieveme.R;

import static android.content.ContentValues.TAG;

public class ViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public ViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.image);
        //imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Achievement myObject){
        /*textViewView.setText(myObject.getText());
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);*/


        Log.d(TAG, "bind: " + R.id.image);
    }
}
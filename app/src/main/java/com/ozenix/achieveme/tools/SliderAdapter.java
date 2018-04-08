package com.ozenix.achieveme.tools;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ozenix.achieveme.R;

/**
 * Created by p16005334 on 28/03/18.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] slide_images = { //Different pages icons
            R.drawable.fp_icon,
            R.drawable.sp_icon,
            R.drawable.tp_icon
    };

    public String[] slide_headings = { //Headers texts
            "Bienvenue",
            "Réalisez",
            "Partagez"
    };

    public String[] slide_descs = { //Descripting texts
            "Achieve Me est une application de succès dans la vie courante, un peu comme dans les jeux vidéos, des petits défis quotidiens sont disponibles, lancez-vous ! \n (Swipez vers la gauche :D)",
            "Pour réaliser vos succès vous pourrez vous balader ou encore cuisiner de délicieux mets, plusieurs catégories d'achievements sont d'ores et déjà disponibles !",
            "Ajoutez vos amis pour partager votre avancée avec eux, à plusieurs, on s'amuse forcément plus, vous pourrez dans une version prochaine, leur proposer vos défis les plus fous ! Let's gooo bois !"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.slideImage);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
package fr.event.commercant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class OfferAdapter extends ArrayAdapter<Offer> {
    public OfferAdapter(@NonNull Context context, List<Offer> offers) {
        super(context, 0, offers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.offer_item,parent, false);
        }

        OfferViewHolder viewHolder = (OfferViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new OfferViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.offer_title);
            viewHolder.points = (TextView) convertView.findViewById(R.id.nb_point);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Offer offer = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(offer.getName());
        viewHolder.points.setText(offer.getPoints() + "points");

        return convertView;
    }

    private class OfferViewHolder{
        public TextView name;
        public TextView points;
    }
}

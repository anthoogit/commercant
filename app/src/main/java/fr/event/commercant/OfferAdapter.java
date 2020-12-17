package fr.event.commercant;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private List<Offer> offers;
    private Context context;
    private Client client;

    public OfferAdapter(@NonNull Context context, List<Offer> offers, Client client) {
        this.context = context;
        this.offers = offers;
        this.client = client;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.offer_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offers.get(position);

        //il ne reste plus qu'à remplir notre vue
        holder.nameTextView.setText(offer.getName());
        holder.pointsTextView.setText(offer.getPoints() + "points");

        // Pour cliquer sur les offres
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OfferActivity.class);
                intent.putExtra(OfferActivity.OFFER_NAME, offer.getName());
                intent.putExtra(OfferActivity.OFFER_POINTS, offer.getPoints());
                intent.putExtra(ClientInfoActivity.CLIENT_ID, client.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView pointsTextView;
        private View parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = (TextView) itemView.findViewById(R.id.offer_title);
            this.pointsTextView = (TextView) itemView.findViewById(R.id.nb_point);
            this.parentView = itemView;
        }
    }
}

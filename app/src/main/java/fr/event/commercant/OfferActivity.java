package fr.event.commercant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OfferActivity extends AppCompatActivity {
    public static final String OFFER_NAME = "fr.event.ommercant.OFFER_NAME";
    public static final String OFFER_POINTS = "fr.event.ommercant.OFFER_POINTS";
    Client client = null;
    int offerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Intent intent = getIntent();
        int id_client = intent.getIntExtra(ClientInfoActivity.CLIENT_ID, 0);
        // Recupère le client
        try {
            client = MainActivity.clientdb.getClient(id_client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Capture the layout's TextView and set the string as this text

        // Prénom
        TextView textView_firstname = findViewById(R.id.firstName);
        textView_firstname.setText(client.getFirstName());

        // Nom
        TextView textView_name = findViewById(R.id.name);
        textView_name.setText(client.getName());

        // Points client actuel
        TextView soldeActuelTextView = findViewById(R.id.client_current_point);
        soldeActuelTextView.setText(client.getPoints() + " points");

        // Nouveaux points client
        offerPoints = intent.getIntExtra(OFFER_POINTS, 0);
        int nouveauSole = client.getPoints() - offerPoints;
        TextView nouveauSoldeTextView = findViewById(R.id.client_after_point);
        nouveauSoldeTextView.setText(nouveauSole + " points");

        // Titre offre
        TextView nameTextView = findViewById(R.id.offer_title);
        nameTextView.setText(intent.getStringExtra(OFFER_NAME));

        // Point offre
        TextView pointsTextView = findViewById(R.id.nb_point);
        pointsTextView.setText(intent.getIntExtra(OFFER_POINTS, 0) + "points");

    }

    // Action bouton valider, met à jour les points du client
    public void validate(View view) {
        int nouveauSole = client.getPoints() - offerPoints;
        client.setPoints(nouveauSole);
        Intent intent = new Intent(this, ClientInfoActivity.class);
        intent.putExtra(ClientInfoActivity.CLIENT_ID, client.getId());
        finish();
    }

}
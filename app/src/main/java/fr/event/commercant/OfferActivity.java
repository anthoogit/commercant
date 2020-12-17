package fr.event.commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        try {
            client = MainActivity.clientdb.getClient(id_client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Capture the layout's TextView and set the string as tis text
        TextView textView_firstname = findViewById(R.id.firstName);
        textView_firstname.setText(client.getFirstName());

        TextView textView_name = findViewById(R.id.name);
        textView_name.setText(client.getName());

        offerPoints = intent.getIntExtra(OFFER_POINTS, 0);
        int nouveauSole = client.getPoints() - offerPoints;
        TextView soldeActuelTextView = findViewById(R.id.client_current_point);
        soldeActuelTextView.setText(client.getPoints() + " points");

        TextView nouveauSoldeTextView = findViewById(R.id.client_after_point);
        nouveauSoldeTextView.setText(nouveauSole + " points");

        TextView nameTextView = findViewById(R.id.offer_title);
        nameTextView.setText(intent.getStringExtra(OFFER_NAME));
        TextView pointsTextView = findViewById(R.id.nb_point);
        pointsTextView.setText(intent.getIntExtra(OFFER_POINTS, 0) + "points");

    }

    public void validate(View view) { //Bouton valider
        int nouveauSole = client.getPoints() - offerPoints;
        client.setPoints(nouveauSole);
        Intent intent = new Intent(this, ClientInfoActivity.class);
        intent.putExtra(ClientInfoActivity.CLIENT_ID, client.getId());
        finish();
    }

}
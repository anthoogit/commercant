package fr.event.commercant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientInfoActivity extends AppCompatActivity {
    public static final String CLIENT_ID = "fr.event.ommercant.CLIENT_ID";
    public static int id_client_temp = 0;
    int id_client;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        Intent intent;

        if (getIntent() != null) {
            intent = getIntent();
            id_client = intent.getIntExtra(CLIENT_ID, 0);
        } else {
            id_client = id_client_temp;
        }
        id_client_temp = id_client;

        // Récupère le client avec son id
        try {
            client = MainActivity.clientdb.getClient(id_client);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String firstname = client.getFirstName(); // Prénom
        String name = client.getName(); // Nom
        int points = client.getPoints(); // Points

        // Capture the layout's TextView and set the string as tis text
        TextView textView_firstname = findViewById(R.id.firstName);
        textView_firstname.setText(firstname);

        TextView textView_name = findViewById(R.id.name);
        textView_name.setText(name);

        TextView textView_points = findViewById(R.id.client_point);
        textView_points.setText(points + " points");

        // Affiche les liste des offres
        RecyclerView recyclerView = findViewById(R.id.listoffers);
        recyclerView.setAdapter(new OfferAdapter(this, MainActivity.offerdb.getAllOffers(), client));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
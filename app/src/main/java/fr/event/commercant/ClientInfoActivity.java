package fr.event.commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClientInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        //Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String firstname = intent.getStringExtra("CLIENT_FIRSNAME");
        String name = intent.getStringExtra("CLIENT_NAME");
        int points = intent.getIntExtra("CLIENT_POINTS", 0);

        // Capture the layout's TextView and set the string as tis text
        TextView textView_firstname = findViewById(R.id.firstName);
        textView_firstname.setText(firstname);

        TextView textView_name = findViewById(R.id.name);
        textView_name.setText(name);

        TextView textView_points = findViewById(R.id.client_point);
        textView_points.setText(points + " points");

        ListView listView = findViewById(R.id.listView);
        OfferAdapter adapter = new OfferAdapter(this, MainActivity.offerdb.getAllOffers());
        listView.setAdapter(adapter);
//        ArrayAdapter<Offer> arrayAdapter = new ArrayAdapter<Offer>(this, R.layout.offer_item, MainActivity.offerdb.getAllOffers());
//        ArrayAdapter<Offer> arrayAdapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, MainActivity.offerdb.getAllOffers());
//        listView.setAdapter(arrayAdapter);
    }
}
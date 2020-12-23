package fr.event.commercant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    static ClientDatabase clientdb = new ClientDatabase();
    static OfferDatabase offerdb = new OfferDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        checkCameraPermission(); // Demande la permission de la caméra

        //data (base de données de test)
        if (clientdb.getClientDbSize() == 0) { // Pour ne les mettre qu'une seule fois
            System.out.println("Data added");
            clientdb.insertClient(new Client(72, "Anthony", "Pauthonnier", 132));
            clientdb.insertClient(new Client(55, "Armand", "Humblot", 150));
            clientdb.insertClient(new Client(102, "Maïwenn", "Le Cacheux", 136));
            clientdb.insertClient(new Client(143, "Nathanaël", "Constant", 120));

            offerdb.insertOffer(new Offer("Une viennoiserie offerte !", 80));
            offerdb.insertOffer(new Offer("Un gateau offert !", 100));
        }

        mCodeScanner = new CodeScanner(this, scannerView);

        // Parameters (default values)
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK); // or CAMERA_FRONT or specific camera id
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS); // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        mCodeScanner.setAutoFocusMode(AutoFocusMode.CONTINUOUS); // or SAFE
        mCodeScanner.setScanMode(ScanMode.SINGLE); // or CONTINUOUS or PREVIEW
        mCodeScanner.setAutoFocusEnabled(true); // Whether to enable auto focus or not
        mCodeScanner.setFlashEnabled(false); // Whether to enable flash or not

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                int id_client = Integer.parseInt(result.getText()); // lit la valeur du qr code
                System.out.println(id_client);

                try {
                    Client c = clientdb.getClient(id_client); // récupère le client s'il existe dans la base de données

                    // Génère une nouvelle activité ClientInfo avec l'id du client
                    Intent intent = new Intent(getApplicationContext(), ClientInfoActivity.class);
                    intent.putExtra(ClientInfoActivity.CLIENT_ID, c.getId());
                    startActivity(intent);
                } catch (Exception e) { // affiche que le client n'a pas été trouvé s'il n'est pas dans la base
                    System.out.println("Id client non trouvé");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Client non trouvé", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Camera initialization error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    // Permission camera
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }


}
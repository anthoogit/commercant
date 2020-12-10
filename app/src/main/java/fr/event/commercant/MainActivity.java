package fr.event.commercant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import java.sql.SQLSyntaxErrorException;

public class MainActivity extends AppCompatActivity {
//    public static final String EXTRA_MESSAGE =  "fr.event.ommercant.MESSAGE";
    private CodeScanner mCodeScanner;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    ClientDatabase clientdb = new ClientDatabase();
    static OfferDatabase offerdb = new OfferDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        //data
        clientdb.insertClient(new Client(72, "Anthony", "Pauthonnier"));
        clientdb.insertClient(new Client(55, "Armand", "Humblot", 50));
        clientdb.insertClient(new Client(102, "Maïwenn", "Le Cacheux"));
        clientdb.insertClient(new Client(143, "Nathanaël", "Constant"));

        offerdb.insertOffer(new Offer("Une vienoiserie offerte !", 80));
        offerdb.insertOffer(new Offer("Une vienoiserie offerte !", 80));
        offerdb.insertOffer(new Offer("Une vienoiserie offerte !", 80));
        offerdb.insertOffer(new Offer("Un gateau offert !", 130));

        //Test
//        Client c2 = null;
//        try {
//            c2 = clientdb.getClient(72);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Test : " + c2.getFirstName());

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
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
                Intent intent = new Intent(getApplicationContext(), ClientInfoActivity.class);
                int id_client = Integer.parseInt(result.getText());
                System.out.println(id_client);
                try {
                    Client c = clientdb.getClient(id_client);
                    intent.putExtra("CLIENT_FIRSNAME", c.getFirstName());
                    intent.putExtra("CLIENT_NAME", c.getName());
                    intent.putExtra("CLIENT_POINTS", c.getPoints());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),  "Camera initialization error" + error.getMessage(), Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CAMERA){
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera();
//            }else{
//                Toast.makeText(this, "Camera Permisssion Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


}
package app.num.barcodescannerproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yummy on 4/12/2017.
 */

public class MainActivity extends AppCompatActivity {

    public static int appflag = 0;
    private Button buttonView;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        buttonView = (Button) findViewById(R.id.buttonView);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);}

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appflag = 1;
                startActivity(new Intent(MainActivity.this, ViewInv.class));
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appflag = 2;
                startActivity(new Intent(MainActivity.this, ScanBar.class));
            }
        });
    }
}
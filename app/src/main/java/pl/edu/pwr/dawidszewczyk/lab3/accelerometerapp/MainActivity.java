package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView ivLightsaber;
    ImageView ivMagnum;
    ImageView ivM4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivLightsaber = (ImageView) findViewById(R.id.ivLightsaber);
        ivMagnum = (ImageView) findViewById(R.id.ivMagnum);
        ivM4 = (ImageView) findViewById(R.id.ivM4);
        setImageLightsaberOnClickListener();
        setImageMagnumOnClickListener();
        setImageM4OnClickListener();
    }

    private void setImageLightsaberOnClickListener() {
        ivLightsaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LightsaberActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setImageMagnumOnClickListener() {
        ivMagnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RevolverActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setImageM4OnClickListener() {
        ivM4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RifleActivity.class);
                startActivity(intent);
            }
        });
    }
}

package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RifleActivity extends AbstractWeaponActivity {
    private static final float SHOT_Y_ACCELERATION_THRESHOLD = 12f;
    private static final float RELOAD_X_ACCELERATION_THRESHOLD = 25f;

    private TextView tvAmmunition;
    private int ammo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun);
        tvAmmunition = (TextView) findViewById(R.id.tvAmmunition);
        ammo = getIntent().getIntExtra(AMMO_NUMBER_KEY, 30);
        updateTextViewAmmunition();
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);

        if (y > SHOT_Y_ACCELERATION_THRESHOLD) {
            if (ammo > 0) {
                playSoundEffect(R.raw.m4_semi_auto);
                ammo--;
                updateTextViewAmmunition();
            } else {
                playSoundEffect(R.raw.gun_empty);
            }
        } else if (x > RELOAD_X_ACCELERATION_THRESHOLD) {
            playSoundEffect(R.raw.m4_reload);
            ammo = 30;
            updateTextViewAmmunition();
        }
    }

    private void updateTextViewAmmunition() {
        tvAmmunition.setText(getString(R.string.ammo_left) + " " + ammo);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.action_rifle).setVisible(false);
        return true;
    }
}

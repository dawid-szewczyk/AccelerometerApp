package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RevolverActivity extends AbstractWeaponActivity {
    private static final float SHOT_Y_ACCELERATION_THRESHOLD = 12f;
    private static final float RELOAD_X_ACCELERATION_THRESHOLD = 20f;
    private static final int STATE_PREPARED = 0;
    private static final int STATE_RELOADING = 1;

    private TextView tvAmmunition;
    private int ammo;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun);
        tvAmmunition = (TextView) findViewById(R.id.tvAmmunition);
        ammo = getIntent().getIntExtra(AMMO_NUMBER_KEY, 6);
        state = STATE_PREPARED;
        updateTextViewAmmunition();
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);

        if(state == STATE_PREPARED) {
            if(y > SHOT_Y_ACCELERATION_THRESHOLD) {
                if(ammo > 0) {
                    playSoundEffect(R.raw.magnum_shot);
                    ammo--;
                    updateTextViewAmmunition();
                } else {
                    playSoundEffect(R.raw.gun_empty);
                }
            } else if (x > RELOAD_X_ACCELERATION_THRESHOLD) {
                playSoundEffect(R.raw.magnum_bullet_shells);
                state = STATE_RELOADING;
            }
        } else if(state == STATE_RELOADING) {
            if(x > RELOAD_X_ACCELERATION_THRESHOLD && ammo < 6) {
                playSoundEffect(R.raw.magnum_reload);
                ammo++;
                updateTextViewAmmunition();
            } else if(y > SHOT_Y_ACCELERATION_THRESHOLD) {
                playSoundEffect(R.raw.magnum_prepare);
                state = STATE_PREPARED;
            }
        }
    }

    private void updateTextViewAmmunition() {
        tvAmmunition.setText(getString(R.string.ammo_left) + " " + ammo);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.action_revolver).setVisible(false);
        return true;
    }
}

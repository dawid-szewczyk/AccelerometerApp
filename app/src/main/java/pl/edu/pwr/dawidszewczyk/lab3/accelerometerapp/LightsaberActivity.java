package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LightsaberActivity extends AbstractWeaponActivity {
    private static final float SHORT_EFFECT_ACCELERATION_THRESHOLD = 10f;

    private List<Integer> shortSoundEffects = new ArrayList<>(Arrays.asList(
            R.raw.lightsaber_swing,
            R.raw.lightsaber_swing2
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
        float z = Math.abs(event.values[2]);
        if(x > SHORT_EFFECT_ACCELERATION_THRESHOLD || y > SHORT_EFFECT_ACCELERATION_THRESHOLD ||
                z > SHORT_EFFECT_ACCELERATION_THRESHOLD) {
            playSoundEffect(shortSoundEffects.get(new Random().nextInt(shortSoundEffects.size())));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.action_lightsaber).setVisible(false);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        playSoundEffect(R.raw.lightsaber_ignition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playSoundEffect(R.raw.lightsaber_retraction);
    }
}

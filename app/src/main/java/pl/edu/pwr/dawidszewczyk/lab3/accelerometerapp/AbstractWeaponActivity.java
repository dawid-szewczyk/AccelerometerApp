package pl.edu.pwr.dawidszewczyk.lab3.accelerometerapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class AbstractWeaponActivity extends AppCompatActivity implements
        SensorEventListener, AmmoDialogFragment.NoticeAmmoDialogListener {
    public static final String AMMO_NUMBER_KEY = "ammo_number";
    public static final String DIALOG_FRAGMENT_TAG = "dialog_tag";
    public static final int TYPE_REVOLVER = 1;
    public static final int TYPE_RIFLE = 2;
    public static final int MAX_AMMO_REVOLVER = 6;
    public static final int MAX_AMMO_RIFLE = 30;

    public SensorManager sensorManager;
    public Sensor accelerometer;
    public MediaPlayer mediaPlayer;
    private int choosedWeaponType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weapon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lightsaber:
                startWeaponActivity(LightsaberActivity.class, 0);
                break;
            case R.id.action_revolver:
                choosedWeaponType = TYPE_REVOLVER;
                showAmmoDialog(MAX_AMMO_REVOLVER);
                break;
            case R.id.action_rifle:
                choosedWeaponType = TYPE_RIFLE;
                showAmmoDialog(MAX_AMMO_RIFLE);
                break;
        }
        return true;
    }

    public void showAmmoDialog(int maxValue) {
        DialogFragment dialog = AmmoDialogFragment.newInstance(maxValue);
        dialog.show(getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    public void onDialogPositiveClick(int ammoNumber) {
        switch(choosedWeaponType) {
            case TYPE_REVOLVER:
                startWeaponActivity(RevolverActivity.class, ammoNumber);
                break;
            case TYPE_RIFLE:
                startWeaponActivity(RifleActivity.class, ammoNumber);
        }
    }

    public void onDialogNegativeClick() {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void playSoundEffect(int resourceId) {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), resourceId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public void startWeaponActivity(Class activity, int ammoNumber) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(AMMO_NUMBER_KEY, ammoNumber);
        this.startActivity(intent);
    }
}

package com.example.modul1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    
    // UI Elements for Parallax
    private View layerShadow, mainCard, layerImage, layerContent;
    
    // Spring Animations for Touch
    private SpringAnimation springX, springY, scaleX, scaleY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        layerShadow = findViewById(R.id.layer_shadow);
        mainCard = findViewById(R.id.main_card);
        layerImage = findViewById(R.id.layer_image);
        layerContent = findViewById(R.id.layer_content);

        // Initialize Sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setupSpringAnimations();
        setupTouchInteractions();

        // Load RenDY's official photo from local resource for instant loading
        Glide.with(this)
                .load(R.mipmap.foto_rendy1)
                .placeholder(R.mipmap.foto_rendy1)
                .error(R.mipmap.foto_rendy1)
                .centerCrop()
                .into((android.widget.ImageView) layerImage);
    }

    private void setupSpringAnimations() {
        // Position Spring
        springX = new SpringAnimation(mainCard, DynamicAnimation.TRANSLATION_X, 0);
        springY = new SpringAnimation(mainCard, DynamicAnimation.TRANSLATION_Y, 0);
        
        // Scale Spring for "Press" effect
        scaleX = new SpringAnimation(mainCard, DynamicAnimation.SCALE_X, 1f);
        scaleY = new SpringAnimation(mainCard, DynamicAnimation.SCALE_Y, 1f);

        configureSpring(springX);
        configureSpring(springY);
        configureSpring(scaleX);
        configureSpring(scaleY);
    }

    private void configureSpring(SpringAnimation anim) {
        anim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        anim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
    }

    private void setupTouchInteractions() {
        mainCard.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    scaleX.animateToFinalPosition(0.95f);
                    scaleY.animateToFinalPosition(0.95f);
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.performClick(); // Added for accessibility
                case MotionEvent.ACTION_CANCEL:
                    scaleX.animateToFinalPosition(1f);
                    scaleY.animateToFinalPosition(1f);
                    return true;
            }
            return false;
        });

        findViewById(R.id.btn_contact).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Connecting with RenDY...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0]; // Tilt Left/Right
            float y = event.values[1]; // Tilt Up/Down

            // Apply Parallax with different sensitivity (Spatial Depth)
            // Layer 0: Shadow (Moves most)
            layerShadow.setTranslationX(x * 15f);
            layerShadow.setTranslationY(y * 15f);

            // Layer 1: Main Card (Middle)
            mainCard.setTranslationX(x * 5f);
            mainCard.setTranslationY(y * 5f);
            
            // Subtle 3D Rotation
            mainCard.setRotationY(-x * 2f);
            mainCard.setRotationX(y * 2f);

            // Layer 3: Content (Moves opposite to card for depth)
            // Reduced sensitivity and added horizontal padding check logic implicitly
            layerContent.setTranslationX(x * 5f); 
            layerContent.setTranslationY(y * 5f);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

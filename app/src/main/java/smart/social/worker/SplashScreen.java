package smart.social.worker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashpra);
        final RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.splashScreen);

        //thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // relativeLayout.setBackground(getResources().getDrawable(R.drawable.));
                        }
                    });
                    sleep(2000);

                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    startActivity(new Intent(SplashScreen.this, FingerprintActivity.class));
                }
                finish();
            }
        };
        logoTimer.start();
    }

}

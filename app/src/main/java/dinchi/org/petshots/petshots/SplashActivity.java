package dinchi.org.petshots.petshots;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;

import dinchi.org.petshots.R;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Splash screen	
 * */
public class SplashActivity extends AppCompatActivity {
	
	//TODO: Make the splash screen for 5 secs
	private static int SPLASH_SCREEN_DELAY = 3000;
	private Bitmap bmSplashLogo;
	private ImageView imgviewapplogo;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        imgviewapplogo = (ImageView) findViewById(R.id.imgLogo);
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inPurgeable = true;
//        bmOptions.inJustDecodeBounds = true;
//        bmSplashLogo = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_logo,bmOptions);
//
//        imgviewapplogo.setImageBitmap(bmSplashLogo);
        
        new Handler().postDelayed(new Runnable() {
			
			public void run() {

				Intent i = new Intent(SplashActivity.this, dinchi.org.petshots.petshots.DashBoardActivity.class);
				startActivity(i);
				//close the splash screen activity
				finish();
			}
		}, SPLASH_SCREEN_DELAY);
        
//        bmSplashLogo.recycle();
//        bmSplashLogo = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}

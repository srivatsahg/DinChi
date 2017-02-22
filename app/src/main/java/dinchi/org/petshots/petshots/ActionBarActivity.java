package dinchi.org.petshots.petshots;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import dinchi.org.petshots.R;

public class ActionBarActivity extends AppCompatActivity {

	  // Action bar
    private ActionBar mActionBar;
    private LayoutInflater mInflater;
    private View mCustomView;
    private TextView mTitleTextView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        initializeActionBar();
    }

    private void initializeActionBar() {
    	mActionBar = getSupportActionBar();
    	Log.i("Actionbar", mActionBar.toString());
    	mActionBar.setDisplayShowHomeEnabled(false);
    	mActionBar.setDisplayShowTitleEnabled(false);
    	mInflater = LayoutInflater.from(this);
    	mCustomView = mInflater.inflate(R.layout.psabsactiontext, null);
    	mTitleTextView = (TextView)findViewById(R.id.title_text);
    	mTitleTextView.setText("Pet Shots");
    	mActionBar.setCustomView(mCustomView);
    	mActionBar.setDisplayShowCustomEnabled(true);
    	
	}
	
}

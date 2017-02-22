package dinchi.org.petshots.utils;

import android.app.Activity;

import dinchi.org.petshots.R;

public class DoctorListLayoutUpdater {

	private Activity activity;
	
	public DoctorListLayoutUpdater(Activity activity){
	
		this.activity = activity;
	}
	
	public void updateLayout(){
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.dash_vet), R.mipmap.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity, Constants.UI_DoctorList+ Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_DoctorList+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}
}
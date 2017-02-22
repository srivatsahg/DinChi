package dinchi.org.petshots.utils;

import android.app.Activity;
import dinchi.org.petshots.R;

public class DoctorInfoLayoutUpdater extends LayoutUpdater {

	private Activity activity;
	
	/*	Constructor */
	public DoctorInfoLayoutUpdater(Activity activity){
		super(activity);
		this.activity = activity;
	}
	
	public void updateLayout(){
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strVetDetails), R.mipmap.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity,Constants.UI_Doctor+Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_Doctor+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}
}

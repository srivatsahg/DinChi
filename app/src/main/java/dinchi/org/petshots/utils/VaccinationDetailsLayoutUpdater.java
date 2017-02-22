package dinchi.org.petshots.utils;

import android.app.Activity;

import dinchi.org.petshots.R;

public class VaccinationDetailsLayoutUpdater extends LayoutUpdater {

	private Activity activity;
	
	public VaccinationDetailsLayoutUpdater(Activity activity) {
		/*	Just calls the constructor of the superclass */
		super(activity);
		this.activity = activity;
	}
	@Override
	public void updateLayout() {
		// TODO Auto-generated method stub
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strVaccineData), R.mipmap.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity, Constants.UI_VaccineEntry+ Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_VaccineEntry+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.

	}

}

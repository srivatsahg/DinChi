package dinchi.org.petshots.utils;

import android.app.Activity;

import dinchi.org.petshots.R;

public class PetInfoLayoutUpdater extends dinchi.org.petshots.utils.LayoutUpdater {

	private Activity activity;
	
	/*	Constructor */
	public PetInfoLayoutUpdater(Activity activity){
		/*	Just calls the constructor of the superclass */
		super(activity);
		this.activity = activity;
	}
	
	public void updateLayout(){
		new dinchi.org.petshots.utils.HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strPetdetails), R.mipmap.trip_analyzer_title);
		dinchi.org.petshots.utils.SharedPrefsManager mPrefs = new dinchi.org.petshots.utils.SharedPrefsManager(activity, dinchi.org.petshots.utils.Constants.UI_Pet+ dinchi.org.petshots.utils.Constants.UIUPDATE);
		mPrefs.EditPrefs(dinchi.org.petshots.utils.Constants.UI_Pet+ dinchi.org.petshots.utils.Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}
}

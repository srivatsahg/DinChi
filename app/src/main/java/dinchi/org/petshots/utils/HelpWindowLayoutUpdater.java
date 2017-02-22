package dinchi.org.petshots.utils;
import android.app.Activity;
import dinchi.org.petshots.R;

public class HelpWindowLayoutUpdater extends LayoutUpdater {

	private Activity activity;
	
	/*	Constructor */
	public HelpWindowLayoutUpdater(Activity activity){
		/*	Just calls the constructor of the superclass */
		super(activity);
		this.activity = activity;
	}
	
	public void updateLayout(){
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strHelp), R.mipmap.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity, Constants.UI_Help+ Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_Help+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}

}

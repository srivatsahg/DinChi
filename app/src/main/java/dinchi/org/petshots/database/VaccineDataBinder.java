package dinchi.org.petshots.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dinchi.org.petshots.R;
import dinchi.org.petshots.utils.Constants;
import dinchi.org.petshots.utils.DoctorDetails;
import dinchi.org.petshots.utils.Typefaces;
import dinchi.org.petshots.utils.VaccineDetails;

public class VaccineDataBinder extends BaseAdapter {

	LayoutInflater inflater;
	List<VaccineDetails> vaccineList;
	VaccineDataViewHolder holder;
	String KEY_TAG = "[VaccineDataBinder]";
	Context ctxt;
	Typeface typeface;
	
	/*
	 * Default constructor
	 * */
	public VaccineDataBinder(){
		
	}
	
	/*
	 * Constructor overload
	 * */
	public VaccineDataBinder(Activity act,List<VaccineDetails> vaccineList){
		this.vaccineList = vaccineList;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.typeface = Typefaces.get(act.getApplicationContext(), Constants.ROBOTA_FONTFILE);
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return vaccineList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return vaccineList.get(arg0);
	}

	public synchronized void refreshAdapter(List<VaccineDetails> vaccineList) {
		   this.vaccineList = vaccineList;
		   notifyDataSetChanged();
		}
	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(dinchi.org.petshots.R.layout.pspetvaccinerowdetail, null);
	      holder = new VaccineDataViewHolder();
	      Log.i(KEY_TAG, "Before vet name access, convertView is null");
	      
	      holder.tvName = (TextView)vi.findViewById(dinchi.org.petshots.R.id.tvVaccineRowColumn);
	      holder.tvDate = (TextView)vi.findViewById(dinchi.org.petshots.R.id.tvVaccineDateColumn);
	      holder.tvDueDate = (TextView)vi.findViewById(dinchi.org.petshots.R.id.tvVaccineDueDateColumn);
	      holder.tvDoctor = (TextView)vi.findViewById(dinchi.org.petshots.R.id.tvVaccineDocColumn);
	      
	      vi.setTag(holder);
	      Log.i(KEY_TAG, "Tag set success, convertView is null !!");
	    }
	    else{
	    	
	    	holder = (VaccineDataViewHolder)vi.getTag();
	    }

	    Log.i(KEY_TAG, "Before Fullname access");
	    
	      // Setting all values in listview
	    	
	      String docVaccineName = 	vaccineList.get(position).getM_vaccinename().toString();
	      holder.tvName.setText(docVaccineName);
	      holder.tvName.setTypeface(typeface);
	      holder.tvName.setBackgroundColor(ContextCompat.getColor(ctxt,R.color.help_item_background_color));
	      
	      String docVaccineDate = vaccineList.get(position).getM_vaccinedate().toString();
	      holder.tvDate.setText(docVaccineDate);
	      holder.tvDate.setTypeface(typeface);
		holder.tvName.setBackgroundColor(ContextCompat.getColor(ctxt,R.color.help_item_background_color));
	      
	      String docVaccineNextDueDate = vaccineList.get(position).getM_nxtvaccinedate().toString();
	      holder.tvDueDate.setText(docVaccineNextDueDate);
	      holder.tvDueDate.setTypeface(typeface);
		holder.tvName.setBackgroundColor(ContextCompat.getColor(ctxt,R.color.help_item_background_color));
	      
	      String docVaccineDoctor = ((DoctorDetails)vaccineList.get(position).getM_doc()).getM_firstname().toString();
	      holder.tvDoctor.setText(docVaccineDoctor);
	      holder.tvDoctor.setTypeface(typeface);
		holder.tvName.setBackgroundColor(ContextCompat.getColor(ctxt,R.color.help_item_background_color));

	      return vi;
	}
	
	/*
	 * Information about the vaccine 
	 * Name
	 * Image - [next version]
	 * */
	public static class VaccineDataViewHolder{
		TextView tvName;
		TextView tvDate;
		TextView tvDueDate;
		TextView tvDoctor;
	}

}

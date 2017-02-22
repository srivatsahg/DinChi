
/*
 * @Author	:	Srivatsa Haridas
 * @Date	:	25th October 2013
 * @Desc	:	Centralized datasource for accessing database
 * */

package dinchi.org.petshots.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import dinchi.org.petshots.utils.PetDetail;

import java.util.ArrayList;
import java.util.List;

import dinchi.org.petshots.utils.DoctorDetails;
import dinchi.org.petshots.utils.VaccineDetails;

public class CentralDataSource{
	
	private static final String LOG_CLASS = "[DataSource]";
	
	SQLiteDatabase database;
	dinchi.org.petshots.database.DatabaseHelper dbhelper;
	
	private boolean isClosed;
	
	public static String getLogClass() {
		return LOG_CLASS;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public dinchi.org.petshots.database.DatabaseHelper getDbhelper() {
		return dbhelper;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public String[] getPetColumns() {
		return petColumns;
	}

	public String[] getVetcolumns() {
		return vetcolumns;
	}

	public String[] getVaccinecolumns() {
		return vaccinecolumns;
	}



	private String[] petColumns = {dinchi.org.petshots.database.PetTable.KEY_PETID, dinchi.org.petshots.database.PetTable.KEY_NAME, dinchi.org.petshots.database.PetTable.KEY_DOB,
			dinchi.org.petshots.database.PetTable.KEY_SEX, dinchi.org.petshots.database.PetTable.KEY_AGE, dinchi.org.petshots.database.PetTable.KEY_MICCHIPID,
			dinchi.org.petshots.database.PetTable.KEY_REGNO};

	private String[] vetcolumns =  {dinchi.org.petshots.database.VetTable.KEY_CID, dinchi.org.petshots.database.VetTable.KEY_TITLE,
			dinchi.org.petshots.database.VetTable.KEY_FIRST_NAME, dinchi.org.petshots.database.VetTable.KEY_LAST_NAME,
			dinchi.org.petshots.database.VetTable.KEY_EMAIL, dinchi.org.petshots.database.VetTable.KEY_PHONE_PRIMARY,
			dinchi.org.petshots.database.VetTable.KEY_PHONE_SECONDARY, dinchi.org.petshots.database.VetTable.KEY_ADDRESS};
	
	private String[] vaccinecolumns =  {dinchi.org.petshots.database.VaccineTable.KEY_VID, dinchi.org.petshots.database.VaccineTable.KEY_VACCINE_NAME, dinchi.org.petshots.database.VaccineTable.KEY_PET, dinchi.org.petshots.database.VaccineTable.KEY_DOC,
										dinchi.org.petshots.database.VaccineTable.KEY_VACCINEDATE, dinchi.org.petshots.database.VaccineTable.KEY_NXTVACCINEDATE, dinchi.org.petshots.database.VaccineTable.KEY_REMARKS};

	/*
	 * Constructor
	 * */
	public CentralDataSource(Context context){
		dbhelper = dinchi.org.petshots.database.DatabaseHelper.getInstance(context);
	}
	
	/*
	 * Open DB
	 * */
	public void open() throws SQLiteException{
		database = dbhelper.getWritableDatabase();
	}
	
	/*	
	 * Close the DB connection
	 * */
	public void close(){
		dbhelper.CloseDB();
		isClosed = true;
	}
	
	/*
	 * 
	 * */
	public VaccineDetails createVaccineDetail(VaccineDetails vaccData){
		
		ContentValues values = new ContentValues();
		
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_VACCINE_NAME, vaccData.getM_vaccinename());
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_PET, vaccData.getM_pet().getP_id());
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_DOC, vaccData.getM_doc().getM_id());
		
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_VACCINEDATE,vaccData.getM_vaccinedate());
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_NXTVACCINEDATE,vaccData.getM_nxtvaccinedate());
		values.put(dinchi.org.petshots.database.VaccineTable.KEY_REMARKS,vaccData.getM_remarks());
		
		long insertId = database.insert(dinchi.org.petshots.database.VaccineTable.TABLE_VACCINE, null,
			        values);
		
		Log.i(LOG_CLASS,"<<---- Insert vaccine details success !!!---->>");
		
		Cursor cursor = database.query(dinchi.org.petshots.database.VaccineTable.TABLE_VACCINE,
				vaccinecolumns, dinchi.org.petshots.database.VaccineTable.KEY_VID + " = " + insertId, null,
		        null, null, null);
		cursor.moveToFirst();
		
		Log.i(LOG_CLASS,"<<---- refreshed vaccine details retrieved !!!---->>");
		
		VaccineDetails newVaccineEntry = cursorToVaccineDetail(cursor);
		cursor.close();
		return newVaccineEntry;
	}
	
	 
	 /*
	  * Return the list of available vets
	  * */
	 public List<VaccineDetails> getAllVaccineInformation() {
		    List<VaccineDetails> vaccines = new ArrayList<VaccineDetails>();

		    Cursor cursor = database.query(dinchi.org.petshots.database.VaccineTable.TABLE_VACCINE,
		    		vaccinecolumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    
		    while (!cursor.isAfterLast()) {
		    	VaccineDetails vac = cursorToVaccineDetail(cursor);
		      vaccines.add(vac);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    Log.i(LOG_CLASS, "<<---- Retrieved list of available doctors ---->>");
		    return vaccines;
		  }
	 
	 /*
	  * Return the list of available vets
	  * */
	 public List<VaccineDetails> getAllVaccineInformation(long id) {
		    List<VaccineDetails> vaccines = new ArrayList<VaccineDetails>();

		    Cursor cursor = database.query(dinchi.org.petshots.database.VaccineTable.TABLE_VACCINE,
		    		vaccinecolumns, dinchi.org.petshots.database.VaccineTable.KEY_PET + " = " + id, null, null, null, null);
		    
		    cursor.moveToFirst();
		    
		    while (!cursor.isAfterLast()) {
		    	VaccineDetails vac = cursorToVaccineDetail(cursor);
		      vaccines.add(vac);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    Log.i(LOG_CLASS, "<<---- Retrieved list of available doctors ---->>");
		    return vaccines;
		  }
	 
	 /*
	  * Return the list of available vets
	  * */
	 public List<PetDetail> getAllPets() {
		    List<PetDetail> petsCollection = new ArrayList<PetDetail>();

		    Cursor cursor = database.query(dinchi.org.petshots.database.PetTable.TABLE_PET,
		    		petColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    
		    while (!cursor.isAfterLast()) {
		    	PetDetail petEntry = cursorToPetDetail(cursor);
		    	petsCollection.add(petEntry);
		    	cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    Log.i(LOG_CLASS, "<<---- Retrieved list of available pets ---->>");
		    return petsCollection;
		  }
	 
	 /*
	  * Return the list of available vets
	  * */
	 public List<DoctorDetails> getAllDoctors() {
		    List<DoctorDetails> docs = new ArrayList<DoctorDetails>();

		    Cursor cursor = database.query(dinchi.org.petshots.database.VetTable.TABLE_VET,
		    		vetcolumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    
		    while (!cursor.isAfterLast()) {
		    	DoctorDetails doc = cursorToDoctorDetail(cursor);
		      docs.add(doc);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    Log.i(LOG_CLASS, "<<---- Retrieved list of available doctors ---->>");
		    return docs;
		  }
		
		
	/*
	 * Doctor detail from the db
	 * */
	private VaccineDetails cursorToVaccineDetail(Cursor cursor) {
		
		Log.i(LOG_CLASS, "<<---- cursorToVaccineDetail entered ---- >>");
		
		VaccineDetails vac = new VaccineDetails();
	    //Vaccine id 
	    vac.setM_vacid(cursor.getLong(0));
	    //Vaccine name
	    vac.setM_vaccinename(cursor.getString(1));
	    //pet detail
	    long petid = cursor.getLong(2);
	    PetDetail pet = retrievePetDetail(petid);
		vac.setM_pet(pet);
		//vet detail 
		long vetid = cursor.getLong(3);
	    DoctorDetails vet = retrieveVetDetail(vetid);
		vac.setM_doc(vet);
	    //Vaccine date
		vac.setM_vaccinedate(cursor.getString(4));
		//Vaccine next due date
		vac.setM_nxtvaccinedate(cursor.getString(5));
		//Vaccine remarks
		vac.setM_remarks(cursor.getString(6));
		
		Log.i(LOG_CLASS, "<<---- before cursorToVaccineDetail exit ---- >>");
		return vac;
	  }
	
		/*
		 * Retrieves pet information
		 * */
		public PetDetail retrievePetDetail(long petid){
			
			Log.i(LOG_CLASS, "<<---- Entered inside retrievePetDetail with petid : " + petid + " ---- >>");
			
			PetDetail petInfo = null;
			Cursor petcursor = database.query(dinchi.org.petshots.database.PetTable.TABLE_PET, petColumns,
					dinchi.org.petshots.database.PetTable.KEY_PETID + " = " + petid, null, null, null, null);
			petcursor.moveToFirst();
	
			while (!petcursor.isAfterLast()) {
				petInfo = cursorToPetDetail(petcursor);
				petcursor.moveToNext();
			}
			// make sure to close the cursor
			petcursor.close();
			
			Log.i(LOG_CLASS, "<<---- Retrieved the details for petid : " + petid + " inside retrievePetDetail Success !!---- >>");
			
			return petInfo;
		}
	
 		/*
		 * Pet detail from the db
		 * */
		private PetDetail cursorToPetDetail(Cursor cursor) {
			Log.i(LOG_CLASS, "<<----cursorToPetDetail entered ---->> ");
			PetDetail pet = new PetDetail();
		    pet.setP_id(cursor.getLong(0));
		    pet.setName(cursor.getString(1));
		    pet.setDob(cursor.getString(2));
		    pet.setSex(cursor.getString(3));
		    pet.setAge(cursor.getString(4));
		    pet.setMchip(cursor.getString(5));
		    pet.setRegno(cursor.getString(6));
			Log.i(LOG_CLASS, "<<----cursorToPetDetail success !! ---->> ");		    
		    return pet;
		  }
		
	/*
	 * Retrieves vet information
	 * */
	public DoctorDetails retrieveVetDetail(long vetid){
		Log.i(LOG_CLASS, "<<----retrieveVetDetail entered ---->> ");
		
		DoctorDetails vetInfo = null;
		Cursor vetcursor = database.query(dinchi.org.petshots.database.VetTable.TABLE_VET, vetcolumns,
				dinchi.org.petshots.database.VetTable.KEY_CID + " = " + vetid, null, null, null, null);
		vetcursor.moveToFirst();

		while (!vetcursor.isAfterLast()) {
			vetInfo = cursorToDoctorDetail(vetcursor);
			vetcursor.moveToNext();
		}
		// make sure to close the cursor
		vetcursor.close();
		Log.i(LOG_CLASS, "<<----retrieveVetDetail success !! ---->> ");
		return vetInfo;
	}
	
	/*
	 * Doctor detail from the db
	 * */
	private DoctorDetails cursorToDoctorDetail(Cursor cursor) {
		Log.i(LOG_CLASS, "<<----cursorToDoctorDetail entered ---->> ");
	    DoctorDetails doc = new DoctorDetails();
	    
	    doc.setM_id(cursor.getLong(0));
	    doc.setM_title(cursor.getString(1));
	    doc.setM_firstname(cursor.getString(2));
	    doc.setM_lastname(cursor.getString(3));
	    doc.setM_email(cursor.getString(4));
	    doc.setM_phonePrimary(cursor.getString(5));
	    doc.setM_phoneSecondary(cursor.getString(6));
	    doc.setM_address(cursor.getString(7));
	    Log.i(LOG_CLASS, "<<----cursorToDoctorDetail entered ---->> ");
	    return doc;
	  }
	
	/*
	 * 
	 * */
	public DoctorDetails createDoctorDetail(DoctorDetails docData){
		Log.i(LOG_CLASS, "<<----createDoctorDetail entered ---->> ");
		ContentValues values = new ContentValues();
		
		values.put(dinchi.org.petshots.database.VetTable.KEY_TITLE,docData.getM_title());
		values.put(dinchi.org.petshots.database.VetTable.KEY_FIRST_NAME, docData.getM_firstname());
		values.put(dinchi.org.petshots.database.VetTable.KEY_LAST_NAME, docData.getM_lastname());
		values.put(dinchi.org.petshots.database.VetTable.KEY_EMAIL, docData.getM_email());
		values.put(dinchi.org.petshots.database.VetTable.KEY_PHONE_PRIMARY, docData.getM_phonePrimary());
		values.put(dinchi.org.petshots.database.VetTable.KEY_PHONE_SECONDARY,docData.getM_phoneSecondary());
		values.put(dinchi.org.petshots.database.VetTable.KEY_ADDRESS, docData.getM_address());
		
		long insertId = database.insert(dinchi.org.petshots.database.VetTable.TABLE_VET, null,
			        values);
		
		Cursor cursor = database.query(dinchi.org.petshots.database.VetTable.TABLE_VET,
				vetcolumns, dinchi.org.petshots.database.VetTable.KEY_CID + " = " + insertId, null,
		        null, null, null);
		cursor.moveToFirst();
		
		DoctorDetails newDoc = cursorToDoctorDetail(cursor);
		cursor.close();
		 Log.i(LOG_CLASS, "<<----createDoctorDetail success ---->> ");
		return newDoc;
	}
	
	/*
	 * Delete the vet information
	 * */
	 public void deleteDoctorDetail(DoctorDetails doc) {
		 Log.i(LOG_CLASS, "Get the doctor id before deleting the record ");
		    long id = doc.getM_id();
		    Log.i(LOG_CLASS, "Doctor Detail deleted for id : " + id);
		    database.delete(dinchi.org.petshots.database.VetTable.TABLE_VET, dinchi.org.petshots.database.VetTable.KEY_CID
		        + " = " + id, null);
		  }
	
	 /*
		 * Delete the pet information
		 * */
		 public void deletePetDetail(PetDetail pet) {
			 Log.i(LOG_CLASS, "Get the pet id before deleting the record ");
			    long id = pet.getP_id();
			    Log.i(LOG_CLASS, "Pet Detail deleted for id : " + id);
			    database.delete(dinchi.org.petshots.database.PetTable.TABLE_PET, dinchi.org.petshots.database.PetTable.KEY_PETID
			        + " = " + id, null);
			  }
		 
		 
		 
		/*
		 * Pet Details entry
		 * */
		public PetDetail createPetDetail(PetDetail petData){
			
			ContentValues values = new ContentValues();
			
			values.put(dinchi.org.petshots.database.PetTable.KEY_NAME, petData.getName());
			values.put(dinchi.org.petshots.database.PetTable.KEY_AGE, petData.getAge());
			values.put(dinchi.org.petshots.database.PetTable.KEY_DOB, petData.getDob());
			values.put(dinchi.org.petshots.database.PetTable.KEY_SEX, petData.getSex());
			values.put(dinchi.org.petshots.database.PetTable.KEY_MICCHIPID, petData.getMchip());
			values.put(dinchi.org.petshots.database.PetTable.KEY_REGNO, petData.getRegno());
			
			long insertId = database.insert(dinchi.org.petshots.database.PetTable.TABLE_PET, null,
				        values);
			
			Cursor cursor = database.query(dinchi.org.petshots.database.PetTable.TABLE_PET,
					petColumns, dinchi.org.petshots.database.PetTable.KEY_PETID + " = " + insertId, null,
			        null, null, null);
			cursor.moveToFirst();
			
			PetDetail newPet = cursorToPetDetail(cursor);
			cursor.close();
			return newPet;
		}
		
		
		/*
		 * Update pet information details
		 * */
		public PetDetail updatePetInformation(PetDetail petData){
			
			try{
				
			ContentValues values = new ContentValues();
			values.put(dinchi.org.petshots.database.PetTable.KEY_NAME, petData.getName());
			values.put(dinchi.org.petshots.database.PetTable.KEY_AGE, petData.getAge());
			values.put(dinchi.org.petshots.database.PetTable.KEY_DOB, petData.getDob());
			values.put(dinchi.org.petshots.database.PetTable.KEY_SEX, petData.getSex());
			values.put(dinchi.org.petshots.database.PetTable.KEY_MICCHIPID, petData.getMchip());
			values.put(dinchi.org.petshots.database.PetTable.KEY_REGNO, petData.getRegno());
			
			int iUpdatedRows = database.update(dinchi.org.petshots.database.PetTable.TABLE_PET, values, dinchi.org.petshots.database.PetTable.KEY_PETID + " = " + petData.getP_id(), null);
			
			Cursor cursor = database.query(dinchi.org.petshots.database.PetTable.TABLE_PET,
					petColumns, dinchi.org.petshots.database.PetTable.KEY_PETID + " = " + petData.getP_id(), null,
			        null, null, null);
			cursor.moveToFirst();
			
			PetDetail updatedPetData = cursorToPetDetail(cursor);
			cursor.close();
			return updatedPetData;
			
			}
			catch(SQLiteException updateException){
				return null;
			}
		}
		
		/*
		 * Update pet information details
		 * */
		public DoctorDetails updateDocInformation(DoctorDetails docData){
			
			try{
				
			ContentValues values = new ContentValues();
			values.put(dinchi.org.petshots.database.VetTable.KEY_FIRST_NAME, docData.getM_firstname());
			values.put(dinchi.org.petshots.database.VetTable.KEY_LAST_NAME, docData.getM_lastname());
			
			values.put(dinchi.org.petshots.database.VetTable.KEY_EMAIL,docData.getM_email());
			values.put(dinchi.org.petshots.database.VetTable.KEY_PHONE_PRIMARY,docData.getM_phonePrimary());
			values.put(dinchi.org.petshots.database.VetTable.KEY_PHONE_SECONDARY,docData.getM_phoneSecondary());
			values.put(dinchi.org.petshots.database.VetTable.KEY_ADDRESS,docData.getM_address());
			
			
			int iUpdatedRows = database.update(dinchi.org.petshots.database.VetTable.TABLE_VET, values, dinchi.org.petshots.database.VetTable.KEY_CID + " = " + docData.getM_id(), null);
			
			Cursor cursor = database.query(dinchi.org.petshots.database.VetTable.TABLE_VET,
					vetcolumns, dinchi.org.petshots.database.VetTable.KEY_CID + " = " + docData.getM_id(), null,
			        null, null, null);
			cursor.moveToFirst();
			
			DoctorDetails updatedVetData = cursorToDoctorDetail(cursor);
			cursor.close();
			return updatedVetData;
			
			}
			catch(SQLiteException updateException){
				return null;
			}
		}
	
	
}

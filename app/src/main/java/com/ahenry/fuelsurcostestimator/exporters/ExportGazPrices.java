package com.ahenry.fuelsurcostestimator.exporters;

import com.ahenry.fuelsurcostestimator.models.FileNameAndMapToSave;

import android.os.AsyncTask;

abstract class ExportGazPrices extends AsyncTask<FileNameAndMapToSave, Void, Boolean>{

	protected abstract Boolean doInBackground(FileNameAndMapToSave... params);
	
}

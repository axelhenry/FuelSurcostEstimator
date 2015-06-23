package com.ahenry.fuelsurcostestimator.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.R;

public class ConnectionDialog extends DialogFragment {

	
	public ConnectionDialog(){
		
	}
	
	public static ConnectionDialog newInstance(String title){
		ConnectionDialog diag = new ConnectionDialog();
		Bundle args = new Bundle();
        args.putString("title", title);
        diag.setArguments(args);
		
		return diag;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		Activity a = getActivity();
		
		Log.d("gazolinePrices()","Activiy is null ? : "+(a == null ? true :false));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        Log.d("gazolinePrices()","builder created");
        alertDialogBuilder.setTitle(title);
        Log.d("gazolinePrices()","title set");
        alertDialogBuilder.setMessage(getResources().getString(R.string.connection_alert_message));
        Log.d("gazolinePrices()","message set");
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            	startActivity(intent);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Log.d("gazolinePrices()","builder constructed, ready to be returned");
        return alertDialogBuilder.create();
    }

}

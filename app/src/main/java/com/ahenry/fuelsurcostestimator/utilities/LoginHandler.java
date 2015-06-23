package com.ahenry.fuelsurcostestimator.utilities;

import android.content.Context;
import android.net.Uri;

import com.github.kevinsawicki.http.HttpRequest;

import org.bouncycastle.util.io.pem.PemObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


/**
 * Created by axel on 22/06/15.
 */
public class LoginHandler {

    /**
     * This class allows application to logon to the server
     * Step 1 if no rsa key stored in  private storage, get public rsa key from the server
     * Step 2, encode uuid, appname and #launches with public rsa key and send this to the server
     * Step 3
     * */

    private Context mContext;
    private Uri mUri;
    private String mPublicRSAKey;

    public LoginHandler(Context aContext, Uri aServerUri){
        mContext = aContext;
        mUri = aServerUri;
    }

    /***
     *
     * @return
     */
    public boolean publicKeyExists(){

        String str="";
        StringBuffer aSBuffer = new StringBuffer();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.openFileInput("publicKey.pem")))){

            while ((str = reader.readLine()) != null) {
                aSBuffer.append(str + "\n" );
            }

            mPublicRSAKey = aSBuffer.toString();
            return true;
        }catch(IOException ioe){

        }
        return false;

    }

    public String checkForPublicKey(String filePath){
        PemFile pf = new PemFile(filePath);
        PemObject mPem = pf.getPemObject();
        return "";
    }

    public boolean getPublicKey(){
        try{
            HttpRequest request = HttpRequest.get(ConstantUtilities.mPublicKeyUri);
            if(request.ok()){
                try(OutputStream out = mContext.openFileOutput("publicKey.pem", Context.MODE_PRIVATE)){
                    request.receive(out);
                }catch(IOException ioe){

                }
            }
        }catch(HttpRequest.HttpRequestException re){

        }
        return false;
    }
}

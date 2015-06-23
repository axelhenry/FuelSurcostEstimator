package com.ahenry.fuelsurcostestimator.utilities;

import android.support.annotation.Nullable;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by axel on 22/06/15.
 */
public class PemFile {

    private PemObject pemObject;

    public PemFile(String filePath) {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(filePath)))) {
            this.pemObject = pemReader.readPemObject();
        } catch (FileNotFoundException ioe) {
            this.pemObject = null;
        } catch (IOException ioe) {
            this.pemObject = null;
        }
    }

    public PemObject getPemObject() {
        return pemObject;
    }

}

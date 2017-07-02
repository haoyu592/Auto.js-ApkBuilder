package com.stardust.autojs.apkbuilder.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stardust.autojs.apkbuilder.ApkBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12345);
                return;
            }
        }
        doSign();
    }

    private void doSign() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "autojs.keystore");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            write(getAssets().open("autojs.keystore"), fos, new byte[4096]);
            fos.close();
            File inApk = new File(Environment.getExternalStorageDirectory(), "unsign.apk");
            inApk.createNewFile();
            fos = new FileOutputStream(inApk);
            write(getAssets().open("test.apk"), fos, new byte[4096]);
            fos.close();
            File outApk = new File(Environment.getExternalStorageDirectory(), "signed.apk");
            outApk.createNewFile();
            ApkBuilder builder = new ApkBuilder(inApk, outApk);
            File tmpDir = new File(Environment.getExternalStorageDirectory(), "tmp/");
            tmpDir.mkdirs();
            builder.signOutputApk(tmpDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doSign();
    }

    private void write(InputStream inputStream, OutputStream out, byte[] buffer) throws IOException {
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}

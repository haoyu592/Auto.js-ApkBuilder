package com.stardust.autojs.apkbuilder;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pxb.android.tinysign.TinySign;

/**
 * Created by Stardust on 2017/7/1.
 */

public class ApkBuilder {


    private InputStream mApkInputStream;
    private File mInApkFile;
    private File mOutApkFile;
    private byte[] mBuffer;

    public ApkBuilder(InputStream apkInputStream, File outApkFile, int bufferSize) {
        mApkInputStream = apkInputStream;
        mOutApkFile = outApkFile;
        mBuffer = new byte[bufferSize];
    }

    public ApkBuilder(InputStream apkInputStream, File outFile) throws FileNotFoundException {
        this(apkInputStream, outFile, 4096);
    }

    public ApkBuilder(File inFile, File outFile) throws FileNotFoundException {
        this(new FileInputStream(inFile), outFile, 4096);
        mInApkFile = inFile;
    }

    public void replaceFiles(Map<String, File> replacement) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(mOutApkFile));
        ZipInputStream original = new ZipInputStream(mApkInputStream);
        ZipEntry entry;
        while ((entry = original.getNextEntry()) != null) {
            File file = replacement.get(entry.getName());
            out.putNextEntry(new ZipEntry(entry.getName()));
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                write(new FileInputStream(file), out);
                fis.close();
            } else {
                write(original, out);
            }
            original.closeEntry();
            out.closeEntry();
        }
        original.close();
        out.close();
    }


    public void signOutputApk(File tmpDir) throws Exception {
        InputStream in = new FileInputStream(mInApkFile);
        extract(in, tmpDir);
        in.close();
        FileOutputStream fos = new FileOutputStream(mOutApkFile);
        TinySign.sign(tmpDir, fos);
        fos.close();
    }

    public void extract(InputStream in, File dir) throws IOException {
        ZipInputStream zis = new ZipInputStream(in);
        for (ZipEntry e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) {
            String name = e.getName();
            if (!e.isDirectory()) {
                File file = new File(dir, name);
                file.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(file);
                write(zis, fos);
                fos.close();
            }
        }
    }


    private void write(InputStream inputStream, OutputStream out) throws IOException {
        int len;
        while ((len = inputStream.read(mBuffer)) > 0) {
            out.write(mBuffer, 0, len);
        }
    }
}

package com.stardust.autojs.apkbuilder;


import com.stardust.autojs.apkbuilder.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Stardust on 2017/7/1.
 */

public class ApkBuilder {


    private File mOutApkFile;
    private ApkPackager mApkPackager;
    private ManifestEditor mManifestEditor;
    private String mWorkspacePath;

    public ApkBuilder(InputStream apkInputStream, File outApkFile, String workspacePath) {
        mOutApkFile = outApkFile;
        mWorkspacePath = workspacePath;
        mApkPackager = new ApkPackager(apkInputStream, mWorkspacePath);
    }

    public ApkBuilder(File inFile, File outFile, String workspacePath) throws FileNotFoundException {
        this(new FileInputStream(inFile), outFile, workspacePath);
    }

    public ApkBuilder prepare() throws IOException {
        new File(mWorkspacePath).mkdirs();
        mApkPackager.unzip();
        mManifestEditor = new ManifestEditor(new FileInputStream(getManifestFile()));
        return this;
    }

    private File getManifestFile() {
        return new File(mWorkspacePath, "AndroidManifest.xml");
    }

    public ManifestEditor editManifest() {
        return mManifestEditor;
    }

    public ApkBuilder replaceFile(String relativePath, String newFilePath) throws IOException {
        StreamUtils.write(new FileInputStream(new File(mWorkspacePath, relativePath)),
                new FileOutputStream(newFilePath));
        return this;
    }

    public ApkBuilder build() throws IOException {
        mManifestEditor.writeTo(new FileOutputStream(getManifestFile()));
        return this;
    }

    public ApkBuilder sign() throws Exception {
        mApkPackager.repackage(mOutApkFile.getPath());
        return this;
    }

    public ApkBuilder cleanWorkspace() {
        delete(new File(mWorkspacePath));
        return this;
    }

    private void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        for (File child : file.listFiles()) {
            delete(child);
        }
        file.delete();
    }

}

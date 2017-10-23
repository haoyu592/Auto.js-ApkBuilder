package com.cmy.parser.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cmy on 2017/6/7
 */
public class ResTableLibrary extends ResTableChunk {

    // entry count
    public int count;

    public List<ResTableLibraryEntry> resTableLibraryEntries;

    public static class ResTableLibraryEntry {
        public int packageId;
        public byte[] packageName;

        @Override
        public String toString() {
            return "ResTableLibraryEntry{" +
                    "packageId=" + packageId +
                    ", packageName=" + new String(packageName) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResTableLibrary{" +
                "count=" + count +
                ", resTableLibraryEntries=" + resTableLibraryEntries +
                "} " + super.toString();
    }
}

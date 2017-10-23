package com.cmy.parser.bean.tabletype;

import com.cmy.parser.bean.ResTableConfig;
import com.cmy.parser.bean.ResTableChunk;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cmy on 2017/6/7
 */
public class ResTableType extends ResTableChunk {

    // The tableType identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the tableType bits in a
    // resource identifier).  0 is invalid.
    public byte typeId;
    // 保留字段，一定为0
    public byte res0;
    // 保留字段，一定为0
    public short res1;
    // 资源项组数
    public int entryCount;
    // TableEntry起始位置
    public int entryStart;

    public ResTableConfig resTableConfig;

    //ResTableEntry偏移数组
    public int[] resTableEntryOffsets;

    public List<ResTableEntry> resTableEntryList;

    @Override
    public String toString() {
        return "ResTableType{" +
                "typeId=" + typeId +
                ", res0=" + res0 +
                ", res1=" + res1 +
                ", entryCount=" + entryCount +
                ", entryStart=" + entryStart +
                ", resTableConfig=" + resTableConfig +
                ", resTableEntryOffsets=" + Arrays.toString(resTableEntryOffsets) +
                ", resTableEntryList=" + resTableEntryList +
                "} " + super.toString();
    }
}

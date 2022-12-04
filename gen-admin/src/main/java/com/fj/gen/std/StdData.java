package com.fj.gen.std;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 默认excel数据读取类
 * @author fjding
 * @date 2022/11/30
 */
@Data
public class StdData {

    @ExcelProperty("子包名")
    private String subPkg;


    @ExcelProperty("表注释")
    private String tableMsg;

    @ExcelProperty("表名")
    private String tableName;

    @ExcelProperty("排序")
    private String order;

    @ExcelProperty("字段注释")
    private String filedMsg;

    @ExcelProperty("字段名")
    private String filedName;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("子类名")
    private String subTableName;

    /**
     * true 或者 false
     */
    @ExcelProperty("内部类")
    private String subClass;

    @ExcelProperty("lombok")
    private String lombok = Constants.TRUE;

}

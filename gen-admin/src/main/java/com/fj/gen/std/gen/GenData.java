package com.fj.gen.std.gen;

import com.fj.gen.std.handler.ClassModel;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>生成数据模板<p/>
 *
 * @author fjding
 * @date 2022/12/1
 */
@Data
public class GenData {

    private Set<String> typeSet;

    private List<ClassModel> dataList;

    /**
     * 包名
     */
    private String pkg;

    private String tableMsg;

    private String tableName;

    private String lombok;

    private String author;

    private Date date = new Date();

}

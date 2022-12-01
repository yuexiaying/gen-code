package com.fj.gen.std.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据模型
 * @author fjding
 * @date 2022/12/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassModel {

    private String subPkg;

    private String tableMsg;

    private String tableName;

    private String order;

    private String filedMsg;

    private String filedName;

    private String type;

    private String lombok;

    /**
     * 子类
     */
    private List<ClassModel> subClassList;

}

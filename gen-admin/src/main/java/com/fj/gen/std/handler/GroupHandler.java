package com.fj.gen.std.handler;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.fj.gen.std.Constants;
import com.fj.gen.std.StdData;
import com.fj.gen.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将数据进行分组处理
 *
 * @author fjding
 * @date 2022/12/1
 */
public class GroupHandler implements DataHandler {

    /**
     * 是子类信息的存入该map
     */
    private Map<String, StdData> stdDataMap = new HashMap<>();

    private static final List<String> subClassList = ListUtil.toList("List", "Object");

    @Override
    public boolean execute(HandlerResult handlerResult) {
        List<StdData> list = handlerResult.getList();
        // 类名、序号、实体
        Table<String, ClassModel> modelTable = Table.getInstance();

        for (StdData stdData : list) {
            String order = stdData.getOrder();

            ClassModel classModel = ClassModel.builder()
                    .subPkg(stdData.getSubPkg())
                    .tableName(stdData.getTableName())
                    .tableMsg(stdData.getTableMsg())
                    .order(order)
                    .filedName(stdData.getFiledName())
                    .filedMsg(stdData.getFiledMsg())
                    .type(stdData.getType())
                    .lombok(StrUtil.isEmpty(stdData.getLombok()) ? Constants.TRUE : stdData.getLombok())
                    .build();
            if (subClassList.contains(stdData.getType())) {
                stdDataMap.put(order, stdData);
                classModel.setType(getSubType(stdData));
            }
            // 判断是一级类，还是子类
            if (order.contains(".")) {
                String subKey = StrUtil.sub(order, 0, StrUtil.lastIndexOfIgnoreCase(order, "."));
                StdData paStdData = stdDataMap.get(subKey);
                String subClassName = getSubClassName(paStdData);
                classModel.setTableName(subClassName);
                classModel.setTableMsg(paStdData.getFiledMsg());
                // 判断是内部类
                if (Constants.TRUE.equals(stdData.getSubClass())) {
                    ClassModel paClassModel = modelTable.get(subKey);
                    paClassModel.getSubClassList().add(classModel);
                } else {
                    modelTable.add(subClassName, classModel.getOrder(), classModel);
                }
            } else {
                modelTable.add(classModel.getTableName(), classModel.getOrder(), classModel);
            }
        }

        handlerResult.setModelTable(modelTable);
        return false;
    }

    private String getSubType(StdData stdData){
        String subClassName = getSubClassName(stdData);
        if (Constants.OBJECT.equals(stdData.getType())){
            return subClassName;
        }
        if (Constants.LIST.equals(stdData.getType())){
            return "List<"+subClassName+">";
        }
        return null;
    }

    private String getSubClassName(StdData stdData) {
        if (StrUtil.isNotEmpty(stdData.getSubTableName())) {
            return stdData.getSubTableName();
        }
        String type = stdData.getFiledName();
        if (Constants.LIST.equals(stdData.getType())){
           type= stdData.getFiledName().replace("List","").replace("Array","");
        }
        // todo 去除 s List Array 等
        return StrUtil.upperFirst(type);
    }

    @Override
    public int order() {
        return 10;
    }
}

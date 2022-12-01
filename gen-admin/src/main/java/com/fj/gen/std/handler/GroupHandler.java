package com.fj.gen.std.handler;

import cn.hutool.core.map.TableMap;
import cn.hutool.core.util.StrUtil;
import com.fj.gen.std.Constants;
import com.fj.gen.std.StdData;

import java.util.List;

/**
 * 将数据进行分组处理
 *
 * @author fjding
 * @date 2022/12/1
 */
public class GroupHandler implements DataHandler {

    @Override
    public boolean execute(HandlerResult handlerResult) {
        List<StdData> list = handlerResult.getList();
        TableMap<String,ClassModel> tableMap = new TableMap<>();
        for (StdData stdData : list) {
            ClassModel classModel = ClassModel.builder()
                    .subPkg(stdData.getSubPkg())
                    .tableName(stdData.getTableName())
                    .tableMsg(stdData.getTableMsg())
                    .order(stdData.getOrder())
                    .filedName(stdData.getFiledName())
                    .filedMsg(stdData.getFiledMsg())
                    .type(stdData.getType())
                    .lombok(StrUtil.isEmpty(stdData.getLombok()) ? Constants.TRUE:stdData.getLombok())
                    .build();
            // 基础类使用0标识
            tableMap.put("0",classModel);
        }
        handlerResult.setTableMap(tableMap);
        return false;
    }

    @Override
    public int order() {
        return 10;
    }
}

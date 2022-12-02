package com.fj.gen.std;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.fj.gen.std.gen.GenData;
import com.fj.gen.std.gen.GenExecutes;
import com.fj.gen.std.handler.ClassModel;
import com.fj.gen.std.handler.Executes;
import com.fj.gen.std.handler.HandlerResult;
import com.fj.ui.ModelData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fjding
 * @date 2022/11/30
 */
public class StdReadListener implements ReadListener<StdData> {

    List<StdData> list = new ArrayList<>();

    private ModelData modelData;

    public StdReadListener(ModelData modelData) {
        this.modelData = modelData;
    }

    @Override
    public void invoke(StdData data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * 每个sheet读完后会调用一次
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        GenExecutes genExecutes = new GenExecutes();
        Executes instance = Executes.getInstance();
        HandlerResult result = instance.exec(list);
        for (String key : result.getTableMap().keySet()) {
            List<ClassModel> values = result.getTableMap().getValues(key);
            GenData genData = getGenData(values);
            genExecutes.process(Constants.STD_ENTITY_FTL,  getOutName(genData.getPkg(),genData.getTableName()), genData);
        }
        list.clear();
    }

    private String getOutName(String pkg,String tableName){
        String path =  new File(modelData.getPath()).getParent() +"/" + pkg.replace(".","/")+"/";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return path+ StrUtil.upperFirst(tableName)+".java";
    }

    /**
     * 生成GenData数据
     *
     * @param values
     * @return
     */
    private GenData getGenData(List<ClassModel> values) {
        GenData genData = new GenData();
        genData.setDataList(values);
        genData.setLombok(values.get(0).getLombok());
        genData.setPkg(modelData.getPkg() + "." + values.get(0).getSubPkg());
        genData.setTableMsg(values.get(0).getTableMsg());
        genData.setTableName(values.get(0).getTableName());
        Set<String> typeSet = new HashSet<>();
        // todo 注意Object和List类型
        values.forEach(e -> typeSet.add(e.getType()));
        genData.setTypeSet(typeSet);
        genData.setAuthor(modelData.getAuthor());
        return genData;
    }
}

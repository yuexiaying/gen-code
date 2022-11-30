package com.fj.ui;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据配置文件操作类
 * @author fjding
 * @date 2022/11/13
 */
public class ConfigUtils {

    private static String userHome = System.getProperty("user.home");

    private static File dataFile = new File(userHome + "/gen/config/data.json");

    public static List<ModelData> getData() throws IOException {
        File pathFile = new File(userHome + "/gen/config");
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        List<ModelData> modelDataList;
        if (!dataFile.exists() || dataFile.length() == 0) {
            dataFile.createNewFile();
            modelDataList = new ArrayList<>();
        } else {
            FileReader fileReader = new FileReader(dataFile);
            String str = fileReader.readString();
            modelDataList = JSONUtil.toBean(str, new TypeReference<List<ModelData>>() {
            }, false);
        }
        return modelDataList;
    }

    public static void addData(ModelData modelData) throws IOException {
        List<ModelData> modelDataList = getData();
        modelDataList.add(modelData);
        FileWriter fileWriter = new FileWriter(dataFile);
        String str = JSONUtil.toJsonStr(modelDataList);
        fileWriter.write(str);
        fileWriter.close();
    }

    @SneakyThrows
    public static void delete(Set<String> set){
        List<ModelData> modelDataList = getData();
        modelDataList.removeIf(e -> set.contains(e.getName()));
        FileWriter fileWriter = new FileWriter(dataFile);
        String str = JSONUtil.toJsonStr(modelDataList);
        fileWriter.write(str);
        fileWriter.close();
    }

    @SneakyThrows
    public static void delete(String name){
        Set<String> set = new HashSet<>();
        set.add(name);
        delete(set);
    }

}

package com.fj.ui;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据配置文件操作类
 *
 * @author fjding
 * @date 2022/11/13
 */
@Slf4j
public class ConfigUtils {

    private static String userHome = System.getProperty("user.home");

    /**
     * 数据文件
     */
    private static File dataFile = new File(userHome + "/gen/config/data.json");

    /**
     * 获得配置的数据
     *
     * @return
     * @throws IOException
     */
    public static List<ModelData> getData() throws IOException {
        File pathFile = new File(userHome + "/gen/config");
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        List<ModelData> modelDataList;
        // 不存在的返回空
        if (!dataFile.exists() || dataFile.length() == 0) {
            dataFile.createNewFile();
            modelDataList = new ArrayList<>();
        } else {
            // 读取数据文件
            FileReader fileReader = new FileReader(dataFile);
            String str = fileReader.readString();
            modelDataList = JSONUtil.toBean(str, new TypeReference<List<ModelData>>() {
            }, false);
        }
        return modelDataList;
    }

    /**
     * 增加数据
     *
     * @param modelData
     * @throws IOException
     */
    public static void addData(ModelData modelData) throws IOException {
        if (ObjectUtil.isNull(modelData)) {
            log.warn("增加的数据为空");
        } else {
            log.info("增加数据：{}", modelData);
        }
        List<ModelData> modelDataList = getData();
        modelDataList.add(modelData);
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            String str = JSONUtil.toJsonStr(modelDataList);
            fileWriter.write(str);
        }
    }

    /**
     * 根据名字删除数据
     *
     * @param set 名字集合
     */
    @SneakyThrows
    public static void delete(Set<String> set) {
        List<ModelData> modelDataList = getData();
        modelDataList.removeIf(e -> set.contains(e.getName()));
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            String str = JSONUtil.toJsonStr(modelDataList);
            fileWriter.write(str);
        }
    }

    @SneakyThrows
    public static void delete(String name) {
        Set<String> set = new HashSet<>();
        set.add(name);
        delete(set);
    }

}

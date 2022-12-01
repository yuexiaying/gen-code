package com.fj.gen.std.gen;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.Writer;

/**
 * 模板生成执行器
 *
 * @author fjding
 * @date 2022/12/1
 */
public class GenExecutes {

    private  Configuration configuration;

     {
        configuration = new Configuration(Configuration.getVersion());
        configuration.setTemplateLoader(new ClassTemplateLoader(GenExecutes.class.getClassLoader(), "/"));
        configuration.setDefaultEncoding("utf-8");
    }

    /**
     * 根据模板路径获得模板
     *
     * @param tName 模板路径
     * @return
     */
    @SneakyThrows
    public  Template getTemplate(String tName) {
        return configuration.getTemplate(tName);
    }

    @SneakyThrows
    public  void process(String tName, String outName, Object data){
        process(getTemplate(tName),outName,data);
    }
    @SneakyThrows
    public  void process(Template template, String outName, Object data){
        Writer writer = new FileWriter(outName);
        template.process(data,writer);
        writer.flush();
        writer.close();
    }
}

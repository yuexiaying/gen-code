package com.fj.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模板数据
 * @author fjding
 * @date 2022/11/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelData {

    private String name;

    private String pkg;

    private String author;

    private String path;

    private boolean checked;

}

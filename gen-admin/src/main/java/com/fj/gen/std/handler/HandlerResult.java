package com.fj.gen.std.handler;

import cn.hutool.core.map.TableMap;
import com.fj.gen.std.StdData;
import lombok.Data;

import java.util.List;

/**
 *
 * @author fjding
 * @date 2022/12/1
 */
@Data
public class HandlerResult {

    private List<StdData> list;

    private TableMap<String,ClassModel> tableMap;


}

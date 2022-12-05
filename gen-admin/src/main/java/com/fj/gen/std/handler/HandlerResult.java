package com.fj.gen.std.handler;

import com.fj.gen.std.StdData;
import com.fj.gen.Table;
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

    private Table<String,ClassModel> modelTable;


}

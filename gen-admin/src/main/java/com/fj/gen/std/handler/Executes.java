package com.fj.gen.std.handler;

import com.fj.gen.std.StdData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 执行器
 *
 * @author fjding
 * @date 2022/12/1
 */
public final class Executes {

    private List<DataHandler> dataHandlerList = new ArrayList<>();

    private Set<Integer> orderSet = new HashSet<>();

    public static Executes getInstance(){
        Executes executes = new Executes();
        executes.addHandler(new GroupHandler());
        return executes;
    }

    public boolean addHandler(DataHandler dataHandler) {
        if (!orderSet.contains(dataHandler.order())) {
            orderSet.add(dataHandler.order());
            dataHandlerList.add(dataHandler);
            dataHandlerList.sort(((o1, o2) -> o1.order() - o2.order()));
            return true;
        }
        return false;
    }

    public HandlerResult exec(HandlerResult handlerResult) {
        for (DataHandler dataHandler : dataHandlerList) {
            dataHandler.execute(handlerResult);
        }
        return handlerResult;
    }

    public HandlerResult exec(List<StdData> list) {
        HandlerResult handlerResult = new HandlerResult();
        handlerResult.setList(list);
        return exec(handlerResult);
    }
}

package com.fj.gen.std.handler;

/**
 * 接口处理器
 *
 * @author fjding
 * @date 2022/12/1
 */
public interface DataHandler {

    /**
     * 执行方法
     * @param handlerResult
     * @return
     */
    boolean execute(HandlerResult handlerResult);

    /**
     * 排序
     * @return
     */
    int order();
}

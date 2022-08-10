package com.github.dreamroute.tracer.starter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import javax.annotation.Resource;
import java.util.Set;

import static com.github.dreamroute.tracer.starter.TracerProperties.TRACER;
import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

/**
 * 过滤器消费端，用于写入trace id
 *
 * @author w.dehai.2021/4/15.15:35
 */
@Activate(group = CONSUMER)
public class TracerConsumer implements Filter {

    @Resource
    private Tracer tracer;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Set<String> keys = tracer.getKeys();
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                String v = tracer.get(key);
                RpcContext.getContext().setAttachment(TRACER + key, v);
            }
        }
        return invoker.invoke(invocation);
    }
}

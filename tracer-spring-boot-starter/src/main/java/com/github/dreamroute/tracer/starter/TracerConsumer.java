package com.github.dreamroute.tracer.starter;

import lombok.Setter;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

/**
 * 过滤器消费端，用于写入trace id
 *
 * @author w.dehai.2021/4/15.15:35
 */
@Activate(group = CONSUMER)
public class TracerConsumer implements Filter {

    @Setter
    Tracer tracer;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (tracer != null) {
            Set<String> keys = tracer.getKeys();
            if (keys != null && !keys.isEmpty()) {
                for (String key : keys) {
                    String v = tracer.get(key);
                    RpcContext.getContext().setAttachment(TracerProperties.TRACER + key, v);
                }
            }
        }
        return invoker.invoke(invocation);
    }
}

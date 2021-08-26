package com.github.dreamroute.tracer.starter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

/**
 * @author w.dehai.2021/4/15.15:35
 */
@Activate(group = CONSUMER)
public class TracerConsumer implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(TracerProperties.TRACE_ID);
        if (!StringUtils.isEmpty(traceId)) {
            RpcContext.getContext().set(TracerProperties.TRACE_ID, traceId);
        }
        return invoker.invoke(invocation);
    }
}

package com.github.dreamroute.tracer.starter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import static com.github.dreamroute.tracer.starter.TracerProperties.TRACE_ID;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * @author w.dehai.2021/4/15.15:35
 */
@Slf4j
@Activate(group = PROVIDER)
public class TracerProvider implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment(TRACE_ID);
        TracerUtil.setTraceId(traceId);
        Result result;
        try {
            result = invoker.invoke(invocation);
        } finally {
            TracerUtil.clearTraceId();
        }
        return result;
    }

}

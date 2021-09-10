package com.github.dreamroute.tracer.starter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.util.StringUtils;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

/**
 * @author w.dehai.2021/4/15.15:35
 */
@Activate(group = CONSUMER)
public class TracerConsumer implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = TracerUtil.getTraceId();
        if (!StringUtils.isEmpty(traceId)) {
            RpcContext.getContext().setObjectAttachment(TracerProperties.TRACE_ID, traceId);
        }
        return invoker.invoke(invocation);
    }
}

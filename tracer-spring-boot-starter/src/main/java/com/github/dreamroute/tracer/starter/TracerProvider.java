package com.github.dreamroute.tracer.starter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.util.HashMap;
import java.util.Map;

import static com.github.dreamroute.tracer.starter.TracerProperties.TRACER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * 过滤器使用端，用于写入trace id
 *
 * @author w.dehai.2021/4/15.15:35
 */
@Slf4j
@Activate(group = PROVIDER)
public class TracerProvider implements Filter {

    @Setter
    Tracer tracer;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (tracer != null) {
            Map<String, Object> attrs = RpcContext.getContext().getObjectAttachments();
            if (attrs != null && !attrs.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                attrs.forEach((k, v) -> {
                    if (k.startsWith(TRACER)) {
                        k = k.replace(TRACER, "");
                        map.put(k, (String) v);
                    }
                });
                if (!map.isEmpty()) {
                    tracer.set(map);
                }
            }
        }
        Result result;
        try {
            result = invoker.invoke(invocation);
        } finally {
            if (tracer != null) {
                tracer.clear();
            }
        }
        return result;
    }

}

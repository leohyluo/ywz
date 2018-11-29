package com.alpha.server.rpc.his;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.server.rpc.diagnosis.UserFeign;
import com.alpha.server.rpc.user.pojo.UserInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HisApiFallbackFactory implements FallbackFactory<HISApiFeign> {

    @Override
    public HISApiFeign create(Throwable throwable) {
        return new HISApiFeign() {
            @Override
            public ResponseMessage getUser(String hospitalCode, String idcard) {
                System.out.println("hystrix fallback..........");
                return WebUtils.buildResponseMessage(ResponseStatus.EXCEPTION);
            }
        };
    }
}

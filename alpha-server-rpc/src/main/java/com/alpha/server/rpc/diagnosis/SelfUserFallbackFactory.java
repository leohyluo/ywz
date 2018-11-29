package com.alpha.server.rpc.diagnosis;

import com.alpha.server.rpc.user.pojo.UserInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SelfUserFallbackFactory implements FallbackFactory<UserFeign> {

    @Override
    public UserFeign create(Throwable e) {
        return new UserFeign() {
            @Override
            public String Authorization() {
                // TODO Auto-generated method stub
                return "fallback cause of ";
            }

            @Override
            public UserInfo getUserInfo() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

}

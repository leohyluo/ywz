package com.alpha.commons.api.tencent.offical.callback;

import com.alpha.commons.api.tencent.offical.dto.CallBackDTO;

public interface CallBackHandler <T extends CallBackDTO> {

	 String handle(T t);

}

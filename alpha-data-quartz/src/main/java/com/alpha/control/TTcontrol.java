package com.alpha.control;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.fegin.SchedualServiceDataAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MR.Wu on 2018-09-03.
 */
@RestController
@RequestMapping("t")
public class TTcontrol {

    @Autowired
    private SchedualServiceDataAdvance schedualServiceDataAdvance;

    @RequestMapping(value = "o", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void dataAdvance() {
        HisRegisterYygh hisRegisterYygh = new HisRegisterYygh();
        hisRegisterYygh.setPhone("13723415915");
        hisRegisterYygh.setCardNo("13723415915");
        schedualServiceDataAdvance.invokeDataAdvanceService(hisRegisterYygh);
    }
}

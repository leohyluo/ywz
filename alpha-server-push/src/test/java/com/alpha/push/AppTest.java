package com.alpha.push;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.push.service.NotifyService;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */

public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void assrt(){
        NotifyService notifyService = SpringContextHolder.getBean("NotifyService");

    }
}

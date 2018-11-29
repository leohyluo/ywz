package com.alpha.user;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    @org.junit.Test
    public void test1() {
        String shit = "";
        String urine = "";
        String appetite = "好";
        String menstrualPeriod = "好";
        StringBuilder  strBuff = new StringBuilder();

        String menstrualPeriod2 = "好".equals(menstrualPeriod) ? "精神可" : "";
        menstrualPeriod2 = "差".equals(menstrualPeriod) ? "精神不佳" : menstrualPeriod2;
        menstrualPeriod2 = "一般".equals(menstrualPeriod) ? "精神一般" : menstrualPeriod2;


        String appetite2 = "好".equals(appetite) ? "食欲可" : "";
        appetite2 = "差".equals(appetite) ? "纳差" : appetite2;
        appetite2 = "一般".equals(appetite) ? "食欲一般" : appetite2;


        String str1 = "好".equals(menstrualPeriod) && "好".equals(appetite) ? "精神食欲可" : "";
        if("".equals(str1)) {
            if(!"".equals(menstrualPeriod2) && !"".equals(appetite2)) {
                str1 = menstrualPeriod2 + "," + appetite2 + "。";
            } else if (!"".equals(menstrualPeriod2) && "".equals(appetite2)) {
                str1 = menstrualPeriod2;
            } else if ("".equals(menstrualPeriod2) && !"".equals(appetite2)) {
                str1 = appetite2;
            }
        }

        String shit2 = "正常".equals(shit) ? "大便正常" : "";
        shit2 = "不成形".equals(shit) ? "大便不成形" : shit2;
        shit2 = "干燥".equals(shit) ? "大便硬结" : shit2;

        String urine2 = "正常".equals(urine) ? "小便正常" : "";
        urine2 = "减少".equals(urine) ? "小便尿量减少" : urine2;
        urine2 = "增多".equals(urine) ? "小便尿量增多" : urine2;

        String str2 = "正常".equals(shit) && "正常".equals(urine) ? "二便正常" :  "";
        if("".equals(str2)) {
            if(!"".equals(shit2) && !"".equals(urine2)) {
                str2 = shit2 + "," + urine2 + "。";
            } else if (!"".equals(shit2) && "".equals(urine2)) {
                str2 = shit2;
            } else if ("".equals(shit2) && !"".equals(urine2)) {
                str2 = urine2;
            }
        }

        if(!"".equals(str1) && !"".equals(str2)) {
            strBuff.append(str1).append("，").append(str2).append("。");
        } else if(!"".equals(str1) && "".equals(str2)) {
            strBuff.append(str1).append("。");
        } else if("".equals(str1) && !"".equals(str2)) {
            strBuff.append(str2).append("。");
        }
    }
}

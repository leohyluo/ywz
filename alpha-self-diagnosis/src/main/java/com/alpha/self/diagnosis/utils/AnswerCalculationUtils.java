package com.alpha.self.diagnosis.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.alpha.commons.enums.Unit;
import com.alpha.commons.util.DateUtils;

public class AnswerCalculationUtils {
	
	private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");  

	public static Double calcWithUnit(String inputAnswer) {
		Unit inputUnit = Unit.containText(inputAnswer);
		if(inputUnit == null) {
			return null;
		}
		Double result = 0.0;
		Double inputAnswerWithoutUnit = Double.valueOf(inputAnswer.replace(inputUnit.getText(), ""));
		if(inputUnit == Unit.MINUTE || inputUnit == Unit.HOUR || inputUnit == Unit.DAY || inputUnit == Unit.WEEK
				|| inputUnit == Unit.MONTH || inputUnit == Unit.SEASON || inputUnit == Unit.YEAR) {
			
			result = DateUtils.toMillSeond(inputAnswerWithoutUnit, inputUnit);
			result = DateUtils.millSecond2Day(result);
		} else if (inputUnit == Unit.CENTIGRADE) {
			result = inputAnswerWithoutUnit;
		} else if (inputUnit == Unit.NUM_OF_TIMES) {
			result = inputAnswerWithoutUnit;
		}
		return result;	
	}
	
	
    /** 
     * @param args 
     */  
    public static Object execFormula(String formula) {  
        try {  
            Object result = jse.eval(formula);
            return result;
        } catch (Exception t) {
        	return null;
        }  
    } 
	
}

//import org.hl7.fhir.dstu3.model.Patient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.simon.commonsall.utils.JsonUtils;

import lombok.Getter;
import lombok.Setter;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * PatientTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2018年6月14日
 * @version 1.0 
 */
public class PatientTest {
	
	public static void main(String[] args) {
//		Patient p = new Patient();
//		JsonUtils.to(p);
		
//		Aaa a = new Aaa();
//		a.setIa("haha");
//		Bbb b = new Bbb();
//		b.setF(a);
//		a.setF(b);
//		JsonUtils.to(a);
		
	}
	
	@Getter
	@Setter
	public static class Aaa{
		
		String ia;
		
		Bbb f;
		
	}
	
	@Getter
	@Setter
	public static class Bbb{
		
		Aaa f;

		
	}
	
}

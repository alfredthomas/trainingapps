package com.example.sschuuac1;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TestData extends ArrayList<TestDataItem> {
	
	public TestData() {
	
	
		double curr = 10.0;
		double curr2 = 20.0;
	
		for (int i = 0; i < 31; i++) {
			curr += Math.random() * 4.0 - 2.0;
			curr2 += Math.random() * 4.0 - 2.0;
		
			TestDataItem newItem = new TestDataItem();
			newItem.setLabel(((Integer)(i + 1)).toString());
			newItem.setValue(Math.min(curr, curr2));
			add(newItem);
		}
   }
	
}

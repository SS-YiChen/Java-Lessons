package com.skillstorm.beans;

import org.springframework.stereotype.Component;

@Component
public class Interviewiee3 {

	//the idea behind AOP is this class gets to carry on as usual and the aspects
	//do whatever they have to do without this class being aware of them
	public boolean interview() {
		System.out.println("Interviewee 3: At the interview");
		
		return true;
	}
}

package com.pcwk.ehr.local;

public class LocalClass {

	public void doSomething() {
		//로컬 클래스
		
		class LocalInerClass{
			void printMessage() {
				System.out.println("Hello local class!");
			}
			
		}
		
		LocalInerClass localInstance=new LocalInerClass();
		
		localInstance.printMessage();
	}
	
	
	public static void main(String[] args) {
		LocalClass main=new LocalClass();
		main.doSomething();

	}

}

package com.pcwk.ehr.local;

public class AnonymouseInnerClass {

	public interface Greeting{
		void greet();
	}
	
	public void sayHello() {
		// Greeting인터페이스의 인스턴스 생성
		
		Greeting greeting=new Greeting() {

			@Override
			public void greet() {
				System.out.println("익명 inner class");
				
			}
			
		};
		
		//익명 inner class호출
		greeting.greet();
		
	}
	
	public static void main(String[] args) {
		AnonymouseInnerClass  main=new AnonymouseInnerClass();
		main.sayHello();

	}

}

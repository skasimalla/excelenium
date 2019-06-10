package com.as.selenium.engine;

public class Main {

	public static void main(String[] args) throws Exception {
		TestEngine engine = new TestEngine();
		engine.runTestSuite(args[0]);
	}

}

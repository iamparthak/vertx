package com.glarimy.nashorn;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class NashornTest {

	@Test
	public void test() {
		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("library.js").getFile());
			engine.eval(new FileReader(file));
			Invocable invocable = (Invocable) engine;

			Book book = (Book) invocable.invokeFunction("prepare");
			assertTrue(book.getIsbn() == 1234);
			assertTrue(book.getTitle().contains("Nashorn"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed");
		}
	}
}
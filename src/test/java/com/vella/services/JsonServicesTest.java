package com.vella.services;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Initially written to use mockito runner but subsequent impl had no services
 * to mock out.
 * 
 * @author avella
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonServicesTest {

	@InjectMocks
	JsonServices jsonServices;

	/**
	 * There should be 26 nodes of type 'subview' with a class name of 'Input'
	 */
	@Test
	public void testInputCount() throws IOException {
		assertEquals(26, jsonServices.queryJson("Input").size());
	}

	@Test
	public void testContainerCount() throws IOException {
		assertEquals(6, jsonServices.queryJson(".container").size());
		assertEquals(1, jsonServices.queryJson(".columns").size());
	}

	@Test
	public void testIdentfierCount() throws IOException {
		assertEquals(1, jsonServices.queryJson("#videoMode").size());
	}

	@Test
	public void testPrettyPrint() {
		String uglyJson = "{\"test1\":1,\"test2\":2}";
		String prettyJson = "{\n  \"test1\": 1,\n  \"test2\": 2\n}";

		assertEquals(prettyJson, jsonServices.prettyPrintJson(uglyJson));

	}
}

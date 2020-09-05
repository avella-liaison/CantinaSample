package com.vella.services;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class JsonServices {

	DocumentContext docContext;

	/**
	 * Basic ctor to setup the json Ideally, this would be created and persisted
	 * via a singleton pattern, but present scenario doesn't justify the extra
	 * code
	 * 
	 * Process/pattern would be different if we needed to pull dynamic from
	 * online source
	 */
	public JsonServices() {
		docContext = JsonPath.parse(this.getClass().getResourceAsStream("/view.json"));
	}

	/**
	 * 
	 * @param searchValue
	 *            - String selector value to query json for
	 * @return JSON array of results
	 * @throws IOException
	 */
	public JSONArray queryJson(String searchValue) throws IOException {

		JSONArray retObj = null;

		// if patterns starts with a dot, search for ClassName
		if (searchValue.startsWith(".")) {
			retObj = findByClassName(searchValue.substring(1));

			// if pattern starts with hash, search for View
		} else if (searchValue.startsWith("#")) {

			retObj = findByIdentifier(searchValue.substring(1));

			// if no delimiter found, search for class
		} else {

			retObj = findByClassType(searchValue);
		}

		return retObj;
	}

	/**
	 * Helper method to format json
	 * 
	 * @param json
	 *            Json in string format
	 * @return json in pretty formatting
	 */
	public String prettyPrintJson(String json) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(json);
		return gson.toJson(je);

	}

	private JSONArray findByClassType(String clazz) {
		return baseJsonQuery("class", clazz);

	}

	private JSONArray findByIdentifier(String identifier) {
		return baseJsonQuery("identifier", identifier);

	}

	/**
	 * JSONpath query for array containing a value
	 */
	private JSONArray findByClassName(String className) {
		JSONArray retObj = docContext.read("$.subviews..[?(@.classNames contains '" + className + "')]");
		return retObj;

	}

	/**
	 * Private method for common attribute query Leverage JSON library to query
	 * json via JsonPath
	 * 
	 * @param searchType
	 *            Attribute to search for
	 * @param searchValue
	 *            value of attribute to match
	 * @return
	 */
	private JSONArray baseJsonQuery(String searchType, String searchValue) {
		JSONArray retObj = docContext.read("$.subviews..[?(@['" + searchType + "'] == '" + searchValue + "')]");
		return retObj;

	}
}

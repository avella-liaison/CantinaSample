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

	public JsonServices() {
		docContext = JsonPath.parse(this.getClass().getResourceAsStream("/view.json"));
	}

	public JSONArray queryJson(String searchValue) throws IOException {

		JSONArray retObj = null;

		if (searchValue.startsWith(".")) {

			retObj = findByClassName(searchValue.substring(1));
		} else if (searchValue.startsWith("#")) {

			retObj = findByIdentifier(searchValue.substring(1));
		} else {

			retObj = findByClassType(searchValue);
		}

		return retObj;
	}

	public String prettyPrintJson(String json) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(json);
		return gson.toJson(je);

	}

	private JSONArray findByClassType(String clazz) {
		return baseJsonQuery("class", clazz);

	}

	private JSONArray findByClassName(String className) {
		JSONArray retObj = docContext.read("$.subviews..[?(@.classNames contains '" + className + "')]");
		return retObj;

	}

	private JSONArray findByIdentifier(String identifier) {
		return baseJsonQuery("identifier", identifier);

	}

	private JSONArray baseJsonQuery(String searchType, String searchValue) {
		JSONArray retObj = docContext.read("$.subviews..[?(@['" + searchType + "'] == '" + searchValue + "')]");
		return retObj;

	}
}

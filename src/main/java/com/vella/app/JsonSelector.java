package com.vella.app;

import java.io.IOException;
import java.util.Scanner;

import com.vella.services.JsonServices;

public class JsonSelector {

	public static void main(String[] args) {

		JsonServices jsonServices = new JsonServices();

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		while (true) {


			System.out.print("Enter your selector query.\nPrefix with a dot (.) for class names or hash (#) for identifiers.\nAll other entries will be of type class:\n");

			String query = scanner.next("[a-zA-Z0-9#.]*");

			try {


				System.out.print(jsonServices.prettyPrintJson(jsonServices.queryJson(query).toJSONString()));
			} catch (IOException e) {
				System.out.print("Your entry was invalid, please try again");
			}

		}
	}
}

package website;

import java.io.Serializable;

import api.EmploiQuebecAPI;
import io.javalin.Javalin;

public class Request {

	private static EmploiQuebecAPI emploiQuebecAPI = new EmploiQuebecAPI();

	public static void main(String[] args) {

		Javalin app = Javalin.create();

		app.enableCorsForOrigin("127.0.0.1");
		app.enableCorsForOrigin("localhost");
		app.enableCorsForOrigin("http://localhost:4200/");

		app.get("/", ctx -> ctx.result("Hello World"));

		app.before("/some-path/*", ctx -> {
			System.out.println("Hello");
		});

		app.get("/regions", ctx -> ctx.json(emploiQuebecAPI.getRegions()));

		app.start();
	}

}

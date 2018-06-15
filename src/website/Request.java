package website;

import java.io.Serializable;

import api.EmploiQuebecAPI;
import io.javalin.Javalin;

public class Request {

	EmploiQuebecAPI emploiQuebecAPI = new EmploiQuebecAPI();

	public static void main(String[] args) {
		Javalin app = Javalin.start(7000);
		app.get("/", ctx -> ctx.result("Hello World"));

		app.before("/some-path/*", ctx -> {
			System.out.println("Hello");
		});

		app.get("/valdor", ctx -> ctx.json(Valdor.getValdorJobs()));
	}

}

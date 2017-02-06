package com.glarimy.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class LibraryVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> future) throws Exception {
		System.out.println("starting...");
		Router router = Router.router(vertx);
		vertx.deployVerticle("com.glarimy.vertx.DatabaseVerticle", new DeploymentOptions().setWorker(true));
		router.route("/about").handler(rctx -> {
			HttpServerResponse response = rctx.response();
			response.putHeader("content-type", "text/html")
					.end("<h1>Hello from my first Vert.x 3 application via routers</h1>");
		});
		router.route("/static/*").handler(StaticHandler.create("web"));
		router.get("/api/book/:isbn").handler(rctx -> {
			int isbn = Integer.parseInt(rctx.request().getParam("isbn"));
			final Book book = new Book(isbn, "Mongo on Vertx");
			rctx.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(book));
		});
		router.route("/api/book").handler(BodyHandler.create());
		router.post("/api/book").handler(rctx -> {
			vertx.eventBus().send("com.glarimy.vertx.library.book.save", rctx.getBodyAsJson(), r -> {
				rctx.response().setStatusCode(200).end();
			});
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080),
				result -> {
					if (result.succeeded()) {
						future.complete();
					} else {
						future.fail(result.cause());
					}
				});
	}

	@Override
	public void stop() throws Exception {
		System.out.println("stopping...");
		super.stop();
	}
}

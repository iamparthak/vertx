package com.glarimy.vertx;

import io.vertx.core.Vertx;

public class EmbeddedHelloWorld {
	public static void main(String[] args) {
		Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Welcome to Embedded Vertx"))
				.listen(8080);
	}
}
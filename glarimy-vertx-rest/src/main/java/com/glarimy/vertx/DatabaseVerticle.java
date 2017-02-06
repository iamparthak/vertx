package com.glarimy.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;

public class DatabaseVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("com.glarimy.vertx.library.book.save", message -> {
			Book book = Json.decodeValue(message.body().toString(), Book.class);
			System.out.println(book);
			message.reply(true);
		});
	}
}

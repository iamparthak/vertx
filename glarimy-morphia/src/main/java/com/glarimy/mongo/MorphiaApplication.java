package com.glarimy.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

public class MorphiaApplication {
	public static void main(String[] args) throws Exception {
		MongoClient mongo = new MongoClient("localhost", 27017);
		Morphia morphia = new Morphia();
		Datastore store = morphia.createDatastore(mongo, "glarimy-morphia");

		BasicDAO<Book, Integer> dao = new BasicDAO<>(Book.class, store);

		dao.save(new Book(1234, "Mongo with Morphia"));

		Book book = dao.createQuery().field("name").equal("1234").get();
		System.out.println(book);
	}
}
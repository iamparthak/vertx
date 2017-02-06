package com.glarimy.mongo;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoApplication {
	public static void main(String[] args) throws Exception {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("glarimy-mongo");
		MongoCollection<Document> collection = db.getCollection("library");
		Document document = new Document();
		document.put("isbn", "1234");
		document.put("title", "Mongo and Java");
		collection.insertOne(document);
		collection.find(eq("isbn", "1234")).forEach((Document d) -> System.out.println(d.toJson()));
		mongo.close();
	}
}

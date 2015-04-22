package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PageEntity {
	private String name;
	private String category;
	private String type;
	private int likes;
	private String owner;
	
	
	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getType() {
		return type;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public String getOwner() {
		return owner;
	}
	
	
	public PageEntity(String string, String string2, String string3,
			int string4, String string5) {
		name = string;
		category = string2;
		type = string3;
		likes = string4;
		owner = string5;
		// TODO Auto-generated constructor stub
	}

	public static PageEntity getPageSearch(String myemail, String name){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		System.out.println("d5l search");
		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println("tmam");
			if (entity.getProperty("name").toString().equals(name)) {
				System.out.println("l2ah fel DB");
				PageEntity returnedpage = new PageEntity(entity.getProperty(
						"name").toString(), entity.getProperty("category")
						.toString(), entity.getProperty("type").toString(),
						Integer.parseInt((entity.getProperty("likes").toString())), entity.getProperty("ownerEmail").toString());
				return returnedpage;
			}
		}	
		System.out.println("ml2ash 7aga fel pages");
		return null;
	}
	
	public static PageEntity likePage(String myemail, String name){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		System.out.println("d5l search");
		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println("tmam");
			if (entity.getProperty("name").toString().equals(name)) {
				System.out.println("l2ah fel DB");
				int likes2 = Integer.parseInt((entity.getProperty("likes").toString()));
				likes2 += 1;
				entity.setProperty("likes", likes2);
				datastore.put(entity);
				PageEntity returnedpage = new PageEntity(entity.getProperty(
						"name").toString(), entity.getProperty("category")
						.toString(), entity.getProperty("type").toString(),
						Integer.parseInt((entity.getProperty("likes").toString())), entity.getProperty("ownerEmail").toString());
				
				
				DatastoreService datastore2 = DatastoreServiceFactory
						.getDatastoreService();
				Query gaeQuery2 = new Query("mypages");
				PreparedQuery pq2 = datastore.prepare(gaeQuery2);
				List<Entity> list = pq2.asList(FetchOptions.Builder.withDefaults());
				Entity mypage = new Entity("mypages", list.size() + 1);
				mypage.setProperty("email", myemail);
				mypage.setProperty("pagename", name);
				datastore2.put(mypage);
				return returnedpage;
			}
		}	
		System.out.println("ml2ash 7aga fel pages");
		return null;
	}
	
}

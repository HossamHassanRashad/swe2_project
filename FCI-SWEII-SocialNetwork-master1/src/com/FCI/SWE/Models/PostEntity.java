package com.FCI.SWE.Models;

import java.util.List;
import java.util.Vector;

import com.FCI.SWE.UserModel.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PostEntity {
	public String content;
	public String owner;
	public String to;
	public int likes;
	public String feel;
	public int postID;
	public String privacy;
	
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	public String getPrivacy() {
		return privacy;
	}
	
	public void setPostID(int postID) {
		this.postID = postID;
	}
	
	public int getPostID() {
		return postID;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getFeel() {
		return feel;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getTo() {
		return to;
	}
	
	public PostEntity(String property, String property2, String property3,
			int property4, String property5 , int property6 , String property7) {
		content = property;
		owner = property2;
		to = property3;
		likes = property4;
		feel = property5;
		postID = property6;
		privacy = property7;
	}

	public static Boolean createPost(Post postObj){//String myemail,String email,String content,String feel,String Privacy) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity post = new Entity("posts", list.size() + 2);

		post.setProperty("owner", postObj.owner);
		post.setProperty("to", postObj.to);
		post.setProperty("content", postObj.content);
		post.setProperty("likes", postObj.likes);
		post.setProperty("feel", postObj.feel);
		post.setProperty("Privacy",postObj.Privacy);	
		datastore.put(post);
		
		Query gaeQuery2 = new Query("Hashtags");
		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
		List<Entity> list2 = pq2.asList(FetchOptions.Builder.withDefaults());
		String hashtags[] = postObj.content.split("#");
		for(int i = 1 ; i < hashtags.length ; i++){
			String hashtag[] = hashtags[i].split(" ");
			PreparedQuery pqtemp = datastore.prepare(gaeQuery2);
			Query gaeQuery3 = new Query("Hashtags");
			PreparedQuery pq3 = datastore.prepare(gaeQuery3);
			List<Entity> list10 = pq3.asList(FetchOptions.Builder.withDefaults());
			System.out.println(list10.size());
			Entity newhashtag = new Entity("Hashtags", list10.size() + 1);
			newhashtag.setProperty("name", hashtag[0]);
			newhashtag.setProperty("post", postObj.content);
			newhashtag.setProperty("postowner", postObj.owner);
			datastore.put(newhashtag);
			increaseCount(newhashtag.getProperty("name").toString());
		}
		return true;
	}
	
	public static void increaseCount(String name){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery2 = new Query("HashtagCounter");
		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
			for(Entity hashtage: pq2.asIterable()){
				//System.out.println("hashtage"+hashtage.getProperty("name").toString());
				if(hashtage.getProperty("name").toString().equals(name)){
					int count=Integer.parseInt(hashtage.getProperty("count").toString());
					count++;
					hashtage.setProperty("count", count);
					datastore.put(hashtage);
					return;
				}
			}
			Query gaeQuery3 = new Query("HashtagCounter");
			PreparedQuery pq3 = datastore.prepare(gaeQuery3);
			List<Entity> list10 = pq3.asList(FetchOptions.Builder.withDefaults());
			System.out.println(list10.size());
			Entity newhashtag = new Entity("HashtagCounter", list10.size() + 1);
			newhashtag.setProperty("name", name);
			newhashtag.setProperty("count", 1);
			datastore.put(newhashtag);
			return;
	}
	
	public static String getMostTags(){
		String res = "";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashtagCounter");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> lis = pq.asList(FetchOptions.Builder.withDefaults());
		if(lis.size() < 10){
			for(Entity hashtage: pq.asIterable()){
				res += hashtage.getProperty("name").toString()+" "+hashtage.getProperty("count").toString()+"<br>";
			}
		}else{
			int i = 0;
			for(Entity hashtage: pq.asIterable()){
				i++;
				res += hashtage.getProperty("name").toString()+" "+hashtage.getProperty("count").toString()+"<br>";
				if(i == 10)
					break;
			}
		}
		return res;
		}
	public static Vector<PostEntity> viewUserTimeline(String email){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Vector<PostEntity> res = new Vector<PostEntity>();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity post: pq.asIterable()){
			if(post.getProperty("to").toString().equals(email)){
				PostEntity p = new PostEntity(post.getProperty("content").toString(),post.getProperty("owner").toString(),
						post.getProperty("to").toString(),Integer.parseInt(post.getProperty("likes").toString())
						,post.getProperty("feel").toString(),Integer.parseInt(Long.toString(post.getKey().getId())),post.getProperty("Privacy").toString());
				res.add(p);
			}
		}
		return res;
	}
	
	public static boolean likePost(String postID){
		System.out.println("post ID: "+ postID);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity post: pq.asIterable()){
			System.out.println("ID: "+ Long.toString(post.getKey().getId()));
			if(Long.toString(post.getKey().getId()).equals(postID)){
				System.out.println("found it");
				int x = Integer.parseInt(post.getProperty("likes").toString());
				x++;
				post.setProperty("likes", x);
				datastore.put(post);
				return true;
			}
		}
		return false;
	}
		
	public static Vector<PostEntity> searchuserTimeline(String email){
		String array[];
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Vector<PostEntity> res = new Vector<PostEntity>();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity post: pq.asIterable()){
			System.out.println("l privacy: "+post.getProperty("Privacy").toString());
			if(!post.getProperty("Privacy").toString().equals("public")){
				System.out.println("d5l l privacy");
				array = post.getProperty("Privacy").toString().split("/");
				for(String temp: array){
					if(post.getProperty("to").toString().equals(email)&& temp.equals(User.getCurrentActiveUser().getEmail())){
						PostEntity p = new PostEntity(post.getProperty("content").toString(),post.getProperty("owner").toString(),
								post.getProperty("to").toString(),Integer.parseInt(post.getProperty("likes").toString())
								,post.getProperty("feel").toString(),Integer.parseInt(Long.toString(post.getKey().getId())),post.getProperty("Privacy").toString());
						res.add(p);
					}
				}
			}
			else if(post.getProperty("to").toString().equals(email)){
				PostEntity p = new PostEntity(post.getProperty("content").toString(),post.getProperty("owner").toString(),
						post.getProperty("to").toString(),Integer.parseInt(post.getProperty("likes").toString())
						,post.getProperty("feel").toString(),Integer.parseInt(Long.toString(post.getKey().getId())),post.getProperty("Privacy").toString());
				res.add(p);
			}
		}
		return res;
	}

}

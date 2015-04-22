package com.FCI.SWE.Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Post {
	public String content;
	public String owner;
	public String to;
	public int likes;
	public String feel;
	public int postID;
	public String Privacy;
	public Post() {
	}
	
	
	public void setPrivacy(String privacy) {
		Privacy = privacy;
	}
	
	public String getPrivacy() {
		return Privacy;
	}
	
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public int getPostID() {
		return postID;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	
	
	public void setFeel(String feel) {
		this.feel = feel;
	}
	
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getLikes() {
		return likes;
	}
	
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}

	public String getFeel() {
		return feel;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public String getTo() {
		return to;
	}


	public static Post parsePostinfo(String jj){
		JSONParser Parser = new JSONParser();
		JSONObject object = new JSONObject();
		try {
			object = (JSONObject) Parser.parse(jj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Post po = new Post();
		po.setContent(object.get("content").toString());
		po.setFeel(object.get("feel").toString());
		po.setOwner(object.get("owner").toString());
		po.setTo(object.get("to").toString());
		po.setLikes(Integer.parseInt(object.get("likes").toString()));
		po.setPostID(Integer.parseInt(object.get("postID").toString()));
		po.setPrivacy(object.get("Privacy").toString());
		return po;
	}
}

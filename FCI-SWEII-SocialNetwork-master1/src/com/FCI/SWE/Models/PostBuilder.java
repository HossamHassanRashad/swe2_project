package com.FCI.SWE.Models;

public abstract class PostBuilder {
	
	Post post = new Post();
	
	public abstract void setContent(String c);
	
	public abstract void setLikes(int l);
	
	public abstract void setOwner(String o);
	
	public abstract void setTo(String t);
	
	public abstract void setFeel(String f);
	
	public abstract void setPrivacy(String p);
	public  Post Build(){
		return post;
	}
}

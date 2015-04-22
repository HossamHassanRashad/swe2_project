package com.FCI.SWE.Models;

public class UserPost extends PostBuilder{

	@Override
	public void setContent(String c) {
		post.setContent(c);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLikes(int l) {
		post.setLikes(l);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOwner(String o) {
		post.setOwner(o);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeel(String f) {
		post.setFeel(f);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTo(String t) {
		// TODO Auto-generated method stub
		post.setTo(t);
	}

	@Override
	public void setPrivacy(String p) {
		// TODO Auto-generated method stub
		post.setPrivacy(p);
	}
	
}

package com.rnb2.gwt1.data.pm;

public class ProfileUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient private Profile profile;
	
	public ProfileUser(User user, Profile profile) {
		super();
		super.setId(user.getId());
		super.setLoginName(user.getLoginName());
		super.setFullName(user.getFullName());
		super.setWorkPhone(user.getWorkPhone());
		setProfile(profile);
		// TODO Auto-generated constructor stub
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}

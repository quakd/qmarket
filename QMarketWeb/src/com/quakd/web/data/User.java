package com.quakd.web.data;

public class User {

	private Long id;

	private String username;

	private String firstName;

	private String lastName;
	
	private String password;

	private boolean subscriber;
	
	private String facebookImageUrl;
	
	private String facebookName;

	private String twitterImageUrl;	
	
	private String twitterName;
	
	private Long locId;
	
	private boolean connectedToSocial;
	
	private byte[] quakdImg;	
	
	private String quakdImgType;	
	
	private boolean splashScreen = true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isSubscriber() {
		return subscriber;
	}

	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebookImageUrl() {
		return facebookImageUrl;
	}

	public void setFacebookImageUrl(String facebookImageUrl) {
		this.facebookImageUrl = facebookImageUrl;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

	public String getTwitterImageUrl() {
		return twitterImageUrl;
	}

	public void setTwitterImageUrl(String twitterImageUrl) {
		this.twitterImageUrl = twitterImageUrl;
	}

	public String getTwitterName() {
		return twitterName;
	}

	public void setTwitterName(String twitterName) {
		this.twitterName = twitterName;
	}

	public boolean isConnectedToSocial() {
		return connectedToSocial;
	}

	public void setConnectedToSocial(boolean connectedToSocial) {
		this.connectedToSocial = connectedToSocial;
	}

	public boolean isSplashScreen() {
		return splashScreen;
	}

	public void setSplashScreen(boolean splashScreen) {
		this.splashScreen = splashScreen;
	}

	public byte[] getQuakdImg() {
		return quakdImg;
	}

	public void setQuakdImg(byte[] quakdImg) {
		this.quakdImg = quakdImg;
	}

	public String getQuakdImgType() {
		return quakdImgType;
	}

	public void setQuakdImgType(String quakdImgType) {
		this.quakdImgType = quakdImgType;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}



	
}

package com.uea.kmg.rotaapp1.model;

public class FavoriteLocation {
	private long id;
	private Double latitude;
	private Double longitude;
	private String description;
	
	public FavoriteLocation(Double latitude, Double longitude, String description) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}
	
	public FavoriteLocation(long id, Double latitude, Double longitude, String description) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}

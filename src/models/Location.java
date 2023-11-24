package models;

public abstract class Location {
	private double longitude;
	private double latitude;

	public Location(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
}

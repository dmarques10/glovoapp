package com.glovoapp.backender.utils;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import com.glovoapp.backender.model.Location;

/**
 * Shamelessly copied from https://github.com/jasonwinn/haversine
 */
public class DistanceCalculator {

	private static final int EARTH_RADIUS = 6371;

	/**
	 * Returns distance between two locations in kilometers
	 */
	public static double calculateDistance(Location start, Location end) {
		double deltaLat = toRadians((end.getLat() - start.getLat()));
		double deltaLong = toRadians((end.getLon() - start.getLon()));

		double startLat = toRadians(start.getLat());
		double endLat = toRadians(end.getLat());

		double a = haversin(deltaLat) + cos(startLat) * cos(endLat) * haversin(deltaLong);
		double c = 2 * atan2(sqrt(a), sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

	private static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}
}

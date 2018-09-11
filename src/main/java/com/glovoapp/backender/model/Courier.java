package com.glovoapp.backender.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Courier {
	private String id;
	private String name;
	private Boolean box;
	private Vehicle vehicle;
	private Location location;

	@Override
	public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
		Courier courier = (Courier) o;
		return Objects.equals(id, courier.id) &&
			Objects.equals(name, courier.name) &&
			Objects.equals(box, courier.box) &&
			vehicle == courier.vehicle &&
			Objects.equals(location, courier.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, box, vehicle, location);
	}

	@Override
	public String toString() {
		return "Courier{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			", box=" + box +
			", vehicle=" + vehicle +
			", location=" + location +
			'}';
	}
}

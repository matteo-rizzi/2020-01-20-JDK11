package it.polito.tdp.artsmia.model;

import java.util.List;

public class Artist {

	private int id;
	private String name;
	private List<ArtObject> objects;

	public Artist(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ArtObject> getObjects() {
		return objects;
	}

	public void setObjects(List<ArtObject> objects) {
		this.objects = objects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name + "(" + this.id+")";
	}

}

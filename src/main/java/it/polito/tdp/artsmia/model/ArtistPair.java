package it.polito.tdp.artsmia.model;

public class ArtistPair implements Comparable<ArtistPair>{

	private Artist first;
	private Artist second;
	private int peso;

	public ArtistPair(Artist first, Artist second, int peso) {
		super();
		this.first = first;
		this.second = second;
		this.peso = peso;
	}

	public Artist getFirst() {
		return first;
	}

	public void setFirst(Artist first) {
		this.first = first;
	}

	public Artist getSecond() {
		return second;
	}

	public void setSecond(Artist second) {
		this.second = second;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(ArtistPair other) {
		return other.peso - this.peso;
	}

}

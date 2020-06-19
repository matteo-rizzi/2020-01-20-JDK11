package it.polito.tdp.artsmia.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo("Manufacturer");
		
		List<Artist> best = model.ricorsione(3662);
		for(Artist a : best) {
			System.out.println(a);
		}
		
		System.out.println(model.getNumeroEsposizioni());

	}

}

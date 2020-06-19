package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private SimpleWeightedGraph<Artist, DefaultWeightedEdge> grafo;
	private Map<Integer, Artist> idMap;
	private List<ArtistPair> connessi;
	private List<Artist> best;
	private Integer numeroEsposizioni;
	private double peso;

	public Model() {
		this.dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, Artist>();
		this.dao.getArtists(idMap);
	}

	public List<String> getRoles() {
		List<String> ruoliOrdinati = new ArrayList<>(this.dao.getRoles());
		Collections.sort(ruoliOrdinati);
		return ruoliOrdinati;
	}

	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<Artist, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		// aggiungiamo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getArtistsbyRole(ruolo, idMap));

		// aggiungiamo gli archi
		this.connessi = this.dao.getArtistPairs(ruolo, idMap);
		for (ArtistPair ap : this.connessi) {
			Graphs.addEdge(this.grafo, ap.getFirst(), ap.getSecond(), ap.getPeso());
		}
	}

	public String getArtistConnessi() {
		Collections.sort(connessi);
		String risultato = "";
		if (connessi == null)
			return null;
		for (ArtistPair ap : connessi) {
			risultato += "PRIMO: " + ap.getFirst().getName() + "(" + ap.getFirst().getId() + ")" + ";\tSECONDO: "
					+ ap.getSecond().getName() + "(" + ap.getSecond().getId() + ")" + ";\tESPOSIZIONI COMUNI: "
					+ ap.getPeso() + "\n";
		}
		return risultato;
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public List<Artist> ricorsione(int idArtista) {
		numeroEsposizioni = 0;
		this.best = new ArrayList<>();
		List<Artist> parziale = new ArrayList<>();
		parziale.add(idMap.get(idArtista));
		
		this.cerca(parziale, idArtista, -1);
		
		this.numeroEsposizioni = (int) peso * this.best.size();

		return best;
	}

	private void cerca(List<Artist> parziale, int idArtista, double peso) {
		// caso terminale
		if (parziale.size() > best.size()) {
			best = new ArrayList<>(parziale);
			this.peso = peso;
		}

		// caso intermedio
		List<Artist> vicini = Graphs.neighborListOf(this.grafo, idMap.get(idArtista));
		for (Artist a : vicini) {
			if (parziale.size() == 1 && !parziale.contains(a)) {
				parziale.add(a);
				peso = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(0), a));
				this.cerca(parziale, a.getId(), peso);
				parziale.remove(a);
			} else {
				if (this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(parziale.size() - 1), a)) == peso
						&& !parziale.contains(a)) {
					parziale.add(a);
					this.cerca(parziale, a.getId(), peso);
					parziale.remove(a);
				}
			}
		}
	}

	public Integer getNumeroEsposizioni() {
		return numeroEsposizioni;
	}
	
	public boolean contieneArtista(int id) {
		boolean trovato = false;
		if(this.grafo.vertexSet().contains(idMap.get(id)))
			trovato = true;
		return trovato;
	}

}

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	if(this.model.getArtistConnessi() == null) {
    		txtResult.appendText("Devi prima creare il grafo!\n");
    		return;
    	}
    	txtResult.appendText(this.model.getArtistConnessi());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	try {
    		int id = Integer.parseInt(this.txtArtista.getText());
    		if(!model.contieneArtista(id)) {
    			this.txtResult.appendText("Errore! L'id selezionato non è associato ad alcuna artista!");
    			return;
    		}
    		List<Artist> best = this.model.ricorsione(id);
    		this.txtResult.appendText("Il cammino più lungo è composto dai seguenti artisti:\n");
    		for(int i = 1; i <= best.size(); i++) {
    			this.txtResult.appendText("Artista " + i + ": " + best.get(i - 1) + "\n");
    		}
    		this.txtResult.appendText("Il numero di esposizioni è : " + model.getNumeroEsposizioni());
    		
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("Errore! Devi inserire un valore numerico che rappresenti l'id dell'artista!\n");
    		return;
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo == null) {
    		txtResult.appendText("Errore! Non hai selezionato alcun ruolo!\n");
    		return;
    	}
    	this.model.creaGrafo(ruolo);
    	this.txtResult.appendText(String.format("Grafo creato! Sono presenti %d VERTICI e %d ARCHI", this.model.nVertici(), this.model.nArchi()));
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.getRoles());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
    
}

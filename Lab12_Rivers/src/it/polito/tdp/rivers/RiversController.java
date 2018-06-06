package it.polito.tdp.rivers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doRiver(ActionEvent event) {
    	
    	this.txtStartDate.clear();
    	this.txtEndDate.clear();
    	this.txtFMed.clear();
    	this.txtNumMeasurements.clear();
    	this.txtK.clear();
    	this.txtResult.clear();
    	
    	River river = this.boxRiver.getValue();
    	
    	if (river == null) {
    		this.txtResult.appendText("Selezionare un fiume!\n");
    		return;
    	}
    	
    	model.setAllFlowsFromRiver(river);
    	
    	List <Flow> flows = river.getFlows();
    	this.txtStartDate.appendText(flows.get(0).getDay().toString());
    	this.txtEndDate.appendText(flows.get(flows.size() - 1).getDay().toString());
    	this.txtNumMeasurements.appendText(Integer.toString(flows.size()));
    	this.txtFMed.appendText(String.format("%.3f", river.getFlowAvg()));
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
    		
    		River river = this.boxRiver.getValue();
        	
        	if (river == null) {
        		this.txtResult.appendText("Selezionare un fiume!\n");
        		return;
        	}
    		double k = Double.parseDouble(this.txtK.getText());

    		if (k > 0) {
    			
    			Simulatore sim = new Simulatore();
    			sim.init(k, river);
    			sim.run();
    			this.txtResult.appendText(String.format("Numero di giorni in cui non si è potuta garantire l'erogazione minima: %d\n", sim.getN_giorni()));
    			this.txtResult.appendText(String.format("Occupazione media del bacino: %.3f", sim.getC_med()));
    		
    		}
    		
    	} catch (NumberFormatException e){
    		this.txtResult.appendText("Inserire un valore valido di K!\n");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxRiver.getItems().addAll(model.getAllRivers());
	}
}

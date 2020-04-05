package application;

import javafx.scene.control.Button;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SampleController implements Initializable{
	
	

	
	ObservableList<String> chooseCurrencyList = FXCollections.observableArrayList("USD","USD", "JPY");
	String currency = "";
	String startDates = "";
	String endDates = "";
	String dividend = "";
	String transactionCost = "";
	String taxRate = "";
	
	
	
	
	@FXML private LineChart<String, Integer>historicViewChart; // need to define X,Y
	XYChart.Series<String, Integer> series = null; //need to generate line
	@FXML private ChoiceBox currencyBox;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TextField dividendInput;
	@FXML private TextField transactionCostInput;
	@FXML private TextField taxRateInput;
	@FXML private Button runButton;
	
	
	//For the future
	/*
	ArrayList<String> currencies = Calculation.getCurrencies();
	ObservableList<String> list = FXCollections.observableArrayList(currencies);
	currencyBox.setItems(list);
	*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML Load Complete");
		//drawChart();
		
		//set the initial values of currencyBox. 
		currencyBox.setValue("USD");
		currencyBox.setItems(chooseCurrencyList);
		
		//set the initial values of the datepicker.
		startDate.setValue(LocalDate.of(2018, 1, 19));
		endDate.setValue(LocalDate.now());
		
		
		//define the values of the input on the text field.
		inputNumericOnly(dividendInput);
		inputNumericOnly(transactionCostInput);
		inputNumericOnly(taxRateInput);
		
		
		
		
		
	}
	

	
	/**
	 * This method forces the text field value to be numerics only. 
	 * @param tf
	 */
	public void inputNumericOnly(TextField tf) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
			
		});
	}
	
	/***
	 * This method is called when the user clicked the run button. 
	 * the method saves all the values into String/Number formats and draw charts. 
	 */
	public void clickedRunButton() {
		startDates = startDate.getValue().toString();
		endDates = endDate.getValue().toString();
		currency = currencyBox.getSelectionModel().getSelectedItem().toString();
		dividend = dividendInput.getText();
		transactionCost = transactionCostInput.getText();
		taxRate = taxRateInput.getText();
		System.out.println(currency + dividend + transactionCost + taxRate + startDates);
		
		drawHistoricalViewChart();
		
		
		//System.out.println(startDates + ", " + endDates);
		
		
	}
	
	/**
	 * Mthod to draw chart
	 */
	public void drawHistoricalViewChart() {
		series = new XYChart.Series<String, Integer> ();
		
		series.getData().add(new XYChart.Data<String, Integer>("1",2));
		series.getData().add(new XYChart.Data<String, Integer>("2",1));
		series.getData().add(new XYChart.Data<String, Integer>("3",5));
		series.getData().add(new XYChart.Data<String, Integer>("4",2));
		
		series.setName("Original Return");
		
		//to add the series into the line chart
		historicViewChart.getData().add(series);
	}
	
	public void multiChart() {
		/*lineChart.getData().clear(); //to delete the currnet line.*/
		
		series = new XYChart.Series<String, Integer>();
		series.getData().add(new XYChart.Data<String, Integer>("1",3));
		series.getData().add(new XYChart.Data<String, Integer>("2",3));
		series.getData().add(new XYChart.Data<String, Integer>("3",3));
		series.getData().add(new XYChart.Data<String, Integer>("4",3));
		series.setName("DollarCostAverage Return");
		
		historicViewChart.getData().add(series);
		
		System.out.println("this it to generate another line. ");
	}
	
	public void selectCurrency() {
		currencyBox.setValue("USD");
		currencyBox.setItems(chooseCurrencyList);
		System.out.println("This is to generate currency");
		
	}
}

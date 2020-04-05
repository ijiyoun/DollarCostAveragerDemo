package application;

import javafx.scene.control.Button;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/***
 * Class to save user inputs and communicate with back end methods and show the graphs. 
 * @author jiyoungim
 *
 */
public class Controller implements Initializable{
	
	
	
	ObservableList<String> chooseCurrencyList = FXCollections.observableArrayList("USD", "JPY");
	String ticker = "";
	String investmentCurrency = "";
	String transactionCurrency = "";
	String startDates = "";
	String endDates = "";
	Double investment = 0.0;
	Double transactionCost = 0.0;
	Double taxRate = 0.0;
	HashMap<Date, Double>calResult = new HashMap<Date,Double>();
	
	
	
	
	@FXML private TextField tickerInput;
	@FXML private LineChart<String, Integer>historicViewChart; // need to define X,Y
	XYChart.Series<String, Integer> series = null; //need to generate line
	@FXML private LineChart<String, Double>DCAViewChart;
	Series<String, Double> DCAseries = null;
	
	@FXML private ChoiceBox investmentCurrencyBox;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TextField investmentInput;
	@FXML private TextField transactionCostInput;
	@FXML private ChoiceBox transactionCurrencyBox;
	@FXML private TextField taxRateInput;
	@FXML private Button runButton;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML Load Complete");
		drawHistoricalViewChart();
		//disable the button if not all the inputs are made. 
		
		
		//set the initial values of currencyBox. 
//		investmentCurrencyBox.setValue("USD");
//		investmentCurrencyBox.setItems(chooseCurrencyList);
		transactionCurrencyBox.setItems(chooseCurrencyList);
		investmentCurrencyBox.setItems(chooseCurrencyList);
		
		//set the initial values of the datepicker.
		startDate.setValue(LocalDate.of(2010, 1, 1));
		endDate.setValue(LocalDate.now());
		
		
		//define the values of the input on the text field.
		inputNumericOnly(investmentInput);
		inputNumericOnly(transactionCostInput);

		
		
		//initially text box empty so disable. 
		runButton.disableProperty().bind(
			    Bindings.isEmpty(tickerInput.textProperty())
			    .or(Bindings.isEmpty(investmentInput.textProperty()))
			    .or(Bindings.isEmpty(transactionCostInput.textProperty()))
			    .or(Bindings.isEmpty(taxRateInput.textProperty()))
			);
		
		/*
		if (transactionCurrencyBox.getSelectionModel().isEmpty() || investmentCurrencyBox.getSelectionModel().isEmpty()) {
			runButton.setDisable(true);
		}
		*/
		
		

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
		
		//Store the data
		startDates = startDate.getValue().toString();
		endDates = endDate.getValue().toString();
		investmentCurrency = investmentCurrencyBox.getSelectionModel().getSelectedItem().toString();
		investment = Double.parseDouble(investmentInput.getText());
		transactionCost = Double.parseDouble(transactionCostInput.getText());
		taxRate = Double.parseDouble(taxRateInput.getText());
		System.out.println("Succesfully stored the daata into the variables");
		
		//Do smthin with the data
		
		
		
		
		//Reflect the result in the chart. 
		drawDCAViewChart();
		
		
	}
	
	/***
	 * If the user does not input any value, then prevent the user
	 */
	
	
	/**
	 * Method to draw the default chart of the historical view. 
	 * On-going. Current state is only inputs of default values. 
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
	
	/**
	 * Method to update the DCA values from the DollarCostAveraging method. 
	 */
	public void drawDCAViewChart() {
		System.out.println("drawDCAViewChart is activated. ");
		
		DollarCostAveraging dca = new DollarCostAveraging();
		dca.run(ticker, investmentCurrency, transactionCurrency, startDates, endDates, investment, transactionCost, taxRate);
		calResult = dca.calcResult;
		
		DCAseries = new XYChart.Series<String, Double> ();
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		int i = 1;
		for (Date key : calResult.keySet()) {
			DCAseries.getData().add(new XYChart.Data<String, Double>(formatter.format(key), calResult.get(key)));
			
		}
		System.out.println("Saved the data into DCAseries.");
		
		
		DCAseries.setName("Original Return");
		
		//to add the series into the line chart
		DCAViewChart.getData().add(DCAseries);
		DCAViewChart.setCreateSymbols(false);
	}
	
	/**
	 * Method to create multichart in the historicalview. 
	 */
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
	
}

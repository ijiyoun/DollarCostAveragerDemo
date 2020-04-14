package application;

import javafx.scene.control.Button;
import java.net.URL;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
	TreeMap<Date, Double> calResultSorted = new TreeMap<Date, Double>();
	HashMap<Date, Double> historicalResult = new HashMap <Date, Double>();
	TreeMap<Date, Double> historicalResultSorted = new TreeMap<Date, Double>();
	HashMap<Date, Double> cashFlowResult = new HashMap<Date, Double>();
	TreeMap<Date, Double> cashFlowResultSorted = new TreeMap<Date, Double>();
	Boolean selectReadFromCSV;
	
	
	
	
	@FXML private ComboBox tickerInput;
	@FXML private LineChart<String, Double>historicViewChart; // need to define X,Y
	Series<String, Double> series = null; //need to generate line
	@FXML private LineChart<String, Double>DCAViewChart;
	Series<String, Double> DCAseries = null;
	Series<String, Double>cashFlowSeries = null;
	
	@FXML private ChoiceBox investmentCurrencyBox;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TextField investmentInput;
	@FXML private TextField transactionCostInput;
	@FXML private ChoiceBox transactionCurrencyBox;
	@FXML private TextField taxRateInput;
	@FXML private Button runButton;
	@FXML private Text totalCashInvested;
	@FXML private Text growthRate;
	@FXML private Text totalDividend;
	@FXML private Text dollarCostAveragerTitle;
	@FXML private Text historicalViewTitle;
	@FXML private RadioButton readFromCSV;
	@FXML private RadioButton readFromAPI;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML Load Complete");
		//read the dca data. 
		DollarCostAveraging dca = new DollarCostAveraging();
		dca.run("S&P 500", "USD", "USD", "2010-01-01", "2015-01-01", 1000.0, 100.0, 3.1);
		cashFlowResult = dca.cashFlow;
		calResult = dca.calcResult;
		historicalResult = dca.historicalData; 
		Double accumulatedDividend = dca.accumulatedDividends;
		cashFlowResultSorted.putAll(cashFlowResult);
		historicalResultSorted.putAll(historicalResult);
		calResultSorted.putAll(calResult);
		
		
		
		//get the charts
		drawDCAViewChart(calResultSorted);
		drawCashFlowChart(cashFlowResultSorted);
		drawHistoricalViewChart(historicalResultSorted);
		
		//confirm the data source input and respond with ticker choice. 
		
		readFromCSV.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
			{
				tickerInput.setEditable(false);
				tickerInput.getItems().addAll(dca.tickersList);
				selectReadFromCSV = true;
				System.out.println("Select read from csv is true");
			}
			else
			{
				tickerInput.getItems().clear();
				tickerInput.setEditable(true);
				selectReadFromCSV = false;
			}
		});
		
		readFromAPI.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
			{
				tickerInput.getItems().clear();
				tickerInput.setEditable(true);
				selectReadFromCSV = false;
			}
		});
		
		//disable user inputs for Date picker
		startDate.getEditor().setEditable(false);
		endDate.getEditor().setEditable(false);
		
		//startDate.getEditor().setDisable(true);
		//set the initial values of currencyBox. 
		transactionCurrencyBox.setItems(chooseCurrencyList);
		investmentCurrencyBox.setItems(chooseCurrencyList);
		
		//set the initial values of the Date picker.
		startDate.setValue(LocalDate.of(2010, 1, 1));
		endDate.setValue(LocalDate.now());
		
		
		//define the values of the input on the text field.
		inputNumericOnly(investmentInput);
		inputNumericOnly(transactionCostInput);

		
		
		//initially text box empty so disable. 
		runButton.disableProperty().bind(
			    tickerInput.valueProperty().isNull()
			    .or(investmentCurrencyBox.valueProperty().isNull())
			    .or(transactionCurrencyBox.valueProperty().isNull())
			    .or(Bindings.isEmpty(investmentInput.textProperty()))
			    .or(Bindings.isEmpty(transactionCostInput.textProperty()))
			    .or(Bindings.isEmpty(taxRateInput.textProperty()))
			    
			);
		
		

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
	 * This method is called when the user clicks the run button. 
	 * the method saves all the values into String/Number formats and draw charts. 
	 */
	public void clickedRunButton() {
		
		Calculation myCalc;
		//Store the input data
		ticker = tickerInput.getSelectionModel().getSelectedItem().toString();
		startDates = startDate.getValue().toString();
		endDates = endDate.getValue().toString();
		investmentCurrency = investmentCurrencyBox.getSelectionModel().getSelectedItem().toString();
		investment = Double.parseDouble(investmentInput.getText());
		transactionCost = Double.parseDouble(transactionCostInput.getText());
		taxRate = Double.parseDouble(taxRateInput.getText());
		System.out.println("Succesfully stored the daata into the variables");
		
		//Read the values from DollarCostAveraging.java.
		DollarCostAveraging dca = new DollarCostAveraging();
		dca.run(ticker, investmentCurrency, transactionCurrency, startDates, endDates, investment, transactionCost, taxRate);
		cashFlowResult = dca.cashFlow;
		calResult = dca.calcResult;
		historicalResult = dca.historicalData; 
		Double accumulatedDividend = dca.accumulatedDividends;
		cashFlowResultSorted.putAll(cashFlowResult);
		historicalResultSorted.putAll(historicalResult);
		calResultSorted.putAll(calResult);
		
		
		//To show the dates range on the UI

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		historicalViewTitle.setText(" Å@*Å@Data range is from " + formatter.format(dca.minimum) + 
				" to " + formatter.format(dca.maximum) + " by " + dca.dataLimitedBy);

		
		//dollarCostAveragerTitle.setText("<Dollar Cost Averager>");


		
		//Update the charts.
		drawDCAViewChart(calResultSorted);
		drawCashFlowChart(cashFlowResultSorted);
		drawHistoricalViewChart(historicalResultSorted);
		
		//Update the Output text values.
		totalDividend.setText("TTL Dividend : " + accumulatedDividend.toString()); //TTL Dividend
		totalCashInvested.setText("TTL Cash Invested : " + dca.totalCashInvested + "(" + investmentCurrency + ")");//Growth Rate
		growthRate.setText("Growth Rate : "); //Profit
		
		
		
		
		
	
		
		
	}
	


	
	/**
	 * Draw the Historical View Chart based on user inputs, triggered by Run event. 
	 */
	public void drawHistoricalViewChart(TreeMap<Date, Double> historicalResultSorted) {
		
		historicViewChart.getData().clear();	//initialize

		
		series = new XYChart.Series<String, Double> ();
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		int i = 1;
		for (Date key : historicalResultSorted.keySet()) {
			series.getData().add(new XYChart.Data<String, Double>(formatter.format(key), historicalResultSorted.get(key)));
		}
		System.out.println("Saved the data into series.");
		
		
		
		
		series.setName("Historic Return");
		
		//to add the series into the line chart
		historicViewChart.getData().add(series);
		historicViewChart.setCreateSymbols(false);
	}
	
	/**
	 * Method to update the DCA values from the DollarCostAveraging method. 
	 */
	public void drawDCAViewChart(TreeMap<Date, Double> calResultSorted) {
		System.out.println("drawDCAViewChart is activated. ");
		
		DCAViewChart.getData().clear();		//initialize
		/*
		DollarCostAveraging dca = new DollarCostAveraging();
		dca.run(ticker, investmentCurrency, transactionCurrency, startDates, endDates, investment, transactionCost, taxRate);
		cashFlowResult = dca.cashFlow;
		calResult = dca.calcResult;
		historicalResult = dca.historicalData; 
		Double accumulatedDividend = dca.accumulatedDividends;
		cashFlowResultSorted.putAll(cashFlowResult);
		calResultSorted.putAll(calResult);
		*/

		
		DCAseries = new XYChart.Series<String, Double> ();
		
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		
		int i = 1;
		for (Date key : calResultSorted.keySet()) {
			DCAseries.getData().add(new XYChart.Data<String, Double>(formatter.format(key), calResultSorted.get(key)));
		}

		
		
		DCAseries.setName("Dollar Cost Averager");
		
		//to add the series into the line chart
		DCAViewChart.getData().add(DCAseries);
		DCAViewChart.setCreateSymbols(false);
		System.out.println("Saved the data into DCAseries.");
		/*
		drawCashFlowChart(cashFlowResultSorted);
		drawHistoricalViewChart(historicalResult);
		*/
		
		
		
		
	}
	
	/***
	 * Add draw Cash Flow line graph into the DCA view
	 */
	public void drawCashFlowChart(TreeMap<Date, Double> cashFlowResultSorted) {
		
		cashFlowSeries = new XYChart.Series<String, Double> ();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		//Need to check whether this contains data
		
		int i = 1;
		for (Date key : cashFlowResultSorted.keySet()) {
			cashFlowSeries.getData().add(new XYChart.Data<String, Double>(formatter.format(key), cashFlowResultSorted.get(key)));
			System.out.println(key + " : " + cashFlowResultSorted.get(key));
		}
		System.out.println("Saved the data into Cash Flow series.");
		
		
		cashFlowSeries.setName("Cash Flow");
		DCAViewChart.getData().add(cashFlowSeries);

		
	}
	

	

	
}

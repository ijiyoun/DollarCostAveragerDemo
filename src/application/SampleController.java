package application;

import java.awt.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

public class SampleController implements Initializable{
	
	

	
	ObservableList<String> chooseCurrencyList = FXCollections.observableArrayList("USD", "JPY");
	
	@FXML private LineChart<String, Integer>lineChart; // need to define X,Y
	XYChart.Series<String, Integer> series = null; //need to generate line
	@FXML private ChoiceBox currencyBox;
	
	//For the future
	/*
	ArrayList<String> currencies = Calculation.getCurrencies();
	ObservableList<String> list = FXCollections.observableArrayList(currencies);
	currencyBox.setItems(list);
	*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("FXML Load Complete");
		drawChart();
		currencyBox.setValue("USD");
		currencyBox.setItems(chooseCurrencyList);
		currencyBox.getSelectionModel().selectedIndexProperty()
		.addListener((v, oldValue, newValue) -> {
			//put new method to do when user input the currency or action
			System.out.println("User input "+ newValue );
		});
	}
	
	/**
	 * Mthod to draw chart
	 */
	public void drawChart() {
		series = new XYChart.Series<String, Integer> ();
		
		series.getData().add(new XYChart.Data<String, Integer>("1",2));
		series.getData().add(new XYChart.Data<String, Integer>("2",1));
		series.getData().add(new XYChart.Data<String, Integer>("3",5));
		series.getData().add(new XYChart.Data<String, Integer>("4",2));
		
		series.setName("Original Return");
		
		//to add the series into the line chart
		lineChart.getData().add(series);
	}
	
	public void multiChart() {
		/*lineChart.getData().clear(); //to delete the currnet line.*/
		
		series = new XYChart.Series<String, Integer>();
		series.getData().add(new XYChart.Data<String, Integer>("1",3));
		series.getData().add(new XYChart.Data<String, Integer>("2",3));
		series.getData().add(new XYChart.Data<String, Integer>("3",3));
		series.getData().add(new XYChart.Data<String, Integer>("4",3));
		series.setName("DollarCostAverage Return");
		
		lineChart.getData().add(series);
		
		System.out.println("this it to generate another line. ");
	}
	
	public void selectCurrency() {
		currencyBox.setValue("USD");
		currencyBox.setItems(chooseCurrencyList);
		System.out.println("This is to generate currency");
		
	}
}

package valuteConverter;

import gen.valuteConverter.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button button1, button2, button3, button4, button5, button6, button7,
			button8, button9, button0, buttonPimp, buttonC;

	final String LOG = "LOG";
	EditText editText1, editText2;

	Spinner spinner, spinner2;

	StringBuilder str = new StringBuilder();

	List<Valute> valuteList = new ArrayList<Valute>();
	
	List<ValuteSpinnerModel> spinnerList = new ArrayList<ValuteSpinnerModel>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);

		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(this);

		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(this);

		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(this);

		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(this);

		button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(this);

		button8 = (Button) findViewById(R.id.button8);
		button8.setOnClickListener(this);

		button9 = (Button) findViewById(R.id.button9);
		button9.setOnClickListener(this);

		button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(this);

		buttonPimp = (Button) findViewById(R.id.buttonPimp);
		buttonPimp.setOnClickListener(this);

		buttonC = (Button) findViewById(R.id.buttonC);
		buttonC.setOnClickListener(this);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.setOnClickListener(this);

		editText2 = (EditText) findViewById(R.id.editText2);
		editText2.setOnClickListener(this);

		new PostAsync().execute();

		spinner = (Spinner) findViewById(R.id.spinner1);

		spinner2 = (Spinner) findViewById(R.id.spinner2);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {	
				
				if (!(editText1.getText().toString().length() == 0)) {
					Convert();
				} else {
					editText2.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {		
				
				if (!(editText1.getText().toString().length() == 0)) {
					Convert();
				} else {
					editText2.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	private void Convert() {
		
		String firstSpinnerValuteRate = null;
		String secondSpinnerValuteRate = null;
		String mainSpinnerValue = null;
		
		for (int i = 0; i < valuteList.size(); i++) {
			
			if (valuteList.get(i).getValuteName()
					.equals(spinner.getSelectedItem().toString())) {
				firstSpinnerValuteRate = valuteList.get(i).getValueRate();
				mainSpinnerValue = editText1.getText().toString();
			}

			if (valuteList.get(i).getValuteName()
					.equals(spinner2.getSelectedItem().toString())) {
				secondSpinnerValuteRate = valuteList.get(i).getValueRate();
			}
		}
		

		editText2.setText(String.valueOf((Float.parseFloat(firstSpinnerValuteRate) / Float.parseFloat(secondSpinnerValuteRate) * Float.parseFloat(mainSpinnerValue))));		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.button1: insert("1");
			break;

		case R.id.button2: insert("2");
			break;

		case R.id.button3: insert("3");
			break;

		case R.id.button4: insert("4");
			break;

		case R.id.button5: insert("5");
			break;

		case R.id.button6: insert("6");
			break;

		case R.id.button7: insert("7");
			break;

		case R.id.button8: insert("8");
			break;

		case R.id.button9: insert("9");
			break;

		case R.id.button0: insert("0");
			break;

		case R.id.buttonC: clear();
			break;

		case R.id.buttonPimp: insertPimp();
			break;

		default:
			break;
		}

	}

	private void insert(String s) {
		str.append(s);
		editText1.setText(String.valueOf(str));
		Convert();
	}

	private void insertPimp() {
		if (!(editText1.getText().toString().contains("."))) {
			str.append(".");
			editText1.setText(str);
		}

		if (str.substring(0, 1).equals(".")) { 
			str.insert(0, "0");
			editText1.setText(str);
		}
	}

	private void clear() {
		if (str.length() > 1) {
			str.delete((str.length() - 1), str.length());
			editText1.setText(str);
		} else {
			str.delete(0, str.length());
			editText1.setText("0");
		}
		Convert();

	}


	class PostAsync extends AsyncTask<Void, Void, Void> {
		ProgressDialog pd;
		
		MySpinner mySpinner = new MySpinner(MainActivity.this, R.layout.spinner_row, getLayoutInflater(), spinnerList);
		
		@Override
		protected void onPreExecute() {
			
			pd = ProgressDialog.show(MainActivity.this, "Отримання курсу валют",
					"Зачекайте ...", true, false);
			
			Valute grn = new Valute();
			grn.setValuteCode("GRN");
			grn.setValueRate("1");
			grn.setValuteName("гривня");
			
			valuteList.add(grn);

			spinnerList.add(new ValuteSpinnerModel(grn.getValuteName(), grn.getValueRate(), R.drawable.ukraine));
		
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {

				URL url = new URL("http://bank-ua.com/export/currrate.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("item");

				for (int i = 0; i < nodeList.getLength(); i++) {

					Node node = nodeList.item(i);

					Element fstElmnt = (Element) node;

					NodeList nameList = fstElmnt.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();

					NodeList rateList = fstElmnt.getElementsByTagName("rate");
					Element rateElement = (Element) rateList.item(0);
					rateList = rateElement.getChildNodes();

					NodeList charList = fstElmnt.getElementsByTagName("char3");
					Element charElement = (Element) charList.item(0);
					charList = charElement.getChildNodes();

					NodeList sizeList = fstElmnt.getElementsByTagName("size");
					Element sizeElement = (Element) sizeList.item(0);
					sizeList = sizeElement.getChildNodes();

					String o = charList.item(0).getNodeValue();


					if (o.equals("USD")) {
						//addValuteObjectToArray(o, sizeList, rateList, nameList,
						//		1);
						
						Test(o, sizeList, rateList, nameList, R.drawable.united);
						
						spinnerList.add(new ValuteSpinnerModel(nameList.item(0).getNodeValue(), String.valueOf(Float.parseFloat(rateList.item(0)
								.getNodeValue())
								/ Float.parseFloat(sizeList.item(0).getNodeValue())), R.drawable.united));

					}

					if (o.equals("EUR")) {	
						//addValuteObjectToArray(o, sizeList, rateList, nameList,
						//		2);
						
						Test(o, sizeList, rateList, nameList, R.drawable.euro);
						
						/*spinnerList.add(new ValuteSpinnerModel(nameList.item(0).getNodeValue(), String.valueOf(Float.parseFloat(rateList.item(0)
								.getNodeValue())
								/ Float.parseFloat(sizeList.item(0).getNodeValue())), R.drawable.euro));*/
					}

					if (o.equals("BYR")) {
						//addValuteObjectToArray(o, sizeList, rateList, nameList,
						//		3);
						
						//Test(o, sizeList, rateList, nameList, R.drawable.belarus);
					}

					/*if (o.equals("RUB")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								4);
					}

					if (o.equals("GBP")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								5);					}

					if (o.equals("JPY")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								6);
					}

					if (o.equals("CNY")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								7);
					}

					if (o.equals("PLN")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								8);
					}
					
					if (o.equals("CAD")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								9);
					}
					
					if (o.equals("ISK")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								10);
					}
					
					if (o.equals("SEK")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								11);
					}
					
					if (o.equals("CZK")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								12);
					}
					
					if (o.equals("LTL")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								13);
					}
					
					if (o.equals("DKK")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								14);
					}
					
					if (o.equals("KZT")) {
						addValuteObjectToArray(o, sizeList, rateList, nameList,
								15);
					}*/
					
					

				}

			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			spinner.setAdapter(mySpinner);
			spinner2.setAdapter(mySpinner);

			pd.dismiss();
			
			

		}

	}

	private void addValuteObjectToArray(String o, NodeList sizeList,
			NodeList rateList, NodeList nameList, int i) {
		Valute valute = new Valute();
		valute.setValuteCode(o);
		valute.setValueRate(String.valueOf(Float.parseFloat(rateList.item(0)
				.getNodeValue())
				/ Float.parseFloat(sizeList.item(0).getNodeValue())));
		valute.setValuteName(nameList.item(0).getNodeValue());
		valuteList.add(valute);

		//spinnerValues[i] = valute.getValuteName();            //native
		//spinnerSubValues[i] = valute.getValueRate();
	}
	
	
	public void Test(String valuteCode, NodeList sizeList,
			NodeList rateList, NodeList nameList, int imageId) {
		
		Valute valute = new Valute();
		valute.setValuteCode(valuteCode);
		valute.setValueRate(String.valueOf(Float.parseFloat(rateList.item(0)
				.getNodeValue())
				/ Float.parseFloat(sizeList.item(0).getNodeValue())));
		valute.setValuteName(nameList.item(0).getNodeValue());
		valuteList.add(valute);
		
		spinnerList.add(new ValuteSpinnerModel(valute.getValuteName(), valute.getValueRate(), imageId));
		
	}
}















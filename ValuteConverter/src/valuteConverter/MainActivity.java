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

	private StringBuilder editText1String = new StringBuilder();

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
	

		spinner = (Spinner) findViewById(R.id.spinner1);

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		
		new PostAsync().execute();

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

		editText2.setText(String.valueOf((Float
				.parseFloat(firstSpinnerValuteRate)
				/ Float.parseFloat(secondSpinnerValuteRate) * Float
				.parseFloat(mainSpinnerValue))));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.button1:
			insert("1");
			break;

		case R.id.button2:
			insert("2");
			break;

		case R.id.button3:
			insert("3");
			break;

		case R.id.button4:
			insert("4");
			break;

		case R.id.button5:
			insert("5");
			break;

		case R.id.button6:
			insert("6");
			break;

		case R.id.button7:
			insert("7");
			break;

		case R.id.button8:
			insert("8");
			break;

		case R.id.button9:
			insert("9");
			break;

		case R.id.button0:
			insert("0");
			break;

		case R.id.buttonC:
			clear();
			break;

		case R.id.buttonPimp:
			insertPimp();
			break;

		default:
			break;
		}

	}

	private void insert(String s) {
		editText1String.append(s);
		editText1.setText(String.valueOf(editText1String));
		Convert();
	}

	private void insertPimp() {
		if (!(editText1.getText().toString().contains("."))) {
			editText1String.append(".");
			editText1.setText(editText1String);
		}

		if (editText1String.substring(0, 1).equals(".")) {
			editText1String.insert(0, "0");
			editText1.setText(editText1String);
		}
	}

	private void clear() {
		if (editText1String.length() > 1) {
			editText1String.delete((editText1String.length() - 1), editText1String.length());
			editText1.setText(editText1String);
		} else {
			editText1String.delete(0, editText1String.length());
			editText1.setText("0");
		}
		Convert();

	}

	class PostAsync extends AsyncTask<Void, Void, Void> {
		ProgressDialog pd;

		MySpinner mySpinner = new MySpinner(MainActivity.this,
				R.layout.spinner_row, getLayoutInflater(), spinnerList);

		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MainActivity.this,
					"Отримую курс валют", "Зачекайте ...", true, false);

			Valute grn = new Valute();
			grn.setValueRate("1");
			grn.setValuteName("гривня");

			valuteList.add(grn);

			spinnerList.add(new ValuteSpinnerModel(grn.getValuteName(), grn
					.getValueRate(), R.drawable.ukraine));

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {

				URL url = new URL("http://bank-ua.com/export/currrate.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
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

					if (charList.item(0).getNodeValue().equals("USD")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.united);
					}

					if (charList.item(0).getNodeValue().equals("EUR")) {
						addValute(sizeList, rateList, nameList, R.drawable.euro);
					}

					if (charList.item(0).getNodeValue().equals("BYR")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.belarus);
					}

					if (charList.item(0).getNodeValue().equals("RUB")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.russia);
					}

					if (charList.item(0).getNodeValue().equals("GBP")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.unitedkingdom);
					}

					if (charList.item(0).getNodeValue().equals("JPY")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.japan);
					}

					if (charList.item(0).getNodeValue().equals("CNY")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.china);
					}

					if (charList.item(0).getNodeValue().equals("PLN")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.poland);
					}

					if (charList.item(0).getNodeValue().equals("CAD")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.canada);
					}

					if (charList.item(0).getNodeValue().equals("ISK")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.spain);
					}

					if (charList.item(0).getNodeValue().equals("SEK")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.shveden);
					}

					if (charList.item(0).getNodeValue().equals("CZK")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.czech);
					}

					if (charList.item(0).getNodeValue().equals("LTL")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.litva);
					}

					if (charList.item(0).getNodeValue().equals("DKK")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.dackiy);
					}

					if (charList.item(0).getNodeValue().equals("KZT")) {
						addValute(sizeList, rateList, nameList,
								R.drawable.kazakhstan);
					}

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

	private void addValute(NodeList sizeList, NodeList rateList,
			NodeList nameList, int imageId) {

		Valute valute = new Valute();
		valute.setValueRate(String.valueOf(Float.parseFloat(rateList.item(0)
				.getNodeValue())
				/ Float.parseFloat(sizeList.item(0).getNodeValue())));
		valute.setValuteName(nameList.item(0).getNodeValue());
		valuteList.add(valute);
		
		
		spinnerList.add(new ValuteSpinnerModel(valute.getValuteName(), valute
				.getValueRate(), imageId));

	}
}

package valuteConverter;

import java.util.List;

import gen.valuteConverter.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySpinner extends ArrayAdapter<ValuteSpinnerModel> {

	List<ValuteSpinnerModel> spinnerList;	
	
	LayoutInflater inflater;
	
	public MySpinner(Context ctx, int t, LayoutInflater inflater, List<ValuteSpinnerModel> spinnerList) {
		super(ctx, t, spinnerList);
		
		this.inflater = inflater;
		this.spinnerList = spinnerList;
	}

	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomView(position, cnvtView, prnt);
	}

	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		return getCustomView(pos, cnvtView, prnt);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View mySpinner = inflater.inflate(R.layout.spinner_row, parent, false);
		TextView mainText = (TextView) mySpinner.findViewById(R.id.name);
		mainText.setText(spinnerList.get(position).getValue());

		TextView subSpinner = (TextView) mySpinner.findViewById(R.id.subName);
		subSpinner.setText(spinnerList.get(position).getSubValue());

		ImageView leftIcon = (ImageView) mySpinner.findViewById(R.id.image);
		leftIcon.setImageResource(spinnerList.get(position).getImage());

		return mySpinner;

	}

}

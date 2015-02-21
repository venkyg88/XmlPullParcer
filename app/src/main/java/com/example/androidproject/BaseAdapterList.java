package com.example.androidproject;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseAdapterList extends BaseAdapter{
	ArrayList<Employee> listModel;
	Context mctx;
	int pos ;

	BaseAdapterList(Context ctx,   ArrayList<Employee> listModel,int i){
		this.mctx = ctx;
		this.listModel = listModel;
		//pos = i;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listModel.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//Toast.makeText(mctx, "text", Toast.LENGTH_LONG).show();

		LayoutInflater li = (LayoutInflater) mctx  
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  


		if(convertView == null){
	//		Toast.makeText(mctx, "text", Toast.LENGTH_LONG).show();
			convertView = li.inflate(R.layout.row, null);
			
		
		}
		
		
		TextView rollnum = (TextView)convertView.findViewById(R.id.name);
		rollnum.setText(""+listModel.get(position).getName());

		TextView name = (TextView)convertView.findViewById(R.id.id);
		name.setText(""+listModel.get(position).getId());

		TextView department = (TextView)convertView.findViewById(R.id.department);
		department.setText(""+listModel.get(position).getDepartment());

	

     /*    if(position == pos){
        	 rollnum.setVisibility(View.GONE);
         }else{
        	 rollnum.setVisibility(View.VISIBLE);
        	 
         }
*/


		return convertView;
	}

}

package com.example.androidproject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements CallBackData{
	private ProgressDialog progress;
	ListView lst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RequestTask req =  new RequestTask(this);
		req.execute("http://192.168.1.20/sample123.html");


		lst = (ListView)findViewById(R.id.listviewdisplay);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void requestResponse(String response) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, ""+response, Toast.LENGTH_LONG).show();
		InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		ArrayList<Employee> emp = parse(stream);
		//progress.dismiss();
		for (int i = 0; i < emp.size(); i++) {
		//	Toast.makeText(this, ""+emp.get(i).getName(), Toast.LENGTH_LONG).show();

		}
		
		BaseAdapterList adapter = new BaseAdapterList(MainActivity.this, emp, 0);
		lst.setAdapter(adapter);
	}

	public ArrayList<Employee> parse(InputStream is) {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factory.setNamespaceAware(true);
		try {
			parser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			parser.setInput(is, null);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int eventType = 0;
		try {
			eventType = parser.getEventType();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Employee employee = null;
		String text = null;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String tagname = parser.getName();

			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (tagname.equalsIgnoreCase("employee")) {
					employee = new Employee();
				}
				break;
			case XmlPullParser.TEXT:
				text = parser.getText();
				break;

			case XmlPullParser.END_TAG:
				if (tagname.equalsIgnoreCase("employee")) {
					//������������������������// add employee object to list
					employees.add(employee);
				} else if (tagname.equalsIgnoreCase("name")) {
					employee.setName(text);
				} else if (tagname.equalsIgnoreCase("empid")) {
					employee.setId(Integer.parseInt(text));
				} else if (tagname.equalsIgnoreCase("department")) {
					employee.setDepartment(text);
				}
				break;
			default:
				break;
			}
			try {
				eventType = parser.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return employees;
	}

	public void showProgress(){


		//	Now you can set some properties of this dialog. Such as , its style,its text e.t.c


	}

}

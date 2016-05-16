package com.weavebytes.httpfrompost;

import com.example.testapptouploadimagewithtwoparameters.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 main class that accepts the username and password & checks its validity
 */
public class MainActivity extends AppCompatActivity implements
		View.OnClickListener {

	private EditText edtUserid;
	private EditText edtEmail;
	private Button btnstart;
	private String userid;
	private String email;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		edtUserid = (EditText) findViewById(R.id.edtuserid);
		edtEmail = (EditText) findViewById(R.id.edtemail);
		btnstart = (Button) findViewById(R.id.btnstart);

		btnstart.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btnstart:

			Toast.makeText(getApplicationContext(), "Save clicked",
					Toast.LENGTH_LONG).show();
			userid = edtUserid.getText().toString();
			email = edtEmail.getText().toString();
			if (userid.equals("")) {
				edtUserid.setError("UserId Required");
				return;
			}
			if (email.equals("")) {
				edtEmail.setError("E-mail Required");
				return;
			} else {
				Config.U_ID = Long.parseLong(userid);
				Config.E_MAIL = email;
				Intent it = new Intent(MainActivity.this, UploadActivity.class);
				startActivity(it);
			}
		}
	}
}// MainActivity

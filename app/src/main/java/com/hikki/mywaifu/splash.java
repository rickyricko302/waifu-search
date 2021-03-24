package com.hikki.mywaifu;
import android.os.*;
import android.support.v7.app.*;
import java.io.*;
import java.net.*;
import org.json.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;

public class splash extends AppCompatActivity
{
	LinearLayout lr;
	Button bt;
	JSONArray ja;
	ArrayList<String> link = new ArrayList<>();
	ArrayList<String> name = new ArrayList<>();
	ArrayList<String> anime = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		lr = findViewById(R.id.lin);
		bt = findViewById(R.id.buttons);
		new loadSata().execute();
		bt.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					new loadSata().execute();
					hide();
				}
		
		});
	}
	
	private void tampil(){
		lr.animate().alpha(255);
	}
	private void hide(){
		lr.animate().alpha(0);
		lr.setVisibility(View.GONE);
	}
	public class loadSata extends AsyncTask<Void,Void,Boolean>
	{
		Exception error;
		@Override
		protected Boolean doInBackground(Void[] p1)
		{
			// TODO: Implement this method
			try
			{			
				URL u = new URL("https://waifu-generator.vercel.app/api/v1");
				URLConnection uc = u.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String g;
				StringBuilder sb = new StringBuilder();
				while ((g = br.readLine())!=null){
					sb.append(g);
				}
				ja = new JSONArray(sb.toString());
				return true;
			}
			catch (Exception e){
				error = e;
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO: Implement this method
			if (result){
				try{
				for (int i=0;i<ja.length();i++){
					link.add(ja.getJSONObject(i).get("image").toString());
					name.add(ja.getJSONObject(i).get("name").toString());
					anime.add(ja.getJSONObject(i).get("anime").toString());
				}
				Intent in = new Intent(splash.this,MainActivity.class);
				in.putExtra("link",link);
				in.putExtra("name",name);
				in.putExtra("anime",anime);
				startActivity(in);
				}catch(JSONException e){}
				
			}
			else{
				lr.setVisibility(View.VISIBLE);
				tampil();
			}
			super.onPostExecute(result);
		}

	}	
}

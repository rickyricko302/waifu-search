package com.hikki.mywaifu;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.View.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.squareup.picasso.*;
import com.synnapps.carouselview.*;
import java.text.*;
import java.util.*;
import android.graphics.*;

public class MainActivity extends AppCompatActivity implements OnClickListener
{

	boolean s = false;
	CarouselView cv;
	ArrayList<String> link = new ArrayList<>();
	ArrayList<String> name = new ArrayList<>();
	ArrayList<String> anime = new ArrayList<>();
	String type = "number";
	EditText ed;
	int rand;
	TextView dates;
	Button number,names,animes;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		cv = findViewById(R.id.xv);
		Intent in = getIntent();
		link = in.getStringArrayListExtra("link");
		name = in.getStringArrayListExtra("name");
		anime = in.getStringArrayListExtra("anime");
		cv.setPageCount(5);
		rand = new Random().nextInt(link.size()-10)+10;
		number = findViewById(R.id.num);
		names = findViewById(R.id.namec);
		animes = findViewById(R.id.animec);
		dates = findViewById(R.id.dates);
		number.setOnClickListener(this);
		names.setOnClickListener(this);
		animes.setOnClickListener(this);
		ed = findViewById(R.id.edlimit);
		Format dateFormat = new SimpleDateFormat("EEE, dd-MM-yy");
		String res = dateFormat.format(new Date());
		dates.setText(res);
		cv.setImageListener(new ImageListener(){

				@Override
				public void setImageForPosition(int p1, ImageView p2)
				{
					// TODO: Implement this method	
				Picasso.with(getApplicationContext()).load(link.get(rand-p1)).skipMemoryCache().placeholder(R.drawable.wait).into(p2);
				}
			});
		cv.setImageClickListener(new ImageClickListener(){

				@Override
				public void onClick(int p1)
				{
					// TODO: Implement this method
					ArrayList<String> klink = new ArrayList<>();
					ArrayList<String> kname = new ArrayList<>();
					ArrayList<String> kanime = new ArrayList<>();
					klink.add(link.get(rand-p1));
					kname.add(name.get(rand-p1));
					kanime.add(anime.get(rand-p1));
					Intent i = new Intent(MainActivity.this,show.class);
					i.putExtra("klink",klink);
					i.putExtra("kname",kname);
					i.putExtra("kanime",kanime);
					startActivity(i);		
					//Toast.makeText(getApplicationContext(),String.valueOf(name.get(rand-p1)),Toast.LENGTH_LONG).show();
				}		
		});
		ed.setOnEditorActionListener(new TextView.OnEditorActionListener(){

				@Override
				public boolean onEditorAction(TextView p1, int p2, KeyEvent p3)
				{
					// TODO: Implement this method
					if (p2 == EditorInfo.IME_ACTION_GO){
						ArrayList<String> klink = new ArrayList<>();
						ArrayList<String> kname = new ArrayList<>();
						ArrayList<String> kanime = new ArrayList<>();
						String g = p1.getText().toString();
						Intent i = new Intent(MainActivity.this,show.class);
						
						
						switch(type){
							case "number":
								if (g.contains("-")){
									int a = Integer.parseInt(g.split("-")[0]);
									int b = Integer.parseInt(g.split("-")[1]);
									//a = 1, b = 5
									for (int k=a;k<b;k++){
										klink.add(link.get(k));
										kname.add(name.get(k));
										kanime.add(anime.get(k));
									}
									i.putExtra("klink",klink);
									i.putExtra("kname",kname);
									i.putExtra("kanime",kanime);
									startActivity(i);
								}
								else{
									Toast.makeText(getApplicationContext(),"Must contain symbol - ",Toast.LENGTH_LONG).show();
								}
								break;
							case "nama":
								for (int k=0;k<link.size();k++){
									if (name.get(k).contains(g)){
										klink.add(link.get(k));
										kname.add(name.get(k));
										kanime.add(anime.get(k));
									}
								}
								if (klink.size()==0){
									Toast.makeText(getApplicationContext(),"Not found "+g,Toast.LENGTH_LONG).show();
								}
								else{
									i.putExtra("klink",klink);
									i.putExtra("kname",kname);
									i.putExtra("kanime",kanime);
									startActivity(i);
								}
								break;
							case "anime":
								for (int k=0;k<link.size();k++){
									if (anime.get(k).contains(g)){
										klink.add(link.get(k));
										kname.add(name.get(k));
										kanime.add(anime.get(k));
									}
								}
								if (klink.size()==0){
									Toast.makeText(getApplicationContext(),"Not found "+g,Toast.LENGTH_LONG).show();
								}
								else{
									i.putExtra("klink",klink);
									i.putExtra("kname",kname);
									i.putExtra("kanime",kanime);
									startActivity(i);
								}
								break;
						}
						
					}
					return false;
				}
			
		});
		
    }

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		ed.setText("");
		switch(p1.getId()){
			
			case R.id.num:
				ed.setHint("input num to num (ex : 1-10). Max "+String.valueOf(link.size()));
				type = "number";
				number.setTextColor(Color.parseColor("#0b1e37"));
				names.setTextColor(Color.parseColor("#FFFFFF"));
				animes.setTextColor(Color.parseColor("#FFFFFF"));
				//Toast.makeText(getApplicationContext(),String.valueOf(link.size())+"\n"+String.valueOf(name.size())+"\n"+String.valueOf(anime.size()),Toast.LENGTH_LONG).show();
				break;
			case R.id.namec:
				ed.setHint("input name character anime");
				type = "nama";
				animes.setTextColor(Color.parseColor("#FFFFFF"));
				number.setTextColor(Color.parseColor("#FFFFFF"));
				names.setTextColor(Color.parseColor("#0b1e37"));
				break;
			case R.id.animec:
				ed.setHint("input title anime");
				type = "anime";
				number.setTextColor(Color.parseColor("#FFFFFF"));
				animes.setTextColor(Color.parseColor("#0b1e37"));
				names.setTextColor(Color.parseColor("#FFFFFF"));
				break;
		}
	}


		
		
	
}

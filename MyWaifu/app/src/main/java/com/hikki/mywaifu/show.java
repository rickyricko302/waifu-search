package com.hikki.mywaifu;
import android.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.squareup.picasso.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class show extends AppCompatActivity
{

	ImageView back,saveimg;
	int pos,y;
	RecyclerView rl;
	String photoFile,urlasli;

	ProgressDialog mProgressDialog;
	adapter adap;
	private static int req = 1;
	ArrayList<String> klink = new ArrayList<>();
	ArrayList<String> kname = new ArrayList<>();
	ArrayList<String> kanime = new ArrayList<>();
	RelativeLayout rls;
	Button backf,save;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		Intent i = getIntent();
		klink = i.getStringArrayListExtra("klink");
		kname = i.getStringArrayListExtra("kname");
		kanime = i.getStringArrayListExtra("kanime");
		back = findViewById(R.id.baliks);
		rl = findViewById(R.id.rv);
		rls = findViewById(R.id.rls);
		backf = findViewById(R.id.buttonback);
		save = findViewById(R.id.buttonsave);
		saveimg = findViewById(R.id.imgsave);
		//Toast.makeText(getApplicationContext(),String.valueOf(klink.size())+"\n"+String.valueOf(kname.size())+"\n"+String.valueOf(kanime.size()),Toast.LENGTH_LONG).show();
		back.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					finish();
				}		
		});
		LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
		rl.setLayoutManager(lm);
		adap = new adapter(getApplicationContext(),klink,kname,kanime);
		rl.setAdapter(adap);
		adap.setclick(new adapter.itemclick(){

				@Override
				public Void onitemclick(View view, int posisi)
				{
					// TODO: Implement this method
					pos = posisi;
					Picasso.with(getApplicationContext()).load(klink.get(posisi)).into(saveimg);
					rls.setAlpha(0);
					rls.setVisibility(View.VISIBLE);
					rls.animate().alpha(255);
					return null;
				}	
		});
		
		backf.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					rls.animate().alpha(0);
					rls.setVisibility(View.GONE);
				}	
		});
		save.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if (Build.VERSION.SDK_INT > 22){
					String[] p = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
					if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
						requestPermissions(p,req);
					}
					else{
						new DownloadFile().execute(klink.get(pos));
					}
					}
					else{
						new DownloadFile().execute(klink.get(pos));
					}
				}
		});
		rls.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					return;
				}
		});
		saveimg.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					return;
				}

			
		});
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		// TODO: Implement this method
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//	Toast.makeText(getApplicationContext(),String.valueOf(grantResults),Toast.LENGTH_SHORT).show();
		if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED){
			
			new DownloadFile().execute(klink.get(pos));
		}
		else{
			Toast.makeText(getApplicationContext(),"failed enable permission:(",Toast.LENGTH_LONG).show();
		}
    }
	private File getDir() {
        File sdDir = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Waifu Image");
    }
	private class DownloadFile extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create progress dialo
			mProgressDialog = new ProgressDialog(show.this);
			// Set your progress dialog Title
			mProgressDialog.setTitle("Downloading...");
			// Set your progress dialog Message
			mProgressDialog.setMessage("Please Wait, Do Not Exit!");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// Show progress dialog
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... Url) {
			try {
				String a = Url[0];
				
				if (a.contains("-7")){
					for (int i = a.length();i>0;i--){
						if (String.valueOf(a.charAt(i-1)).equals(".")){
							y = i;
						}
						if (String.valueOf(a.charAt(i-1)).equals("-")){
							a = a.replace(a.substring(i-1,y-1),"");
							break;
						}
					}
				}
				
				URL url = new URL(a);
				URLConnection connection = url.openConnection();
				connection.connect();

				// Detect the file lenghth
				int fileLength = connection.getContentLength();
				// Locate storage location
				// Download the file
				InputStream input = new BufferedInputStream(url.openStream());

				// Save the downloaded file
				File pictureFileDir = getDir();

				if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

					Toast.makeText(getApplicationContext(), "Can't create directory to save image.",
								   Toast.LENGTH_LONG).show();
				}
				 photoFile =  kname.get(pos)+"("+kanime.get(pos)+")"+ ".jpg";
			
				String filename = pictureFileDir.getPath() + File.separator + photoFile;

				File pictureFile = new File(filename);
				OutputStream output = new FileOutputStream(pictureFile);
				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress
					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
				// Close connection
				output.flush();
				output.close();
				input.close();
				
			} catch (Exception e) {
				// Error Log
				Log.e("Error", e.getMessage());
				e.printStackTrace();
				//Toast.makeText(getApplicationContext(),"unkowm error",Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			// Update the progress dialog
			mProgressDialog.setProgress(progress[0]);		
			// Dismiss the progress dialog
			//mProgressDialog.dismiss();
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			mProgressDialog.hide();
			Toast.makeText(getApplicationContext(),"Sukes simpan di folder Picture/Waifu Image/"+photoFile,Toast.LENGTH_LONG).show();
		}
		//error file url
		
	}
}

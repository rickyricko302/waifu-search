package com.hikki.mywaifu;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.squareup.picasso.*;
import java.util.*;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>
{

	private Context c;
	private itemclick ic;
	private ArrayList<String> klink = new ArrayList<>();
	private ArrayList<String> kname = new ArrayList<>();
	private ArrayList<String> kanime = new ArrayList<>();
	private int lastPosition = -1;
	public adapter(Context c,ArrayList<String> klink,ArrayList<String> kname,ArrayList<String> kanime){
		this.c = c;
		this.klink = klink;
		this.kname = kname;
		this.kanime = kanime;
	}
	@Override
	public adapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		View v = LayoutInflater.from(c).inflate(R.layout.item_layout,p1,false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(adapter.ViewHolder v, int p2)
	{
		// TODO: Implement this method
		v.judul.setText(String.valueOf(p2+1)+". "+kanime.get(p2));
		v.nama.setText("# "+kname.get(p2));
		Picasso.with(c).load(klink.get(p2)).placeholder(R.drawable.a).into(v.img);
		setAnimation(v.cv,p2);
	}
	private void setAnimation(View viewToAnimate, int position) {
		// If the bound view wasn't previously displayed on screen, it's animated
		if (position > lastPosition) {
			Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
			viewToAnimate.startAnimation(animation);
			lastPosition = position;
		}
		else{
			Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
			viewToAnimate.startAnimation(animation);
		}
	}
	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return klink.size();
	}
	

	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView nama,judul;
		ImageView img;
		CardView cv;
		ViewHolder(View v){		
			super(v);
			nama = v.findViewById(R.id.namaanime);
			judul = v.findViewById(R.id.judulanime);
			img = v.findViewById(R.id.imgwaifu);
			cv = v.findViewById(R.id.cv);
			v.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						// TODO: Implement this method
						if (ic!=null)ic.onitemclick(p1,getAdapterPosition());
					}
					
				
			});
		}
	}
	public String getItem(int id) {
		return klink.get(id);
	}
	public void setclick(itemclick ic){
		this.ic=ic;
	}
	public interface itemclick{
		Void onitemclick(View view, int posisi);
	}
}

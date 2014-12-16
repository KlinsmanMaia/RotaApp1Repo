package com.uea.kmg.rotaapp1.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uea.kmg.rotaapp1.R;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class FavoriteAdapter extends BaseAdapter{
	private Context context;
	private List<FavoriteLocation> favoritesList;
	
	public FavoriteAdapter(Context context, List<FavoriteLocation> favoritesList) {
		this.context = context;
		this.favoritesList = favoritesList;
	}
	
	
	@Override
	public int getCount() {
		return favoritesList.size();
	}

	@Override
	public Object getItem(int position) {
		return favoritesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FavoriteLocation favorite = this.favoritesList.get(position);
		ViewHolder holder;
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null){
			convertView = inflate.inflate(R.layout.favorite_item, null);
			holder = new ViewHolder();
			holder.description = (TextView) convertView.findViewById(R.id.tvLtItem);
			holder.imgFAvorite = (ImageView) convertView.findViewById(R.id.imgLtItem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.description.setText(favorite.getDescription());
		return convertView;
	}
	
	static class ViewHolder{
		private TextView description;
		private ImageView imgFAvorite;
	}

}

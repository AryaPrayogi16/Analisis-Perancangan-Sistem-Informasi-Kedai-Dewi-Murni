package com.apsi.Dewi_Murni.fragment;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.apsi.Dewi_Murni.R;
import com.apsi.Dewi_Murni.adapter.CategoryListAdapter;
import com.apsi.Dewi_Murni.dummy.MasterContent;
import com.apsi.Dewi_Murni.entity.ProductCategory;
import com.apsi.Dewi_Murni.sqlite.DatabaseManager;
import com.apsi.Dewi_Murni.sqlite.ds.ProductCategoryDataSource;
import com.apsi.Dewi_Murni.utils.Constants;
import com.apsi.Dewi_Murni.utils.Shared;

public class CategoryListFragment extends Fragment implements OnClickListener{
	private MasterContent.DummyItem mItem;
	private ListView lv;
	private CategoryListAdapter adapter;
	private ArrayList<ProductCategory> dtlist;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
			mItem = MasterContent.ITEM_MAP.get(getArguments().getString(Constants.ARG_ITEM_ID));
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_category_list,container, false);
		
		lv = (ListView)rootView.findViewById(R.id.listView1);
		adapter = new CategoryListAdapter(getActivity(),mItem.id);
		
		popolateAdapter();
		lv.setAdapter(adapter);
				
		TextView title = (TextView)rootView.findViewById(R.id.item_detail);
		title.setTypeface(Shared.OpenSansSemibold);
		if (mItem != null) {
			title.setText(mItem.content);
		}
		
		rootView.findViewById(R.id.imageButton1).setOnClickListener(this);;
		
		return rootView;
	}
	
	private void popolateAdapter()
	{
		SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
        ProductCategoryDataSource ds = new ProductCategoryDataSource(db);
        dtlist = ds.getAll();
     
		adapter.set(dtlist);
		DatabaseManager.getInstance().closeDatabase();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Fragment fragment  = new CategoryAddFragment();
		Bundle arguments = new Bundle();
		arguments.putString(Constants.ARG_ITEM_ID, mItem.id);
		fragment.setArguments(arguments);
		getFragmentManager().beginTransaction()
		.setTransition(android.R.anim.slide_in_left)
		.addToBackStack("add")
		.replace(R.id.master_detail_container, fragment).commit();
	}

}



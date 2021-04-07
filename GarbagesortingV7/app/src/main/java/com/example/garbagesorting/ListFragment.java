package com.example.garbagesorting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {
    private static ItemsDB itemsDB;
    private ArrayList<Item> localDB;
    private ItemAdapter adapter;
    private RecyclerView itemList;
    // private TextView listThings;

    // Implement update method because it is an Observer
    // So when observed data is changed this method is called,
    // so gui shows correct updated data
    public void update(Observable observable, Object data) {
        localDB = itemsDB.getAll();
        adapter.notifyDataSetChanged();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
        itemsDB.addObserver(this); // ListFragment subscribes to updates of itemDB
        localDB = itemsDB.getAll();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_list, container, false);

        itemList = v.findViewById(R.id.listItems); // in fragment_list.xml
        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));

        // itemList is a RecyclerView so needs an adapter to couple data to layout
        adapter = new ItemAdapter();
        itemList.setAdapter(adapter);
        return v;
    }

    // Uses one_row.xml to setup the layout of a row in the list fragment.
    // What is showed in one row in the recycler view is controlled by the ItemHolder
    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView garbageItemTextView, placementItemTextView, noView;

        // Constructor
        public ItemHolder(View itemView) {
            super(itemView);
            noView = itemView.findViewById(R.id.item_no);
            garbageItemTextView   = itemView.findViewById(R.id.item_what);
            placementItemTextView = itemView.findViewById(R.id.item_where);
            itemView.setOnClickListener(this);
        }

        // Bind data to the TextViews
        public void bind(Item item, int position) {
            String str = " " + position + " ";
            noView.setText(str);
            garbageItemTextView.setText(item.getWhat());
            placementItemTextView.setText(item.getWhere());
        }

        @Override
        public void onClick(View v) {
            String what = (String)((TextView)v.findViewById(R.id.item_what)).getText();
            itemsDB.removeItem(what);
        }
    }

    // The adapter couples the data in the itemsDB to the recycler view's layout
    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(v);
        }

        // When particular row is viewable we pick corresponding item from list localDB and binds
        // it to the view
        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = localDB.get(position);
            holder.bind(item, position);
        }

        @Override
        public int getItemCount() {
            return localDB.size();
        }
    }
}
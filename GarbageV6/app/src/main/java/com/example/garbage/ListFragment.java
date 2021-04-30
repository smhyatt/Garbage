package com.example.garbage;

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
    private RecyclerView itemList;
    private ItemAdapter mAdapter;
    private ArrayList<Item> localDB;

    public void update(Observable observable, Object data) {
        localDB = itemsDB.listAll();
        mAdapter.notifyDataSetChanged();
    }


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB= ItemsDB.get(getActivity());
        itemsDB.addObserver(this);
        localDB= itemsDB.listAll();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_list, container, false);
        itemList= v.findViewById(R.id.listItems);
        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter= new ItemAdapter();
        itemList.setAdapter(mAdapter);
        return v;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mWhatTextView, mWhereTextView, mNoView;

        public ItemHolder(View itemView) {
            super(itemView);
            mNoView= itemView.findViewById(R.id.item_no);
            mWhatTextView= itemView.findViewById(R.id.item_what);
            mWhereTextView= itemView.findViewById(R.id.item_where);
        }

        public void bind(Item item, int position){
            mNoView.setText("    " + (position+1) + " ");
            mWhatTextView.setText(item.getWhat());
            mWhereTextView.setText(item.getWhere());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        public ItemAdapter() { }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
            View v= layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item=  localDB.get(position);
            holder.bind(item, position);
        }

        @Override
        public int getItemCount(){
            return localDB.size();
        }
    }


}

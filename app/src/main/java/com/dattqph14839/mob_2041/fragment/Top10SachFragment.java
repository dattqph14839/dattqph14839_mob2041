package com.dattqph14839.mob_2041.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.adapter.Top10SachAdapter;
import com.dattqph14839.mob_2041.dao.ThongKeDao;
import com.dattqph14839.mob_2041.model.Top;

import java.util.ArrayList;
import java.util.List;

public class Top10SachFragment extends Fragment {
    private List<Top> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Top10SachAdapter adapter;
    private ThongKeDao thongKeDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10_sach, container, false);
        thongKeDao=new ThongKeDao(getContext());
        list=thongKeDao.getTop();
        recyclerView=view.findViewById(R.id.rv_data_top10sach);
        adapter=new Top10SachAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
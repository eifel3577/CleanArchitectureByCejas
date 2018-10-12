package com.example.cleanarchitecturebycejas.Presentation.View.Adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * LayoutManager чтобы размещать итемы внутри {@link android.support.v7.widget.RecyclerView}.
 *
 */
public class UsersLayoutManager extends LinearLayoutManager {
    public UsersLayoutManager(Context context) {
        super(context);
    }
}
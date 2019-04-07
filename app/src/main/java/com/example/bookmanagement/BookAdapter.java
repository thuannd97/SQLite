package com.example.bookmanagement;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter{

    private List<Book> books;
    private Context mContext;
    private View.OnClickListener mOnItemtClickListener;

    public BookAdapter(List<Book> books, Context mContext){
        this.books = books;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookAdapter.MyViewHolder myViewHolder = (BookAdapter.MyViewHolder) holder;
        myViewHolder.txtBookName.setText(books.get(position).getBookName());
        myViewHolder.txtAuthor.setText(books.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public View.OnClickListener getmOnItemtClickListener() {
        return mOnItemtClickListener;
    }

    public void setmOnItemtClickListener(View.OnClickListener mOnItemtClickListener) {
        this.mOnItemtClickListener = mOnItemtClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivBook;
        TextView txtBookName;
        TextView txtAuthor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivBook = itemView.findViewById(R.id.ivBook);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemtClickListener);
        }
    }

}

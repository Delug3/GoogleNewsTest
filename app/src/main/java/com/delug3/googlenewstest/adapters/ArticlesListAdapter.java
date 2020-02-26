package com.delug3.googlenewstest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.delug3.googlenewstest.R;
import com.delug3.googlenewstest.models.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ViewHolder> {

        private List<Articles> articlesList;
        private Context context;
        private ItemClickListener mClickListener;

        public ArticlesListAdapter(List<Articles> articlesList) {
            this.articlesList = articlesList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Articles a = articlesList.get(position);
            holder.textViewTitle.setText(a.getTitle());
            holder.textViewDescription.setText(a.getDescription());

            //urltoImage: "urlToImage": "https://static01.nyt.com/images/2020/02/26/world/26virus-briefing-1sub/26virus-briefing-1sub-facebookJumbo-v2.jpg",
            Picasso.get()
                    .load(a.getUrlToImage())
                    .into(holder.imageViewUrl);
        }

        @Override
        public int getItemCount() {
            return articlesList.size();
        }

        public void addArticlesList(ArrayList<Articles> listArticles) {

            articlesList.addAll(listArticles);
            notifyDataSetChanged();

        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView textViewTitle;
            private TextView textViewDescription;
            private ImageView imageViewUrl;

            public ViewHolder(View itemView) {

                super(itemView);
                textViewTitle = itemView.findViewById(R.id.text_view_articles_title);
                textViewDescription = itemView.findViewById(R.id.text_view_articles_description);
                imageViewUrl = itemView.findViewById(R.id.image_view_articles);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }

        }

    // Getting data at click position
    public Articles getItem(int id) {
        return articlesList.get(id);
    }


    public void setContext(Context context) {
        this.context = context;
    }

    // Allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}

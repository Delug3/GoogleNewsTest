package com.delug3.googlenewstest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.delug3.googlenewstest.R;
import com.delug3.googlenewstest.models.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> implements Filterable {

        Context context;
        private List<Articles> articlesList;
        private List<Articles> articlesListFiltered;
        private ArticlesAdapterListener listener;

        public ArticlesAdapter(Context context, List articlesList, ArticlesAdapterListener listener) {
            this.context = context;
            this.listener = listener;
            this.articlesList = articlesList;
            this.articlesListFiltered = articlesList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_articles, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Articles articles = articlesListFiltered.get(position);
            holder.textViewTitle.setText(articles.getTitle());
            holder.textViewDescription.setText(articles.getDescription());

            //urltoImage: "urlToImage": "https://static01.nyt.com/images/2020/02/26/world/26virus-briefing-1sub/26virus-briefing-1sub-facebookJumbo-v2.jpg",
            Picasso.get()
                    .load(articles.getUrlToImage())
                    .into(holder.imageViewUrl);
        }

        @Override
        public int getItemCount() {

            return articlesListFiltered.size();

        }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    articlesListFiltered = articlesList;
                } else {
                    List filteredList = new ArrayList<>();
                    //String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Articles row : articlesList) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    articlesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articlesListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articlesListFiltered = (ArrayList) filterResults.values;
                notifyDataSetChanged();
            }
        };
        }



    public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textViewTitle;
            private TextView textViewDescription;
            private ImageView imageViewUrl;

            public ViewHolder(View itemView) {

                super(itemView);
                textViewTitle = itemView.findViewById(R.id.text_view_articles_title);
                textViewDescription = itemView.findViewById(R.id.text_view_articles_description);
                imageViewUrl = itemView.findViewById(R.id.image_view_articles);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onArticleSelected(articlesListFiltered.get(getAdapterPosition()));
                    }
                });
            }
        }

    // Parent activity will implement this method to respond to click events
    public interface ArticlesAdapterListener {
        void onArticleSelected(Articles articles);
    }

}

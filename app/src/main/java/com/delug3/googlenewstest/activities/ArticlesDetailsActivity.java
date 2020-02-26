package com.delug3.googlenewstest.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.delug3.googlenewstest.R;
import com.squareup.picasso.Picasso;

public class ArticlesDetailsActivity extends AppCompatActivity {

    private TextView textViewDetailsTitle, textViewDetailsContent, textViewDetailsDate;
    private ImageView imageViewDetailsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_details);

        imageViewDetailsUrl = findViewById(R.id.image_view_details_url);
        textViewDetailsTitle = findViewById(R.id.text_view_details_article_title);
        textViewDetailsContent = findViewById(R.id.text_view_detais_article_content);
        textViewDetailsDate = findViewById(R.id.text_view_details_article_date);

        String title, content, date, imageUrl;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                title = null;
                content = null;
                date = null;
                imageUrl = null;
            } else {

                title = extras.getString("ARTICLE_TITLE");
                content = extras.getString("ARTICLE_CONTENT");
                date = extras.getString("ARTICLE_DATE");
                imageUrl = extras.getString("ARTICLE_IMAGE");

                textViewDetailsTitle.setText(title);
                textViewDetailsContent.setText(content);
                textViewDetailsDate.setText(date);

                Picasso.get().load(imageUrl).into(imageViewDetailsUrl);
            }

        }
    }

}

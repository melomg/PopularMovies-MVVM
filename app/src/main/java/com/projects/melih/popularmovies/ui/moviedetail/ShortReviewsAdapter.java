package com.projects.melih.popularmovies.ui.moviedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.projects.melih.popularmovies.R;
import com.projects.melih.popularmovies.common.CollectionUtils;
import com.projects.melih.popularmovies.model.Review;

import java.util.List;

/**
 * Created by Melih Gültekin on 17.03.2018
 */

class ShortReviewsAdapter extends RecyclerView.Adapter<ShortReviewsAdapter.ReviewViewHolder> {
    private final Context context;
    private List<Review> reviews;

    ShortReviewsAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReviewViewHolder(inflater.inflate(R.layout.item_movie_review, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bindTo(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.size(reviews);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final ExpandableTextView tvReview;

        ReviewViewHolder(@NonNull View itemView, @NonNull Context context) {
            super(itemView);
            this.context = context;
            tvReview = itemView.findViewById(R.id.expand_text_view);
        }

        void bindTo(@Nullable final Review review) {
            if (review != null) {
                tvReview.setText(context.getString(R.string.review_author, review.getAuthor(), review.getContent()));
            }
        }
    }
}
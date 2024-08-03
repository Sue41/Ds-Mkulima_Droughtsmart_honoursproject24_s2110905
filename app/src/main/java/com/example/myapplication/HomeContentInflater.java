package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Resources.HomeItemContent;

import java.util.List;

public class HomeContentInflater extends RecyclerView.Adapter<HomeContentInflater.ViewHolder> {
    private List<HomeItemContent> items;

    public HomeContentInflater(List<HomeItemContent> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data to the views for the first item
        HomeItemContent item1 = items.get(position * 2);
      //  holder.item1Image.setImageResource(item1.getImageResId());
//holder.item1Text.setText(item1.getText());
       // holder.item1ButtonText.setText(item1.getButtonText());

        // Bind data to the views for the second item
        if ((position * 2 + 1) < items.size()) {
            HomeItemContent item2 = items.get(position * 2 + 1);
          //  holder.item2Image.setImageResource(item2.getImageResId());
         //   holder.item2Text.setText(item2.getText());
          //  holder.item2ButtonText.setText(item2.getButtonText());
        }
    }

    @Override
    public int getItemCount() {
        return (items.size() + 1) / 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item1Image;
        TextView item1Text;
        TextView item1ButtonText;
        ImageView item1ButtonIcon;

        ImageView item2Image;
        TextView item2Text;
        TextView item2ButtonText;
        ImageView item2ButtonIcon;

        public ViewHolder(View itemView) {
            super(itemView);
           // item1Image = itemView.findViewById(R.id.item1_image);
          //  item1Text = itemView.findViewById(R.id.item1_text);
          //  item1ButtonText = itemView.findViewById(R.id.item1_button_text);
         //   item1ButtonIcon = itemView.findViewById(R.id.item1_button_icon);

          //  item2Image = itemView.findViewById(R.id.item2_image);
          //  item2Text = itemView.findViewById(R.id.item2_text);
         //   item2ButtonText = itemView.findViewById(R.id.item2_button_text);
         //   item2ButtonIcon = itemView.findViewById(R.id.item2_button_icon);
        }
    }
}

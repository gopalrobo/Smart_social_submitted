/*
 * Copyright (c) 2014 Davide Cirillo
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *     Come on, don't tell me you read that.
 */

package smart.social.worker.attend;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;

import java.util.ArrayList;

import smart.social.worker.R;

/**
 * Created by davidecirillo on 13/03/16.
 */
class MySampleToolbarAdapter extends MultiChoiceAdapter<MySampleToolbarViewHolder> {

    private final ArrayList<String> mList;
    private final Context mContext;

    MySampleToolbarAdapter(ArrayList<String> stringList, Context context) {
        this.mList = stringList;
        this.mContext = context;
    }

    @Override
    public MySampleToolbarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MySampleToolbarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_toolbar, parent, false));
    }


    @Override
    public void onBindViewHolder(MySampleToolbarViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTextView.setText(mList.get(position)+"\n13R2" + String.valueOf(position));
    }


    /**
     * Override this method to implement a custom active/deactive state
     */
    @Override
    public void setActive(@NonNull View view, boolean state) {
        final ImageView tickImage = (ImageView) view.findViewById(R.id.tick_image);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
        CardView cardView = (CardView) view.findViewById(R.id.card_view);
        if (relativeLayout != null) {
            if (state) {
                relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBackgroundLight));
                cardView.setScaleX(0.9f);
                cardView.setScaleY(0.9f);
                tickImage.setVisibility(View.VISIBLE);
            } else {
                relativeLayout.setBackgroundColor(Color.WHITE);
                cardView.setScaleX(1f);
                cardView.setScaleY(1f);
                tickImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected View.OnClickListener defaultItemViewClickListener(MySampleToolbarViewHolder holder, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Click on item " + position, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

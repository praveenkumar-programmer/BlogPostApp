package com.geeks4ever.blogpostapp.view.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.geeks4ever.blogpostapp.R;
import com.google.android.material.textview.MaterialTextView;

/*
 *  Created by Praveen Kumar on 17/2/21 9:22 PM for BlogPostApp.
 *  Copyright (c) 2021.
 *  Last modified 17/2/21 9:02 PM.
 *
 *  This file/part of BlogPostApp is OpenSource.
 *
 *  BlogPostApp is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  BlogPostApp is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Foobar.
 *  If not, see http://www.gnu.org/licenses/.
 */

public class PostViewHolder  extends RecyclerView.ViewHolder {
    public MaterialTextView userId, body;

    public PostViewHolder(View itemView) {
        super(itemView);
        userId = itemView.findViewById(R.id.post_user_id);
        body = itemView.findViewById(R.id.post_body);
    }

    public void setUserId(String userId) {
        this.userId.setText(userId);
    }

    public void setBody(String body) {
        this.body.setText(body);
    }
}

/*
* Copyright 2017 WalmartLabs
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ernpetstore.ern.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

import com.walmartlabs.electrode.reactnative.bridge.Bridgeable;

import static com.walmartlabs.electrode.reactnative.bridge.util.BridgeArguments.*;

public class Pet implements Parcelable, Bridgeable {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    private Pet() {}

    private Pet(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.name = builder.name;
        this.photoUrls = builder.photoUrls;
        this.tags = builder.tags;
        this.status = builder.status;
    }

    private Pet(Parcel in) {
        this(in.readBundle());
    }

    public Pet(@NonNull Bundle bundle) {
        if(!bundle.containsKey("name")){
            throw new IllegalArgumentException("name property is required");
        }

        if(!bundle.containsKey("photoUrls")){
            throw new IllegalArgumentException("photoUrls property is required");
        }

        this.id = getNumberValue(bundle, "id") == null ? null : getNumberValue(bundle, "id").longValue();
        this.category = bundle.containsKey("category") ? new Category(bundle.getBundle("category")) : null;
        this.name = bundle.getString("name");
        this.photoUrls = bundle.containsKey("photoUrls") ? getList(bundle.getStringArray("photoUrls"), String.class) : null;
        this.tags = bundle.containsKey("tags") ? getList(bundle.getParcelableArray("tags"), Tag.class) : null;
        this.status = bundle.getString("status");
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    @Nullable
    public List<Tag> getTags() {
        return tags;
    }

    /**
    * pet status in the store
    *
    * @return String
    */
    @Nullable
    public String getStatus() {
        return status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(toBundle());
    }

    @NonNull
    @Override
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("name", this.name);
        updateBundleWithList(this.photoUrls, bundle, "photoUrls");
        if(this.id != null) {
            bundle.putLong("id", this.id);
        }
        if(this.category != null) {
            bundle.putBundle("category", this.category.toBundle());
        }
        if(this.tags != null) {
            updateBundleWithList(this.tags, bundle, "tags");
        }
        if(status != null) {
            bundle.putString("status", this.status );
        }
        return bundle;
    }

    public static class Builder {
        private final String name;
        private final List<String> photoUrls;
        private Long id;
        private Category category;
        private List<Tag> tags;
        private String status;

        public Builder(@NonNull String name, @NonNull List<String> photoUrls) {
            this.name = name;
            this.photoUrls = photoUrls;
        }

        @NonNull
        public Builder id(@Nullable Long id) {
            this.id = id;
            return this;
        }
        @NonNull
        public Builder category(@Nullable Category category) {
            this.category = category;
            return this;
        }
        @NonNull
        public Builder tags(@Nullable List<Tag> tags) {
            this.tags = tags;
            return this;
        }
        @NonNull
        public Builder status(@Nullable String status) {
            this.status = status;
            return this;
        }

        @NonNull
        public Pet build() {
            return new Pet(this);
        }
    }
}

package com.example.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String username;
    private int profileImage;
    private int postImage; // Untuk gambar dummy (resource ID)
    private String imageUri; // Tambahan: Untuk gambar dari galeri
    private String caption;

    // Constructor untuk data dummy
    public Post(String username, int profileImage, int postImage, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.imageUri = null;
        this.caption = caption;
    }

    // Constructor untuk postingan baru dari galeri
    public Post(String username, int profileImage, String imageUri, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImage = 0;
        this.imageUri = imageUri;
        this.caption = caption;
    }

    protected Post(Parcel in) {
        username = in.readString();
        profileImage = in.readInt();
        postImage = in.readInt();
        imageUri = in.readString();
        caption = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) { return new Post(in); }
        @Override
        public Post[] newArray(int size) { return new Post[size]; }
    };

    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public String getImageUri() { return imageUri; }
    public String getCaption() { return caption; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(profileImage);
        dest.writeInt(postImage);
        dest.writeString(imageUri);
        dest.writeString(caption);
    }
}
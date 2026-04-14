package com.example.tp2;

import java.util.ArrayList;

public class DataSource {

    public static ArrayList<Post> allPosts = new ArrayList<>();
    private static boolean isInitialized = false;

    public static ArrayList<Post> getAllPosts() {
        if (!isInitialized) {
            // Membuat daftar 10 akun utama untuk Home
            ArrayList<Post> baseAccounts = new ArrayList<>();
            baseAccounts.add(new Post("gebysimamora", R.drawable.profile1, R.drawable.feed1, "Exploring the new art gallery today! 🎨✨"));
            baseAccounts.add(new Post("sleepy_hammy", R.drawable.profile2, R.drawable.feed2, "Cute finds at the store today! 🎀"));
            baseAccounts.add(new Post("roller.girl", R.drawable.profile3, R.drawable.feed3, "Tropical vibes only 🌴🌺"));
            baseAccounts.add(new Post("doggo_swag", R.drawable.profile4, R.drawable.feed4, "Friendship rings with the besties! 💍✨"));
            baseAccounts.add(new Post("princess.barbie", R.drawable.profile5, R.drawable.feed5, "Matching jewelry check! 💅"));
            baseAccounts.add(new Post("chaos.queen", R.drawable.profile6, R.drawable.feed6, "Best night ever at the concert!! 🎶🔥"));
            baseAccounts.add(new Post("bratz.life", R.drawable.profile7, R.drawable.feed7, "Chilling by the bonfire under the stars ⛺🔥"));
            baseAccounts.add(new Post("messy.vibes", R.drawable.profile8, R.drawable.feed8, "Late night hangs and memories 🥂✨"));
            baseAccounts.add(new Post("tired_coffee", R.drawable.profile9, R.drawable.feed9, "POV: You fell down and we are judging you 😂"));
            baseAccounts.add(new Post("rich_bratz", R.drawable.profile10, R.drawable.feed10, "Hands up in the air! What a party! 🙌"));

            // Memberikan 5 postingan untuk setiap akun agar sesuai ketentuan tugas aslab
            for (Post p : baseAccounts) {
                allPosts.add(new Post(p.getUsername(), p.getProfileImage(), R.drawable.feed8, "Late night hangs 🥂✨"));
                allPosts.add(new Post(p.getUsername(), p.getProfileImage(), R.drawable.feed9, "Random dump 😂"));
                allPosts.add(new Post(p.getUsername(), p.getProfileImage(), R.drawable.feed10, "Party time! 🙌"));
                allPosts.add(new Post(p.getUsername(), p.getProfileImage(), p.getProfileImage(), "Just me ✨"));

                // Postingan ke-5 menggunakan gambar original mereka yang ada di Home
                allPosts.add(new Post(p.getUsername(), p.getProfileImage(), p.getPostImage(), p.getCaption()));
            }
            isInitialized = true;
        }
        return allPosts;
    }

    // Fungsi untuk memfilter postingan khusus 1 akun (Untuk Profil)
    public static ArrayList<Post> getUserPosts(String username) {
        ArrayList<Post> userPosts = new ArrayList<>();
        for (Post p : getAllPosts()) {
            if (p.getUsername().equals(username)) {
                userPosts.add(p);
            }
        }
        return userPosts;
    }

    // Fungsi menambah postingan dari Galeri
    public static void addPost(Post post) {
        allPosts.add(0, post); // Tambahkan ke urutan indeks paling atas (0)
    }

    // Kembalikan 7 Highlight Story
    public static ArrayList<Story> generateDummyStories() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story("Art", R.drawable.feed1));
        stories.add(new Story("Cute", R.drawable.feed2));
        stories.add(new Story("Trip", R.drawable.feed3));
        stories.add(new Story("Besties", R.drawable.feed4));
        stories.add(new Story("OOTD", R.drawable.feed5));
        stories.add(new Story("Concert", R.drawable.feed6));
        stories.add(new Story("Camp", R.drawable.feed7));
        return stories;
    }
}
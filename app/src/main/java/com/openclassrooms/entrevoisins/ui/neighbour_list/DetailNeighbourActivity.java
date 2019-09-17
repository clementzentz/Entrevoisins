package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;

public class DetailNeighbourActivity extends AppCompatActivity implements Serializable {

    public static final String BUNDLE_EXTRA_FAVORIS = "BUNDLE_EXTRA_FAVORIS";

    private TextView neighbourWhiteNametxt;
    private  TextView neighbourBlackNametxt;
    private ImageView avatarImageView;
    private TextView neighbourPhone;

    ImageButton retourMainActivity;
    FloatingActionButton favorisFAB;

    public Neighbour mCurrentNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);

        getIncomingNeighbour();

        retourMainActivity = findViewById(R.id.return_mainActivity_btn);
        retourMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_EXTRA_FAVORIS, mCurrentNeighbour);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        favorisFAB = findViewById(R.id.favories_fab);
        favorisFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNeighbour.setFavoris(true);
            }
        });
    }

    private void getIncomingNeighbour(){
            if (getIntent().hasExtra("neighbour")) {
                mCurrentNeighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");
                String imageAvatarUrl =  mCurrentNeighbour.getAvatarUrl().replaceAll("150", "300");
                String neighbourName = mCurrentNeighbour.getName();
                String neighbourPhone = mCurrentNeighbour.getPhone();
                setActivityView(imageAvatarUrl, neighbourName, neighbourPhone);
            }
        }

    private void setActivityView(String anImageAvatarUrl, String aNeighbourName, String aNeighbourPhone){

        neighbourWhiteNametxt = findViewById(R.id.avatar_whiteName_txt);
        neighbourWhiteNametxt.setText(aNeighbourName);

        neighbourBlackNametxt = findViewById(R.id.avatar_blackName_txt);
        neighbourBlackNametxt.setText(aNeighbourName);

        avatarImageView = findViewById(R.id.avatar_url_img);
        Glide.with(this)
                .asBitmap()
                .load(anImageAvatarUrl)
                .into(avatarImageView);

        neighbourPhone = findViewById(R.id.phone_txt);
        neighbourPhone.setText(aNeighbourPhone);
    }
}

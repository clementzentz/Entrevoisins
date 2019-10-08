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

        initView();
        getIncomingNeighbour();

        retourMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent();
                intent.putExtra(AllKeys.INTENT_DETAIL_RETOUR_FAVORIS, mCurrentNeighbour);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        favorisFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNeighbour.setFavoris(true);
            }
        });
    }

    private void getIncomingNeighbour(){
            if (getIntent().hasExtra(AllKeys.INTENT_ENVOIE_DETAIL_NEIGHBOUR)) {
                mCurrentNeighbour = (Neighbour) getIntent().getSerializableExtra(AllKeys.INTENT_ENVOIE_DETAIL_NEIGHBOUR);
                String imageAvatarUrl =  mCurrentNeighbour.getAvatarUrl();
                String neighbourName = mCurrentNeighbour.getName();
                String neighbourPhone = mCurrentNeighbour.getPhone();
                setActivityView(imageAvatarUrl, neighbourName, neighbourPhone);
            }
        }

    private void initView(){
        neighbourWhiteNametxt = findViewById(R.id.avatar_whiteName_txt);
        neighbourBlackNametxt = findViewById(R.id.avatar_blackName_txt);
        avatarImageView = findViewById(R.id.avatar_url_img);
        neighbourPhone = findViewById(R.id.phone_txt);
        favorisFAB = findViewById(R.id.favories_fab);
        retourMainActivity = findViewById(R.id.return_mainActivity_btn);
    }

    private void setActivityView(String anImageAvatarUrl, String aNeighbourName, String aNeighbourPhone){

        neighbourWhiteNametxt.setText(aNeighbourName);

        neighbourBlackNametxt.setText(aNeighbourName);

        Glide.with(this)
                .asBitmap()
                .load(anImageAvatarUrl)
                .override(300,300)
                .into(avatarImageView);

        neighbourPhone.setText(aNeighbourPhone);
    }
}

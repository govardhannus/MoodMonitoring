package com.example.govardhan.wearpracticeconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseSingleFragmentActivity extends BaseAbstractActivity {

    @Bind(R.id.img_home)
    ImageView imgHome;
    @Bind(R.id.toolbar_base_linear_layout)
    LinearLayout mLinearLayout;

    public LinearLayout getLinearLayout() {
        return mLinearLayout;
    }

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_single_fragment);
        ButterKnife.bind(this);
        // Add actions
        addActions();
        // Call the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            // Get the fragment that needs to be added.
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /****************************************************/
    // Actions
    /****************************************************/

    /**
     * Methods to add actions to the various view elements
     */
    private void addActions() {
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseSingleFragmentActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
            }
        });
    }
}

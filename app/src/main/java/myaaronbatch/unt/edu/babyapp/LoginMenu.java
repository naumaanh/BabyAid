package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.dropbox.core.android.Auth;


public class LoginMenu extends BaseActivity
{

    Button signInBtn;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        signInBtn = (Button) findViewById(R.id.signInBtn);

        continueBtn = (Button) findViewById(R.id.continueBtn);

        signInBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Auth.startOAuth2Authentication(getApplicationContext(), getString(R.string.APP_KEY));
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Proceed to ChooseChildMenu
                Intent intent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getAccessToken();
    }

    public void getAccessToken()
    {
        String accessToken = Auth.getOAuth2Token(); //generate Access Token

        if (accessToken != null)
        {
            //Store accessToken in SharedPreferences
            //SharedPreferences prefs = getSharedPreferences("com.example.valdio.dropboxintegration", Context.MODE_PRIVATE);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            prefs.edit().putString("access-token", accessToken).apply();

            //Proceed to ChooseChildMenu
            Intent intent = new Intent(getApplicationContext(), ChooseChildMenu.class);

            startActivity(intent);
        }
    }
}


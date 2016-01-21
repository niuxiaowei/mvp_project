package com.niu.myapp.myapp.view.activity;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.niu.myapp.myapp.R;

import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.fragment.BaseFragment;
import com.niu.myapp.myapp.view.fragment.MainFragment;
import com.niu.myapp.myapp.view.util.Functions;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void setFunctionsForFragment(int fragmentId) {
        FragmentManager fm = getSupportFragmentManager();
        MainFragment fragment = (MainFragment)fm.findFragmentById(fragmentId);
        super.setFunctionsForFragment(fragmentId);
        switch (fragmentId){
            case R.id.main_fragment:
                fragment.setFunctions(new Functions().addFunction(new Functions.FunctionWithParam<String>(MainFragment.INVOKE_TO_H5_TAG) {
                    @Override
                    public void function(String o) {
                        getApplicationComponent().getNavigator().toH5Activity(MainActivity.this, o);
                    }
                }));

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }









}

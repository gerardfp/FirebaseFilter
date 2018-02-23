package com.example.gerard.firebasefilter.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gerard.firebasefilter.R;
import com.example.gerard.firebasefilter.ThingsManager;
import com.example.gerard.firebasefilter.view.fragment.AllThingListFragment;
import com.example.gerard.firebasefilter.view.fragment.FavThingListFragment;
import com.example.gerard.firebasefilter.view.fragment.MyThingListFragment;
import com.example.gerard.firebasefilter.viewmodel.SearchViewModel;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filldb) {
            ThingsManager.getInstance().ponerCosas();
            return true;
        } else if (id == R.id.action_deletedb){
            ThingsManager.getInstance().eliminarCosas();
            return true;
        } else if (id == R.id.action_search) {
            handleMenuSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 1) {
                return new MyThingListFragment();
            } else if (position == 2) {
                return new FavThingListFragment();
            }
            return new AllThingListFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar();

        if(isSearchOpened){

            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

            View view = this.getCurrentFocus();

            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_search));

            isSearchOpened = false;
        } else {

            action.setDisplayShowCustomEnabled(true);

            action.setCustomView(R.layout.search);
            action.setDisplayShowTitleEnabled(false);

            edtSeach = action.getCustomView().findViewById(R.id.edtSearch);

            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        // Establecer el terminoDeBusqueda en el SearchViewModel
                        // El ThingListFragment, esta observando, y cuando cambia el valor
                        // del terminoDeBusqueda, actualiza el Adaptador del RecyclerView

                        //  terminoDeBusqueda ====> textView.getText().toString();

                        SearchViewModel searchViewModel = ViewModelProviders.of(MainActivity.this).get(SearchViewModel.class);
                        searchViewModel.getTerminoDeBusqueda().setValue(textView.getText().toString());

                        return true;
                    }
                    return false;
                }

            });

            edtSeach.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_delete));

            isSearchOpened = true;
        }
    }
}

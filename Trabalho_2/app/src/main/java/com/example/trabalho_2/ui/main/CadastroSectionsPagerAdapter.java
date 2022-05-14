package com.example.trabalho_2.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.trabalho_2.R;
import com.example.trabalho_2.fragments.CadastroJogadorFragment;
import com.example.trabalho_2.fragments.CadastroTimeFragment;

public class CadastroSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.cadastro_tab_text_1, R.string.cadastro_tab_text_2};
    private final Context mContext;

    public CadastroSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = CadastroJogadorFragment.newInstance();
                break;
            case 1:
                fragment = CadastroTimeFragment.newInstance();
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}

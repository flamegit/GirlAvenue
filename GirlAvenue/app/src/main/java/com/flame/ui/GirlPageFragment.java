package com.flame.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flame.datasource.RemoteLadyFetcher;
import com.flame.ui.adapter.LadyPagerAdapter;
import com.flame.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/10/7.
 */
public class GirlPageFragment extends BaseFragment {

    private static final int REQUEST_PERMISSION_CODE = 1;
    LadyPagerAdapter mAdapter;
    int mIndex;
    String mUrl;
    View mActionView;

    public GirlPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mUrl = bundle.getString(Constants.URL);
        mIndex = bundle.getInt("index", 0);
       // getActivity().getWindow().setFlags(Window.);
    }

    public static GirlPageFragment Instance(String url, int index) {
        GirlPageFragment fragment = new GirlPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onResume() {
        super.onResume();
    }

    private String createFileName() {
        String des = getActivity().getTitle().toString();
        int index = mIndex + 1;
        return des + index + ".png";
    }

    private void saveLadyImage() {
        new AsyncTask<Void, Void, Void>() {
            Bitmap bitmap;
            @Override
            protected void onPreExecute() {
                ImageView view = mAdapter.getPrimaryItem();
                bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
            }

            @Override
            protected Void doInBackground(Void... params) {
                boolean isSaved = true;
                File path = Environment.getExternalStoragePublicDirectory("GirlAvenue");
                if (!path.exists()) {
                    path.mkdir();
                }
                String fileName = createFileName();
                File file = new File(path, fileName);
                OutputStream outputStream = null;
                try {
                    if (file.exists()) {
                        Snackbar.make(getView(), "Image already saved", Snackbar.LENGTH_SHORT).show();
                        return null;
                    }
                    outputStream = new FileOutputStream(file);
                    if (bitmap != null) {
                        isSaved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    }
                    if (isSaved) {
                        Snackbar.make(getView(), fileName + "have saved in " + path, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(getView(), "Image saved failed", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }

    private void share(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        getContext().startActivity(Intent.createChooser(intent, "Share"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_save) {
            saveLadyImage();
        }
        if (item.getItemId() == R.id.action_share) {
            share(Uri.parse(mUrl));
        }
        return true;
    }


    private void saveLadyImageWrap() {
        if (requestPermission()) {
            saveLadyImage();
        }
    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    saveLadyImage();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "CALL_PHONE Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void fillView(String item) {
        mAdapter.addItem(item);
    }

    @Override
    int getLayout() {
        return R.layout.girl_pager;
    }

    @Override
    void initView(View view) {
       // ((LadyViewActivity)getActivity()).showToolbar(false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final TextView textView = (TextView) view.findViewById(R.id.index_view);
        mActionView = view.findViewById(R.id.bottom_action_view);
        View saveView = view.findViewById(R.id.save_view);
        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLadyImageWrap();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int p = position + 1;
                textView.setText(p + "/" + mAdapter.getCount());
                mIndex = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mAdapter = new LadyPagerAdapter(mUrl);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int num = mAdapter.getCount();
                textView.setText("1/" + num);

            }
        });

        mAdapter.setTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mActionView.getAlpha() > 0) {
                    mActionView.animate().alpha(0);
                } else {
                    mActionView.animate().alpha(1);
                }
            }
        });
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mIndex);
        viewPager.getFocusedChild();
    }
}

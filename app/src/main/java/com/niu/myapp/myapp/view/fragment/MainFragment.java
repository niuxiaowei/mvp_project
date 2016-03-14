package com.niu.myapp.myapp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.image.ImageLoaderProxy;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.DaggerMainComponent;
import com.niu.myapp.myapp.internal.di.components.MainComponent;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.LoginModule;
import com.niu.myapp.myapp.internal.di.modules.MainModule;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.compnent.ILoginView;
import com.niu.myapp.myapp.view.compnent.IMainView;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.data.User;
import com.niu.myapp.myapp.view.util.FunctionException;
import com.niu.myapp.myapp.view.widget.ConfirmDialogFragment;

import java.util.List;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class MainFragment extends BaseFragment implements IMainView,DialogInterface.OnClickListener {

    private MainComponent mMainComponent;



    private ImageView mImageView;


    public static final String  INVOKE_TO_H5_TAG = "to_h5_tag";

    @Override
    protected void onAddPresenters() {
        addPresenter(mMainComponent.getLoginPresenter());
        addPresenter(mMainComponent.getMainPresenter());
        addPresenter(mMainComponent.getFriendListPresenter());
    }

    @Override
    protected void onInitPresenters() {
        mMainComponent.getLoginPresenter().initView(new LoginView());
        mMainComponent.getMainPresenter().initView(this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ToastUtil.showLong(mBaseActivity, "id="+which);
    }


    private class LoginView implements ILoginView {
        private TextView mLoadDataStateView;

        @Override
        public void showLoginingView() {
            mDialogFactory.showProgressDialog("数据正在加载中.....", false);
        }

        @Override
        public void loginSuccess() {

            mLoadDataStateView.setText("登录成功");
            mDialogFactory.showConfirmDialog("my app", "恭喜登录成", true, new ConfirmDialogFragment.ConfirmDialogListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            mDialogFactory.dissProgressDialog();
        }

        @Override
        public void loginFailed(String failedReason) {
            mLoadDataStateView.setText("登录失败");
            mDialogFactory.dissProgressDialog();
        }

        @Override
        public void bindDataForView(User user) {

        }

        @Override
        public void initView() {
            mLoadDataStateView = (TextView)getView().findViewById(R.id.load_state);
            final EditText userNameView = (EditText)getView().findViewById(R.id.username);
            final EditText passView = (EditText)getView().findViewById(R.id.password);
            getView().findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = userNameView.getText().toString();
                    String pass = passView.getText().toString();
                    mMainComponent.getLoginPresenter().login(username, pass);
//                ToastUtil.showLong(MainActivity.this,"00000000000000000000");
                }
            });


        }
    }

    private class FriendListView implements IFriendListView {

        ListView mFriendsView ;
        @Override
        public void bindDataForView(List<Friend> friends) {

        }

        @Override
        public void initView() {

        }

        @Override
        public void showGitHubUser(String userName) {

        }
    }

    @Override
    public void bindDataForView(Object o) {

    }

    @Override
    public void initView() {
        mImageView = (ImageView)getView().findViewById(R.id.image);
        ImageLoaderProxy.getInstance().displayBannerImg("http://www.sinaimg.cn/qc/photo_auto/photo/50/11/1485011/1485011_src.jpg",mImageView);

        getView().findViewById(R.id.to_h5_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFunctions != null) {
                    try {
                        mFunctions.invokeFunc(INVOKE_TO_H5_TAG, "file:///android_asset/h5_native.html");
                    } catch (FunctionException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        getView().findViewById(R.id.to_rxbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mFunctions.invokeFunc("rxbus");
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        });

        getView().findViewById(R.id.to_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFunctions != null) {
                    try {
                        mFunctions.invokeFunc("toFriends");
                    } catch (FunctionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    protected void onInjectFragment() {
        this.mMainComponent = DaggerMainComponent.builder()
                .applicationComponent(mBaseActivity.getApplicationComponent())
                .mainModule(new MainModule())
                .friendsModule(new FriendsModule())
                .loginModule(new LoginModule()).build();
        this.mMainComponent.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainComponent = null;
    }



}

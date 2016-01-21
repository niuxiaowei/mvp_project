package com.niu.myapp.myapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.http.image.ImageLoaderProxy;
import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.DaggerMainComponent;
import com.niu.myapp.myapp.internal.di.components.MainComponent;
import com.niu.myapp.myapp.internal.di.modules.FriendsModule;
import com.niu.myapp.myapp.internal.di.modules.LoginModule;
import com.niu.myapp.myapp.internal.di.modules.MainModule;
import com.niu.myapp.myapp.presenter.FriendListPresenter;
import com.niu.myapp.myapp.presenter.LoginPresenter;
import com.niu.myapp.myapp.presenter.MainPresenter;
import com.niu.myapp.myapp.view.annotation.IsIView;
import com.niu.myapp.myapp.view.annotation.IsPresneter;
import com.niu.myapp.myapp.view.compnent.IFriendListView;
import com.niu.myapp.myapp.view.compnent.ILoginView;
import com.niu.myapp.myapp.view.compnent.IMainView;
import com.niu.myapp.myapp.view.data.Friend;
import com.niu.myapp.myapp.view.data.Friends;
import com.niu.myapp.myapp.view.data.User;
import com.niu.myapp.myapp.view.util.Functions;
import com.niu.myapp.myapp.view.widget.ConfirmDialogFragment;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
public class MainFragment extends BaseFragment implements IMainView {

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

    }



    private class LoginView implements ILoginView {
        private TextView mLoadDataStateView;

        @Override
        public void showLoginingView() {
            showProgressDialog("数据正在加载中.....", false);
        }

        @Override
        public void loginSuccess() {

            mLoadDataStateView.setText("登录成功");
            showConfirmDialog("my app", "恭喜登录成", 2, true, new ConfirmDialogFragment.ConfirmDialogClickListener() {
                @Override
                public void onOKClick() {
                    ToastUtil.showLong(mBaseActivity, "id=" );
                }

                @Override
                public void onCancleClick() {

                }
            });
            dissProgressDialog();
        }

        @Override
        public void loginFailed(String failedReason) {
            mLoadDataStateView.setText("登录失败");
            dissProgressDialog();
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
        public void bindDataForView(Friends friends) {

        }

        @Override
        public void initView() {

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
                if(mFunctions != null){
                    mFunctions.invokeFunc(INVOKE_TO_H5_TAG, "file:///android_asset/h5_native.html");
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
    protected void onInitializeInjector() {
        this.mMainComponent = DaggerMainComponent.builder()
                .applicationComponent(mBaseActivity.getApplicationComponent())
                .mainModule(new MainModule(this))
                .friendsModule(new FriendsModule(new FriendListView()))
                .loginModule(new LoginModule(new LoginView())).build();
    }

    @Override
    protected void onInjectFragment() {
        this.mMainComponent.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainComponent = null;
    }



}

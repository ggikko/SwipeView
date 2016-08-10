package ggikko.me.swipviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.daimajia.swipe.SwipeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ggikko.me.swipviewapp.dto.ImageInfo;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sample)
    SwipeLayout swipeLayout;

    private Realm realm;

    @OnClick({R.id.sample, R.id.btn_count_check})
    void callOpenClick(View view) {

        switch (view.getId()) {

            case R.id.sample:
                if (swipeLayout.getOpenStatus() == SwipeLayout.Status.Open) {
                    swipeLayout.close(true);
                } else {
                    swipeLayout.open(true);
                }
                break;

            case R.id.btn_count_check:
                Log.e("ggikko", "size : " + realm.where(ImageInfo.class).findAll().size());
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, findViewById(R.id.bottom_wrapper));
//        swipeLayout.setSwipeEnabled(false);

        RealmResults<ImageInfo> infos = realm.where(ImageInfo.class).findAll();

        ((CheckBox)swipeLayout.findViewById(R.id.check_box)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setName("hello");
                    imageInfo.setUrl("www.hello.com");
                    realm.executeTransactionAsync(realm ->realm.copyToRealmOrUpdate(imageInfo));
                }else{
                    realm.executeTransactionAsync(realm ->{
                        realm.where(ImageInfo.class).equalTo("name", "hello").findAll().deleteAllFromRealm();
                    });
                }
            }
        });

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

    }
}

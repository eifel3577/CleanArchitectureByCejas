package com.example.cleanarchitecturebycejas.Presentation.View.Activity;

import android.os.Bundle;
import android.widget.Button;

import com.example.cleanarchitecturebycejas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**точка входа в приложение */
public class MainActivity extends BaseActivity {

    /**кнопка загрузить данные */
    @BindView(R.id.btn_LoadData)
    Button btn_LoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**инициализация Butterknife */
        ButterKnife.bind(this);
    }

    /**
     * при нажатии на кнопку показывается список пользователей
     *
     */
    @OnClick(R.id.btn_LoadData)
    /**в методе идет обращение к обьекту navigator,он получен в классе родителе через Dagger */
    void navigateToUserList() {
        this.navigator.navigateToUserList(this);
    }
}

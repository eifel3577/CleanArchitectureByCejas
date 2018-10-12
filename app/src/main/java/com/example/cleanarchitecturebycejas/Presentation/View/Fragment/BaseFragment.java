package com.example.cleanarchitecturebycejas.Presentation.View.Fragment;

import android.app.Fragment;
import android.widget.Toast;

import com.example.cleanarchitecturebycejas.Presentation.DI.HasComponent;

/**
 * Основной класс родитель для всех фрагментов в приложении
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Показывает {@link android.widget.Toast} сообщение
     *
     * @param message строка,которая будет показана в сообщении
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Дает компонент для DI по данному типу
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
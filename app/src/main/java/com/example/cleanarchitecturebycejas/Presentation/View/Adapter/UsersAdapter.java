package com.example.cleanarchitecturebycejas.Presentation.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;
import com.example.cleanarchitecturebycejas.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * адаптер для управления списком{@link UserModel}.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    /**интерфейс для обработки нажатия на юзере.совместный интерфейс с UserFragment */
    public interface OnItemClickListener {
        void onUserItemClicked(UserModel userModel);
    }

    /**коллекция с которой будет работать адаптер */
    private List<UserModel> usersCollection;
    private final LayoutInflater layoutInflater;

    /**обьект интерфейса */
    private OnItemClickListener onItemClickListener;

    /**адаптер будет передаваться в качестве зависимости в другие классы.*/
    @Inject
    UsersAdapter(Context context) {
        /**инициализация LayoutInflater и дефолтная инициализация коллекции пустом списком */
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usersCollection = Collections.emptyList();
    }

    /**возвращает размер коллекции */
    @Override public int getItemCount() {
        return (this.usersCollection != null) ? this.usersCollection.size() : 0;
    }

    /**создает возвращает вьюхолдер,работающий с итемом */
    @Override public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    /**получает холдер и позицию,заполняет итем */
    @Override public void onBindViewHolder(UserViewHolder holder, final int position) {
        final UserModel userModel = this.usersCollection.get(position);
        holder.textViewTitle.setText(userModel.getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (UsersAdapter.this.onItemClickListener != null) {
                    UsersAdapter.this.onItemClickListener.onUserItemClicked(userModel);
                }
            }
        });
    }

    /**возвращает позицию */
    @Override public long getItemId(int position) {
        return position;
    }

    /**сначала проверяет не нулевая ли коллекция,затем инициализирует полученной коллекцией usersCollection
     * и обновляет экран*/
    public void setUsersCollection(Collection<UserModel> usersCollection) {
        this.validateUsersCollection(usersCollection);
        this.usersCollection = (List<UserModel>) usersCollection;
        this.notifyDataSetChanged();
    }

    /**получает лисенер */
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**проверяет не нулевая ли коллекция,если да выбрасывает исключение */
    private void validateUsersCollection(Collection<UserModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    /**класс вьюхолдера */
    static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView textViewTitle;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
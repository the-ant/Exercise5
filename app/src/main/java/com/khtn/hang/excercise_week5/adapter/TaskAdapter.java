package com.khtn.hang.excercise_week5.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khtn.hang.excercise_week5.R;
import com.khtn.hang.excercise_week5.pojo.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private List<Task> listTasks;
    private MovieItemClickListener itemClickLister;
    private MovieItemLongClickListener itemLongClickListener;

    public interface MovieItemClickListener {
        void onItemClick(Task result);
    }

    public interface MovieItemLongClickListener {
        void onItemLongClick(Task result);
    }

    public TaskAdapter(Context context, List<Task> listMovies, MovieItemClickListener listener,MovieItemLongClickListener itemLongClickListener ) {
        this.context = context;
        this.listTasks = listMovies;
        this.itemClickLister = listener;
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task todo = listTasks.get(position);
        holder.name.setText(todo.getName());
        holder.dueToDate.setText(todo.getDueToDate().toString());
        holder.priority.setText(todo.getPriorityString());
        holder.itemView.setOnClickListener(v -> itemClickLister.onItemClick(todo));
        holder.itemView.setOnLongClickListener(view -> {
            itemLongClickListener.onItemLongClick(todo);
            return true;
        });
    }

    public void clear() {
        listTasks.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Task> listTasks) {
        this.listTasks.addAll(listTasks);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.dueToDate)
        TextView dueToDate;
        @BindView(R.id.priority)
        TextView priority;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

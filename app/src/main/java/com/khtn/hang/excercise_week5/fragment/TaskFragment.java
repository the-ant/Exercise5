package com.khtn.hang.excercise_week5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khtn.hang.excercise_week5.Activity.MainActivity;
import com.khtn.hang.excercise_week5.Constants;
import com.khtn.hang.excercise_week5.R;
import com.khtn.hang.excercise_week5.adapter.TaskAdapter;
import com.khtn.hang.excercise_week5.pojo.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class TaskFragment extends Fragment implements TaskAdapter.MovieItemClickListener, TaskAdapter.MovieItemLongClickListener {
    public static final String ARG_PAGE = "ARG_PAGE_TITLE";
    private static final String TAG = "fragment";

    private String title;
    private TaskAdapter adapter;
    private Realm realm;

    @BindView(R.id.rv_movie)
    RecyclerView rvTask;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    public static TaskFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PAGE, title);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = this.getArguments().getString(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        init();
        loadData();
        return view;
    }

    private void init() {
        rvTask.setHasFixedSize(true);
        rvTask.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvTask.setItemAnimator(new DefaultItemAnimator());
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        realm = Realm.getDefaultInstance();
    }

    private void loadData() {
        final RealmResults<Task> tasks = realm.where(Task.class)
                .equalTo("isDone", isDoneTab())
                .findAll();
        getActivity().runOnUiThread(() -> {
            adapter = new TaskAdapter(TaskFragment.this.getContext(), realm.copyFromRealm(tasks), TaskFragment.this, this);
            rvTask.setAdapter(adapter);
        });
    }

    @Override
    public void onItemClick(Task result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (title.equals(Constants.TAG_TODO_LIST)) {
            builder.setTitle("What's your choice?")
                    .setPositiveButton(R.string.done, (dialog, id) -> {
                        setDoneTask(result);
                        dialog.cancel();
                    })
                    .setNegativeButton(R.string.edit, (dialog, id) -> {
                        showAddTaskFragment(result);
                        dialog.cancel();
                    })
                    .setNeutralButton(R.string.cancel, (dialog, id) -> {
                        dialog.cancel();
                    });
        }
        builder.show();
    }

    @Override
    public void onItemLongClick(Task result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("What's your choice?")
                .setPositiveButton(R.string.delete, (dialog, id) -> {
                    deleteTask(result);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    dialog.cancel();
                });
        builder.show();
    }

    private void deleteTask(Task result) {
        realm.executeTransaction(inRealm -> {
            final Task task = inRealm.where(Task.class)
                    .equalTo("id", result.getId())
                    .findFirst();
            if (task != null) {
                task.deleteFromRealm();
                ((MainActivity) getActivity()).reload(isDoneTab());
            }
        });
    }

    private void setDoneTask(Task doneTask) {
        realm.executeTransaction(inRealm -> {
            final Task task = inRealm.where(Task.class)
                    .equalTo("id", doneTask.getId())
                    .findFirst();
            if (task != null) {
                task.setDone(true);
                ((MainActivity) getActivity()).reload(isDoneTab());
            }
        });
    }

    private void showAddTaskFragment(Task task) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddDialogFragment addDialogFragment = new AddDialogFragment(task);
        addDialogFragment.show(fm, "fragment_edit");
    }

    private boolean isDoneTab(){
        return title.equals(Constants.TAG_TODO_LIST)?false:true;
    }
}

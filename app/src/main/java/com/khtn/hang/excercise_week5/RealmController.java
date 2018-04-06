package com.khtn.hang.excercise_week5;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.khtn.hang.excercise_week5.pojo.Task;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Task> getBooks() {

        return realm.where(Task.class).findAll();
    }

    //query a single item with the given id
    public Task getBook(String id) {

        return realm.where(Task.class).equalTo("id", id).findFirst();
    }

    //query example
    public RealmResults<Task> queryedBooks() {

        return realm.where(Task.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();
    }
}
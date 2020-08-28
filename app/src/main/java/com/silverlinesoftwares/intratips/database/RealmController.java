package com.silverlinesoftwares.intratips.database;

import android.app.Activity;
import android.app.Application;
import androidx.fragment.app.Fragment;

import com.silverlinesoftwares.intratips.models.SearchModel;

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
//    public void clearAll() {
//
//        realm.beginTransaction();
//        realm.clear(SearchModel.class);
//        realm.commitTransaction();
//    }

    //find all objects in the Book.class
    public RealmResults<SearchModel> getBooks() {

        return realm.where(SearchModel.class).findAll();
    }

    //query a single item with the given id
    public SearchModel getBook(String id) {

        return realm.where(SearchModel.class).equalTo("symbol", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {

        return false;
//        return !realm.allObjects(SearchModel.class).isEmpty();
    }

    //query example
    public RealmResults<SearchModel> queryedBooks() {

        return realm.where(SearchModel.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}

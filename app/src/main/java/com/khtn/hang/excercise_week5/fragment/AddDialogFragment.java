package com.khtn.hang.excercise_week5.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.khtn.hang.excercise_week5.Activity.MainActivity;
import com.khtn.hang.excercise_week5.Constants;
import com.khtn.hang.excercise_week5.R;
import com.khtn.hang.excercise_week5.pojo.Task;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;

@SuppressLint("ValidFragment")
public class AddDialogFragment extends DialogFragment implements View.OnClickListener {
    private EditText editName;
    private DatePicker datePicker;
    private RadioButton rdLow, rdNormal, rdHigh;
    private Task task;


    public AddDialogFragment() {
    }

    public AddDialogFragment(Task task) {
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_task_add, null);
        builder.setView(view);

        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        editName = view.findViewById(R.id.input_name);
        datePicker = view.findViewById(R.id.datePicker);
        rdLow = view.findViewById(R.id.rdLow);
        rdNormal = view.findViewById(R.id.rdNormal);
        rdHigh = view.findViewById(R.id.rdHigh);

        if (task != null) {
            editName.setText(task.getName());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getDueToDate());
            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            if (task.getPriority() == Constants.PRIORITY_LOW_ID) {
                rdLow.setChecked(true);
            } else if (task.getPriority() == Constants.PRIORITY_NORMAL_ID) {
                rdNormal.setChecked(true);
            } else {
                rdHigh.setChecked(true);
            }
        }

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                addTask();
                break;
            case R.id.btnCancel:
                this.getDialog().cancel();
                break;
        }
    }

    private void addTask() {
        Realm realm = Realm.getDefaultInstance();
        if (!editName.getText().toString().trim().equals("")) {
            realm.executeTransaction(realm1 -> {
                Task task = realm1.createObject(Task.class, UUID.randomUUID().toString());
                task.setName(editName.getText().toString().trim());
                task.setDueToDate(getDateFromDatePicker(datePicker));
                task.setDone(false);

                if (rdLow.isChecked()) {
                    task.setPriority(Constants.PRIORITY_LOW_ID);
                }
                if (rdNormal.isChecked()) {
                    task.setPriority(Constants.PRIORITY_NORMAL_ID);
                }
                if (rdHigh.isChecked()) {
                    task.setPriority(Constants.PRIORITY_HIGH_ID);
                }
            });
            this.getDialog().cancel();
            ((MainActivity) getActivity()).reload(false);
        }

    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
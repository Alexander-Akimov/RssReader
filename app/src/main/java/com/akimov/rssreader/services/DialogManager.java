package com.akimov.rssreader.services;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akimov.rssreader.R;
import com.akimov.rssreader.model.Channel;

public class DialogManager {

    private AlertDialog.Builder builder;
    private View dialogView;

    public DialogManager(Activity activity) {
        builder = new AlertDialog.Builder(activity);
        dialogView = activity.getLayoutInflater().inflate(R.layout.add_channel_dialog, null);
    }

    public void ShowAddDialog(CommonCallback callback) {

        EditText channelTitle = dialogView.findViewById(R.id.channelTitleTxt);
        EditText channelDesc = dialogView.findViewById(R.id.channelDescTxt);
        EditText channelUrl = dialogView.findViewById(R.id.channelUrl);
        AlertDialog dialog = builder.setView(dialogView)
                .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена", null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            String title = channelTitle.getText().toString();
            String description = channelDesc.getText().toString();
            String url = channelUrl.getText().toString();
            if (title.isEmpty() && description.isEmpty() && url.isEmpty()) {
                Toast.makeText(builder.getContext(), "Пожалуйста заполните пустые поля", Toast.LENGTH_SHORT).show();
            } else {
                callback.doAction();
                dialog.dismiss();
            }
        });
    }


}

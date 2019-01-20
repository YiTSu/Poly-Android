package com.yitsu.poly.view.splashUI.createNoteUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/11/12.
 * 用于发表帖子的第二个Fragment
 */
public class CreateNoteTwoFragment extends Fragment {

    private View rootView;
    public static EditText editContent;
    public static ImageView imageSquare;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_note_three, container, false);
        editContent = (EditText)rootView.findViewById(R.id.edit_content);
        imageSquare = (ImageView)rootView.findViewById(R.id.image_square);
        return rootView;
    }
}
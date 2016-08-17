package com.example.user.simpleui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkOrderDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    NumberPicker mNumberPicker;
    NumberPicker lNumberPicker;
    RadioGroup iceRadioGroup;
    RadioGroup sugarRadioGroup;
    EditText noteEditText;


    private DrinkOrder drinkOrder;

    private OnFragmentInteractionListener mListener;

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(DrinkOrder drinkOrder) {
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();

        args.putParcelable(ARG_PARAM1, drinkOrder);

        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(getArguments() != null)
        {
            this.drinkOrder = getArguments().getParcelable(ARG_PARAM1);
        }

        View contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_drink_order_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(contentView)
                .setTitle(drinkOrder.getDrink().getName())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drinkOrder.setmNumber(mNumberPicker.getValue());
                        drinkOrder.setlNumber(lNumberPicker.getValue());
                        drinkOrder.setIce(getSelectedTextFromRadioGroup(iceRadioGroup));
                        drinkOrder.setSugar(getSelectedTextFromRadioGroup(sugarRadioGroup));
                        drinkOrder.setNote(noteEditText.getText().toString());

                        if(mListener != null)
                        {
                            mListener.onDrinkOrderResult(drinkOrder);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mNumberPicker = (NumberPicker)contentView.findViewById(R.id.mNumberPicker);
        lNumberPicker = (NumberPicker)contentView.findViewById(R.id.lNumberPicker);
        iceRadioGroup = (RadioGroup)contentView.findViewById(R.id.iceRadioGroup);
        sugarRadioGroup = (RadioGroup)contentView.findViewById(R.id.sugarRadioGroup);
        noteEditText = (EditText)contentView.findViewById(R.id.noteEditText);


        mNumberPicker.setMaxValue(100);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setValue(drinkOrder.getmNumber());

        lNumberPicker.setMaxValue(100);
        lNumberPicker.setMinValue(0);
        lNumberPicker.setValue(drinkOrder.getlNumber());

        noteEditText.setText(drinkOrder.getNote());
        setSelectedTextInRadioGroup(drinkOrder.getIce(), iceRadioGroup);
        setSelectedTextInRadioGroup(drinkOrder.getSugar(), sugarRadioGroup);

        return builder.create();
    }

    private String getSelectedTextFromRadioGroup(RadioGroup radioGroup)
    {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(id);
        return radioButton.getText().toString();
    }

    private void setSelectedTextInRadioGroup(String selectedText, RadioGroup radioGroup)
    {
        int count = radioGroup.getChildCount();
        for(int i=0; i <count;i++)
        {
            View view = radioGroup.getChildAt(i);
            if(view instanceof RadioButton)
            {
                RadioButton radioButton = (RadioButton)view;
                if(radioButton.getText().toString().equals(selectedText))
                {
                    radioButton.setChecked(true);
                }
                else
                {
                    radioButton.setChecked(false);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDrinkOrderResult(DrinkOrder drinkOrder);
    }
}

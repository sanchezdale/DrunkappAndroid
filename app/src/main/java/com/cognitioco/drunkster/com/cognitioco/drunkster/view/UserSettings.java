package com.cognitioco.drunkster.com.cognitioco.drunkster.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cognitioco.drunkster.R;
import com.cognitioco.drunkster.com.cognitioco.drunkster.com.cognitioco.drunkster.controller.UserController;
import com.cognitioco.drunkster.com.cognitioco.drunkster.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSettings.OnUserFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnUserFragmentInteractionListener mListener;
    private SeekBar.OnSeekBarChangeListener seekbarWeightListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            TextView weightDisplay = (TextView) getView().findViewById(R.id.lbl_weightDisplay);
            weightDisplay.setText(String.valueOf(progress + 1));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener seekbarAgeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            TextView ageDisplay = (TextView) getView().findViewById(R.id.lbl_ageDisplay);
            ageDisplay.setText(String.valueOf(progress + 1));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (saveUserSettings()) {
                getFragmentManager().popBackStack();
            } else {
                //Toast
                //Context context = getApplicationContext();
                //CharSequence text = "Hello toast!";
                //int duration = Toast.LENGTH_SHORT;

                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();

                Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private View.OnClickListener listenerContact = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO: Correct fragment_contacts_display loading. Crashes on each attempt.
            /*ContactsDisplaySettings CDS = new ContactsDisplaySettings() ;
            getFragmentManager().beginTransaction().add(R.id.fragmentContactsDisplay, CDS).addToBackStack(null).commit();*/

            /*ContactsDisplaySettings CDS= new ContactsDisplaySettings();
            getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContactsDisplay, CDS, "TAG_FRAGMENT")
                    .addToBackStack(null)
                    .commit();*/
        }
    };

    public UserSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSettings newInstance(String param1, String param2) {
        UserSettings fragment = new UserSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_settings, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.btn_save);
        fab.setOnClickListener(listener);
        Button btnContact = (Button) v.findViewById(R.id.btn_contactsOpen);
        btnContact.setOnClickListener(listenerContact);

        UserController userc = new UserController();

        User user = userc.retirveAll().get(0);

        TextView weightDisplay = (TextView) v.findViewById(R.id.lbl_weightDisplay);
        TextView ageDispla = (TextView) v.findViewById(R.id.lbl_ageDisplay);

        weightDisplay.setText(String.valueOf((int) user.getWeight()));
        ageDispla.setText(String.valueOf(user.getAge()));

        EditText tf = (EditText) v.findViewById(R.id.tf_username);
        SeekBar weight = (SeekBar) v.findViewById(R.id.bar_weight);
        SeekBar age = (SeekBar) v.findViewById(R.id.bar_age);


        RadioButton maleButton = (RadioButton) v.findViewById(R.id.radio_male);
        RadioButton femaleButton = (RadioButton) v.findViewById(R.id.radio_female);

        if (user.getSexDB() == 1) {
            femaleButton.setSelected(true);
        } else {
            maleButton.setSelected(true);
        }

        tf.setText(user.getName());
        weight.setProgress((int) user.getWeight());
        age.setProgress(user.getAge());
        weight.setOnSeekBarChangeListener(seekbarWeightListener);
        age.setOnSeekBarChangeListener(seekbarAgeListener);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onUserFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserFragmentInteractionListener) {
            mListener = (OnUserFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean saveUserSettings()
    {

        UserController usercontroller = new UserController();
        User newSettings = usercontroller.retirveAll().get(0);

        TextView newName = (TextView) getView().findViewById(R.id.tf_username);
        SeekBar newAge = (SeekBar) getView().findViewById((R.id.bar_age));
        RadioButton newSex = (RadioButton) getView().findViewById(R.id.radio_male);
        SeekBar newWeight = (SeekBar) getView().findViewById(R.id.bar_weight);


        //Ask user to fill name
        String checkName = newName.getText().toString();
        String empty = "";
        if (!checkName.isEmpty())
        {
            newSettings.setName(newName.getText().toString());
        }
        else
        {
            return false;
        }

        newSettings.setAge(newAge.getProgress());
        //newSettings.setEmergencyNumber();
        //newSettings.setPrefferedTaxiService();
        newSettings.setWeight(newWeight.getProgress());

        if (newSex.isChecked())
        {
            newSettings.setSex(User.Sex.MALE);
        }
        else
        {
            newSettings.setSex(User.Sex.FEMALE);
        }

        usercontroller.updateUser(newSettings);

        return true;
    }

    public void onProgressChanged(SeekBar seekBar,
                                  int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub
        TextView weightDisplay = (TextView)getView().findViewById(R.id.lbl_weight);
        weightDisplay.setText(String.valueOf(progress));
        TextView ageDisplay = (TextView)getView().findViewById(R.id.lbl_ageDisplay);
        ageDisplay.setText(String.valueOf(progress));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUserFragmentInteractionListener {
        // TODO: Update argument type and name
        void onUserFragmentInteraction(Uri uri);
    }
}

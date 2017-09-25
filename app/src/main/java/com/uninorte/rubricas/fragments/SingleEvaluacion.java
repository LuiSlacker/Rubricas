package com.uninorte.rubricas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uninorte.rubricas.helper.CustomEvaluacionesAdapter;
import com.uninorte.rubricas.helper.EvaluacionDataModel;
import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.calificacion.evaluacion.CalificacionEvaluacion;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleEvaluacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleEvaluacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleEvaluacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<EvaluacionDataModel> dataModels;
    private static CustomEvaluacionesAdapter adapter;
    private int evaluacionId;
    private String evaluacionNombre;
    private List<CalificacionEvaluacion> calificacionEvaluacionEntities = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public SingleEvaluacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleEvaluacion.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleEvaluacion newInstance(String param1, String param2) {
        SingleEvaluacion fragment = new SingleEvaluacion();
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
        evaluacionId = getArguments().getInt("evaluacionId");
        evaluacionNombre = getArguments().getString("evaluacionNombre");

        getActivity().setTitle(evaluacionNombre);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_evaluacion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        calificacionEvaluacionEntities = AppDatabase
                .getAppDatabase(getActivity())
                .calificacionEvaluacionDao()
                .getAllForOneEvaluacion(evaluacionId);

        dataModels = new ArrayList<>();

        for (CalificacionEvaluacion calificacionEvaluacion : calificacionEvaluacionEntities) {
            dataModels.add(new EvaluacionDataModel(calificacionEvaluacion.getEstudianteNombre(), calificacionEvaluacion.getNota()));
        }

        ListView listview = (ListView) getActivity().findViewById(R.id.evaluacion_list_view);

        adapter = new CustomEvaluacionesAdapter(dataModels, getActivity());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = EvaluacionUI.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putInt("evaluacionId", evaluacionId);
                bundle.putInt("calificacionEvaluacionId", (int) calificacionEvaluacionEntities.get(i).getUid());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("singleEvaluacion").commit();
                getActivity().setTitle(evaluacionNombre + " - " + dataModels.get(i).getEstudiante());
            }
        });
    }


        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

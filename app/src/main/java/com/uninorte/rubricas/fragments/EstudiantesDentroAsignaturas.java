package com.uninorte.rubricas.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstudiantesDentroAsignaturas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstudiantesDentroAsignaturas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudiantesDentroAsignaturas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> estudiantes;
    private ArrayAdapter<String> estudiantesAdapter;
    private List<Estudiante> estudianteEntities;
    private long asignaturaId;

    private OnFragmentInteractionListener mListener;

    public EstudiantesDentroAsignaturas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudiantesDentroAsignaturas.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudiantesDentroAsignaturas newInstance(String param1, String param2) {
        EstudiantesDentroAsignaturas fragment = new EstudiantesDentroAsignaturas();
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

        asignaturaId = getArguments().getInt("asignaturaId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estudiantes_dentro_asignaturas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        estudianteEntities = AppDatabase.getAppDatabase(getActivity()).estudianteDao().getAllForOneAsignatura((int)asignaturaId);
        estudiantes = new ArrayList<String>();
        estudiantes.addAll(mapEstudiantesToNames(estudianteEntities));
        estudiantesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, estudiantes);
        ListView listview = (ListView) getActivity().findViewById(R.id.estudiantesDentroAsgnaturasListView);
        listview.setAdapter(estudiantesAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = EstudiantesReport.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                estudianteEntities = AppDatabase.getAppDatabase(getActivity()).estudianteDao().getAllForOneAsignatura((int)asignaturaId); // refetch all asigntauras for Ids
                long estudianteId = estudianteEntities.get(i).getUid();
                Bundle bundle = new Bundle();
                bundle.putInt("estudianteId", (int) estudianteId);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("estudiantes").commit();
                getActivity().setTitle(estudiantes.get(i)+"");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.estudiantes_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nombreEditText = new EditText(getActivity());
                nombreEditText.setHint("Nuevo Estudiante");

                new AlertDialog.Builder(getActivity())
                        .setTitle("Crear Estudiante")
                        .setMessage("Ingrese un nombre!")
                        .setView(nombreEditText)
                        .setCancelable(false)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String nombre = nombreEditText.getText().toString();
                                estudiantes.add(nombre);
                                estudiantesAdapter.notifyDataSetChanged();
                                Estudiante newEstudiante = new Estudiante();
                                newEstudiante.setNombre(nombre);
                                newEstudiante.setAsignaturaId((int) asignaturaId);
                                AppDatabase.getAppDatabase(getActivity()).estudianteDao().insertAll(newEstudiante);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });
    }

    private List<String> mapEstudiantesToNames(List<Estudiante> estudianteObjects) {
        List<String> list = new ArrayList<String>();
        for (Estudiante estudiante: estudianteObjects) {
            list.add(estudiante.getNombre());
        }
        return list;
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

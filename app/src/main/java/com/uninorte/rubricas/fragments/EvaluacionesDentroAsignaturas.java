package com.uninorte.rubricas.fragments;

import android.app.Dialog;
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
import android.widget.Spinner;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.estudiante.Estudiante;
import com.uninorte.rubricas.db.evaluacion.Evaluacion;
import com.uninorte.rubricas.db.rubrica.Rubrica;

import java.util.ArrayList;
import java.util.List;

import static com.uninorte.rubricas.R.layout.estudiantes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvaluacionesDentroAsignaturas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvaluacionesDentroAsignaturas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluacionesDentroAsignaturas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> evaluaciones;
    private ArrayAdapter<String> evaluacionesAdapter;
    private ArrayAdapter<String> rubricasAdapter;
    private long asignaturaId;
    private int rubricaId;
    private List<String> rubricas;
    private List<Rubrica> rubricasEntities = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public EvaluacionesDentroAsignaturas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluacionesDentroAsignaturas.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluacionesDentroAsignaturas newInstance(String param1, String param2) {
        EvaluacionesDentroAsignaturas fragment = new EvaluacionesDentroAsignaturas();
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
        return inflater.inflate(R.layout.fragment_evaluaciones_dentro_asignaturas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<Estudiante> estudianteEntities = AppDatabase.getAppDatabase(getActivity()).evaluacionDao().getAllForOneAsignatura((int)asignaturaId);
        evaluaciones = new ArrayList<String>();
        evaluaciones.addAll(mapEstudiantesToNames(estudianteEntities));
        evaluacionesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, evaluaciones);
        ListView listview = (ListView) getActivity().findViewById(R.id.evaluacionesDentroAsignaturas);
        listview.setAdapter(evaluacionesAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = SingleEvaluacion.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //asignaturasEntities = AppDatabase.getAppDatabase(getActivity()).asignaturaDao().getAll(); // refetch all asigntauras for Ids
                //long asignaturaId = asignaturasEntities.get(i).getUid();
                /*Bundle bundle = new Bundle();
                bundle.putInt("asignaturaId", (int) asignaturaId);
                fragment.setArguments(bundle);*/
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                //getActivity().setTitle(asignaturas.get(i)+"");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.evaluaciones_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rubricasEntities = AppDatabase.getAppDatabase(getActivity()).rubricaDao().getAll();
                rubricas = new ArrayList<String>();
                rubricas.addAll(mapRubricasToNames(rubricasEntities));
                rubricasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, rubricas);

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Crear Evaluacion")
                        .setMessage("Ingrese un nombre!")
                        .setView(R.layout.custom_dialog)
                        .setCancelable(false)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Dialog dialogObj = Dialog.class.cast(dialog);
                                EditText edt =(EditText) dialogObj.findViewById(R.id.evaluacion_nombre);
                                String nombre = edt.getText().toString();
                                evaluaciones.add(nombre);
                                evaluacionesAdapter.notifyDataSetChanged();
                                Evaluacion newEvaluacion = new Evaluacion();
                                newEvaluacion.setNombre(nombre);
                                newEvaluacion.setAsignaturaId((int) asignaturaId);
                                newEvaluacion.setRubricaId(rubricaId);
                                AppDatabase.getAppDatabase(getActivity()).evaluacionDao().insertAll(newEvaluacion);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                Spinner spinner = (Spinner) dialog.findViewById(R.id.dialog_spinner);
                rubricasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(rubricasAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        rubricaId = (int) rubricasEntities.get(i).getUid();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
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

    private List<String> mapRubricasToNames(List<Rubrica> rubricasObjects) {
        List<String> list = new ArrayList<String>();
        for (Rubrica rubrica: rubricasObjects) {
            list.add(rubrica.getNombre());
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

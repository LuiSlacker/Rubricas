package com.uninorte.rubricas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.calificacion.categoria.CalificacionCategoria;
import com.uninorte.rubricas.db.calificacion.elemento.CalificacionElemento;
import com.uninorte.rubricas.db.categoria.Categoria;
import com.uninorte.rubricas.db.elementos.Elemento;
import com.uninorte.rubricas.db.evaluacion.Evaluacion;
import com.uninorte.rubricas.helper.CategoriaWrapper;
import com.uninorte.rubricas.helper.ElementoWrapper;

import java.util.ArrayList;
import java.util.List;
import android.widget.LinearLayout.LayoutParams;


import static com.uninorte.rubricas.R.id.fab;
import static com.uninorte.rubricas.R.id.spinner;
import static com.uninorte.rubricas.db.AppDatabase.getAppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvaluacionUI.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvaluacionUI#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluacionUI extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean rogueFirstSelection;
    private int evaluacionId;
    private int calificacionEvaluacionId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EvaluacionUI() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluacionUI.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluacionUI newInstance(String param1, String param2) {
        EvaluacionUI fragment = new EvaluacionUI();
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
        calificacionEvaluacionId = getArguments().getInt("calificacionEvaluacionId");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluacion_ui, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout mainLayout = (LinearLayout) getActivity().findViewById(R.id.evaluacion_ui_wrapper);
        List<CalificacionCategoria> calificacionCategorias = fetchCalificacionCategorias();
        for (CalificacionCategoria calificacionCategoria : calificacionCategorias) {
            TextView categoriaHeading = new TextView(getActivity());
            categoriaHeading.setText(calificacionCategoria.getCategoriaNombre());
            categoriaHeading.setTextSize(20);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(18, 4, 0, 0);
            mainLayout.addView(categoriaHeading, params);
            List<CalificacionElemento> calificacionElementos = fetchCalificacionElementosForCalificacionCategoria((int) calificacionCategoria.getUid());

            for (CalificacionElemento calificacionElemento : calificacionElementos) {
                View singleElemento = getLayoutInflater().inflate(R.layout.single_elemento_evaluacion, null);
                TextView nombre = (TextView) singleElemento.findViewById(R.id.name);
                nombre.setText(calificacionElemento.getElementoNombre());

                Elemento elemento = fetchElementoById(calificacionElemento.getElementoId());



                List<String> elementoNiveles = new ArrayList<String>();
                elementoNiveles.addAll(mapElementoToNivelNames(elemento));
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, elementoNiveles);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final Spinner spinner = (Spinner) singleElemento.findViewById(R.id.spinner);
                spinner.setTag(calificacionElemento.getUid()+"");
                spinner.setAdapter(spinnerAdapter);
                spinner.setSelection(calificacionElemento.getNivel());

                // Post to avoid initial invocation where Tag wont be set yet
                spinner.post(new Runnable() {
                    @Override public void run() {
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                int calificacionElementoId = Integer.valueOf(parent.getTag().toString());
                                CalificacionElemento selectedCalificacionElemento = AppDatabase.getAppDatabase(getActivity())
                                        .calificacionElementoDao()
                                        .getOneById(calificacionElementoId);
                                selectedCalificacionElemento.setNivel(position);
                                AppDatabase.getAppDatabase(getActivity()).calificacionElementoDao().insertAll(selectedCalificacionElemento);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                });
                mainLayout.addView(singleElemento);
            }
        }


        Button guardarBtn = (Button) getView().findViewById(R.id.guardar);
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    private List<String> mapElementoToNivelNames(Elemento elemento) {
        List<String> list = new ArrayList<>();
        list.add(elemento.getNivel1());
        list.add(elemento.getNivel2());
        list.add(elemento.getNivel3());
        list.add(elemento.getNivel4());
        return list;
    }

    private List<CalificacionCategoria> fetchCalificacionCategorias() {
        return AppDatabase.getAppDatabase(getActivity()).calificacionCategoriaDao().getAllForOneCalificacionEvaluacion(calificacionEvaluacionId);
    }

    private List<CalificacionElemento> fetchCalificacionElementosForCalificacionCategoria(int calificacionCategoriaId) {
        return AppDatabase.getAppDatabase(getActivity()).calificacionElementoDao().getAllForOneCalificacionCategoria(calificacionCategoriaId);
    }

    private Elemento fetchElementoById(int elementoId) {
        return AppDatabase.getAppDatabase(getActivity()).elementoDao().getOneById(elementoId);
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

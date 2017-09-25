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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriasDentroRubricas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriasDentroRubricas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasDentroRubricas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> categorias;
    private ArrayAdapter<String> categoriasAdapter;
    private List<Categoria> categoriasEntities = new ArrayList<>();

    private int rubricaId;
    private String rubricaNombre;

    private OnFragmentInteractionListener mListener;

    public CategoriasDentroRubricas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriasDentroRubricas.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriasDentroRubricas newInstance(String param1, String param2) {
        CategoriasDentroRubricas fragment = new CategoriasDentroRubricas();
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

        rubricaId = getArguments().getInt("rubricaId");
        rubricaNombre = getArguments().getString("rubricaNombre");

        getActivity().setTitle(rubricaNombre);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias_dentro_rubricas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        categoriasEntities = AppDatabase.getAppDatabase(getActivity()).categoriaDao().getAllForOneRubrica(rubricaId);
        categorias = new ArrayList<String>();
        categorias.addAll(mapCategoriasToNames(categoriasEntities));
        categoriasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categorias);
        ListView listview = (ListView) getActivity().findViewById(R.id.categoriasListView);
        listview.setAdapter(categoriasAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = ElementosDentroCategorias.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                categoriasEntities = AppDatabase.getAppDatabase(getActivity()).categoriaDao().getAllForOneRubrica(rubricaId); // refetch all categorias for Ids
                long categoriaId = categoriasEntities.get(i).getUid();
                Bundle bundle = new Bundle();
                bundle.putInt("categoriaId", (int) categoriaId);
                bundle.putString("categoriaNombre", categorias.get(i)+"");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("categorias").commit();
                getActivity().setTitle(categorias.get(i)+"");
            }
        });


        final LinearLayout mainLayout = (LinearLayout) getView().findViewById(R.id.main_layout);
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.categorias_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Crear Categoria")
                        .setMessage("Ingrese un nombre!")
                        .setView(R.layout.custom_categorias_dialog)
                        .setCancelable(false)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Dialog dialogObj = Dialog.class.cast(dialog);
                                EditText edtNombre = (EditText) dialogObj.findViewById(R.id.categorias_nombre);
                                EditText edtPeso = (EditText) dialogObj.findViewById(R.id.categorias_peso);
                                String nombre = edtNombre.getText().toString();
                                int peso = Integer.parseInt(edtPeso.getText().toString());
                                categorias.add(nombre);
                                categoriasAdapter.notifyDataSetChanged();
                                Categoria newCategoria = new Categoria();
                                newCategoria.setNombre(nombre);
                                newCategoria.setPeso(peso);
                                newCategoria.setRubricaId((int) rubricaId);
                                AppDatabase.getAppDatabase(getActivity()).categoriaDao().insertAll(newCategoria);
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


    private List<String> mapCategoriasToNames(List<Categoria> categoriasObjects) {
        List<String> list = new ArrayList<String>();
        for (Categoria categoria: categoriasObjects) {
            list.add(categoria.getNombre());
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

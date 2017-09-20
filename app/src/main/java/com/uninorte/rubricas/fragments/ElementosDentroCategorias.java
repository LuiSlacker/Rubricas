package com.uninorte.rubricas.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.uninorte.rubricas.db.categoria.Categoria;
import com.uninorte.rubricas.db.elementos.Elemento;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ElementosDentroCategorias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ElementosDentroCategorias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElementosDentroCategorias extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> elementos;
    private ArrayAdapter<String> elementosAdapter;
    private List<Elemento> elementosEntities = new ArrayList<>();

    private int categoriaId;

    private OnFragmentInteractionListener mListener;

    public ElementosDentroCategorias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElementosDentroCategorias.
     */
    // TODO: Rename and change types and number of parameters
    public static ElementosDentroCategorias newInstance(String param1, String param2) {
        ElementosDentroCategorias fragment = new ElementosDentroCategorias();
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

        categoriaId = getArguments().getInt("categoriaId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elementos_dentro_categorias, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        elementosEntities = AppDatabase.getAppDatabase(getActivity()).elementoDao().getAllForOneCategoria(categoriaId);
        elementos = new ArrayList<String>();
        elementos.addAll(mapElementosToNames(elementosEntities));
        elementosAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, elementos);
        ListView listview = (ListView) getActivity().findViewById(R.id.elementosListView);
        listview.setAdapter(elementosAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = AsignaturasTabWrapper.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                asignaturasEntities = AppDatabase.getAppDatabase(getActivity()).asignaturaDao().getAll(); // refetch all asigntauras for Ids
                long asignaturaId = asignaturasEntities.get(i).getUid();
                Bundle bundle = new Bundle();
                bundle.putInt("asignaturaId", (int) asignaturaId);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                getActivity().setTitle(asignaturas.get(i)+"");*/
            }
        });


        final LinearLayout mainLayout = (LinearLayout) getView().findViewById(R.id.main_layout);
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.elementos_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Crear Categoria")
                        .setMessage("Ingrese un nombre!")
                        .setView(R.layout.custom_elementos_dialog)
                        .setCancelable(false)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Dialog dialogObj = Dialog.class.cast(dialog);
                                EditText edtNombre = (EditText) dialogObj.findViewById(R.id.elementos_nombre);
                                EditText edtPeso = (EditText) dialogObj.findViewById(R.id.elementos_peso);
                                EditText edtN1 = (EditText) dialogObj.findViewById(R.id.elementos_nivel1);
                                EditText edtN2 = (EditText) dialogObj.findViewById(R.id.elementos_nivel2);
                                EditText edtN3 = (EditText) dialogObj.findViewById(R.id.elementos_nivel3);
                                EditText edtN4 = (EditText) dialogObj.findViewById(R.id.elementos_nivel4);
                                String nombre = edtNombre.getText().toString();
                                int peso = Integer.parseInt(edtPeso.getText().toString());
                                String nivel1 = edtN1.getText().toString();
                                String nivel2 = edtN2.getText().toString();
                                String nivel3 = edtN3.getText().toString();
                                String nivel4 = edtN4.getText().toString();
                                elementos.add(nombre);
                                elementosAdapter.notifyDataSetChanged();
                                Elemento newElemento = new Elemento();
                                newElemento.setNombre(nombre);
                                newElemento.setPeso(peso);
                                newElemento.setNivel1(nivel1);
                                newElemento.setNivel2(nivel2);
                                newElemento.setNivel3(nivel3);
                                newElemento.setNivel4(nivel4);
                                newElemento.setCategoriaId((int) categoriaId);
                                AppDatabase.getAppDatabase(getActivity()).elementoDao().insertAll(newElemento);
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

    private List<String> mapElementosToNames(List<Elemento> elementosObjects) {
        List<String> list = new ArrayList<String>();
        for (Elemento elemento: elementosObjects) {
            list.add(elemento.getNombre());
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

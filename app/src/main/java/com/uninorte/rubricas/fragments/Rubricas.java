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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.uninorte.rubricas.R;
import com.uninorte.rubricas.db.AppDatabase;
import com.uninorte.rubricas.db.rubrica.Rubrica;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Rubricas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Rubricas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rubricas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> rubricas;
    private ArrayAdapter<String> rubricasAdapter;
    private List<Rubrica> rubricasEntities = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public Rubricas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rubricas.
     */
    // TODO: Rename and change types and number of parameters
    public static Rubricas newInstance(String param1, String param2) {
        Rubricas fragment = new Rubricas();
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

        getActivity().setTitle("Rúbricas");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rubricas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rubricasEntities = AppDatabase.getAppDatabase(getActivity()).rubricaDao().getAll();
        rubricas = new ArrayList<String>();
        rubricas.addAll(mapRubricasToNames(rubricasEntities));
        rubricasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, rubricas);
        ListView listview = (ListView) getActivity().findViewById(R.id.rubricasListView);
        listview.setAdapter(rubricasAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = CategoriasDentroRubricas.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rubricasEntities = AppDatabase.getAppDatabase(getActivity()).rubricaDao().getAll(); // refetch all rubricas for Ids
                long rubricaId = rubricasEntities.get(i).getUid();
                Bundle bundle = new Bundle();
                bundle.putInt("rubricaId", (int) rubricaId);
                bundle.putString("rubricaNombre", rubricas.get(i)+"");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("rubricas").commit();
                getActivity().setTitle(rubricas.get(i)+"");
            }
        });


        final LinearLayout mainLayout = (LinearLayout) getView().findViewById(R.id.main_layout);
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fabrub);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nombreEditText = new EditText(getActivity());
                nombreEditText.setHint("Nueva Rúbrica");

                new AlertDialog.Builder(getActivity())
                        .setTitle("Crear Rúbrica")
                        .setMessage("Ingrese un nombre!")
                        .setView(nombreEditText)
                        .setCancelable(false)
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String nombre = nombreEditText.getText().toString();

                                if (!nombreEditText.getText().toString().trim().equalsIgnoreCase("")) {
                                    rubricas.add(nombre);
                                    rubricasAdapter.notifyDataSetChanged();

                                    Rubrica newRubrica = new Rubrica();
                                    newRubrica.setNombre(nombre);
                                    AppDatabase.getAppDatabase(getActivity()).rubricaDao().insertAll(newRubrica);
                                }

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

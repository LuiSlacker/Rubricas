package com.uninorte.rubricas;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.uninorte.rubricas.fragments.Asignaturas;
import com.uninorte.rubricas.fragments.CategoriasDentroRubricas;
import com.uninorte.rubricas.fragments.ElementosDentroCategorias;
import com.uninorte.rubricas.fragments.Estudiantes;
import com.uninorte.rubricas.fragments.EstudiantesDentroAsignaturas;
import com.uninorte.rubricas.fragments.EstudiantesReport;
import com.uninorte.rubricas.fragments.EvaluacionUI;
import com.uninorte.rubricas.fragments.EvaluacionesDentroAsignaturas;
import com.uninorte.rubricas.fragments.Info;
import com.uninorte.rubricas.fragments.Rubricas;
import com.uninorte.rubricas.fragments.AsignaturasTabWrapper;
import com.uninorte.rubricas.fragments.SingleEvaluacion;

public class MainActivity
        extends AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            Estudiantes.OnFragmentInteractionListener,
            Asignaturas.OnFragmentInteractionListener,
            Rubricas.OnFragmentInteractionListener,
            EstudiantesDentroAsignaturas.OnFragmentInteractionListener,
            AsignaturasTabWrapper.OnFragmentInteractionListener,
            EvaluacionesDentroAsignaturas.OnFragmentInteractionListener,
            CategoriasDentroRubricas.OnFragmentInteractionListener,
            ElementosDentroCategorias.OnFragmentInteractionListener,
            SingleEvaluacion.OnFragmentInteractionListener,
            EvaluacionUI.OnFragmentInteractionListener,
            EstudiantesReport.OnFragmentInteractionListener,
            Info.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = Asignaturas.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = null;
            Class fragmentClass = Info.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("info").commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_asignatura) {
            fragmentClass = Asignaturas.class;
        /*} else if (id == R.id.nav_estudiantes) {
            fragmentClass = Estudiantes.class;*/
        } else if (id == R.id.nav_rubricas) {
            fragmentClass = Rubricas.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("main").commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO closeDB
    }
}

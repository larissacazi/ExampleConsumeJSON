package zimmermann.larissa.legislativoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoapp.adapter.ProposicaoAdapter;
import zimmermann.larissa.legislativoapp.adapter.SituationAdapter;
import zimmermann.larissa.legislativoapp.communication.PropListResponse;
import zimmermann.larissa.legislativoapp.communication.Proposicao;
import zimmermann.larissa.legislativoapp.communication.Situation;
import zimmermann.larissa.legislativoapp.communication.SituationListResponse;
import zimmermann.larissa.legislativoapp.recycler.ClickListener;
import zimmermann.larissa.legislativoapp.recycler.DividerItemDecoration;
import zimmermann.larissa.legislativoapp.recycler.RecyclerTouchListener;
import zimmermann.larissa.legislativoapp.service.RetrofitService;
import zimmermann.larissa.legislativoapp.service.ServiceGenerator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerTouchListener propTouch, situationTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        loadProps();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prop) {
            loadProps();
        } else if (id == R.id.nav_prop_situacao) {
            Toast.makeText(getApplicationContext(), "Situacao das Proposicoes pressionado!", Toast.LENGTH_SHORT).show();
            loadSituationPropsList();
        } else if (id == R.id.nav_prop_ano) {
            showYearDialog();
        } else if (id == R.id.nav_tutorial) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    private void showYearDialog() {
        int currentYear = getCurrentYear();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.calendar3);
        builderSingle.setTitle("Escolha o ano:");

        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.select_dialog_item);
        for(int i = 0; i<100; i++) {
            arrayAdapter.add(currentYear - i);
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer year = arrayAdapter.getItem(which);
                Toast.makeText(getApplicationContext(), "Year: " + year.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builderSingle.show();

    }

    private void showPropsSituationDialog(final ArrayAdapter<String> arrayAdapter) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.situacao);
        builderSingle.setTitle("Escolha a Situação da Proposição:");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String situation = arrayAdapter.getItem(which);
                Toast.makeText(getApplicationContext(), "Situation: " + situation, Toast.LENGTH_SHORT).show();
            }
        });
        builderSingle.show();
    }

    private void loadProps(){
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Log.d("MainActivity", "Passou1");

       // Call<PropListResponse> call = service.getDefaultProposicaoList();
        Call<PropListResponse> call = service.getProposicaoListByYear(getCurrentYear());

        call.enqueue(new Callback<PropListResponse>() {
            @Override
            public void onResponse(Call<PropListResponse> call, Response<PropListResponse> response) {

                Log.d("MainActivity", "Recebeu resposta.");
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Resposta recebida com sucesso.");
                    PropListResponse respostaServidor = response.body();
                    Log.d("MainActivity", "Response saved!");

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.d("MainActivity", "PropListResponse structure received!");
                        final List<Proposicao> props = respostaServidor.getDados();

                        propTouch = new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
                            //@Override
                            public void onClick(View view, int position) {
                                if(position < props.size()){
                                    Proposicao prop = props.get(position);
                                    Intent intent = new Intent(MainActivity.this, PropDetailsActivity.class);
                                    Bundle b = new Bundle();
                                    b.putInt("Id", prop.getId()); //Your id
                                    intent.putExtras(b); //Put your id to your next Intent
                                    startActivity(intent);
                                }
                            }

                            //@Override
                            public void onLongClick(View view, int position) {

                            }
                        });

                        recyclerView.removeOnItemTouchListener(situationTouch);
                        recyclerView.addOnItemTouchListener(propTouch);

                        ProposicaoAdapter adapter = new ProposicaoAdapter(props, R.layout.list_item_prop2, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {

                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<PropListResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Error OnFailure()");
            }
        });
    }

    private void loadSituationPropsList(){
        //final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.situation_props_recyclerview);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
        Call<SituationListResponse> call = service.getAllPropSituation();

        call.enqueue(new Callback<SituationListResponse>() {
            @Override
            public void onResponse(Call<SituationListResponse> call, Response<SituationListResponse> response) {

                Log.d("MainActivity", "loadSituationPropsList: Recebeu resposta.");
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "loadSituationPropsList: Resposta recebida com sucesso.");
                    SituationListResponse respostaServidor = response.body();
                    Log.d("MainActivity", "loadSituationPropsListResponse saved!");

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        final List<Situation> situationList = respostaServidor.getDados();

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
                        for(int i = 0; i<situationList.size(); i++) {
                            arrayAdapter.add(situationList.get(i).getNome());
                        }
                        showPropsSituationDialog(arrayAdapter);
                    } else {

                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<SituationListResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "loadSituationPropsList:Error OnFailure()");
            }
        });
    }
}


package zimmermann.larissa.legislativoapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.ShareActionProvider;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoapp.adapter.DeputadoAdapter;
import zimmermann.larissa.legislativoapp.adapter.ProposicaoAdapter;
import zimmermann.larissa.legislativoapp.communication.Deputado;
import zimmermann.larissa.legislativoapp.communication.DeputadoListResponse;
import zimmermann.larissa.legislativoapp.communication.Link;
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
        implements NavigationView.OnNavigationItemSelectedListener,
        PropConnectionService.propCallBack,
        DepConnectionService.depCallBack{

    private final int INITIAL_YEAR = 1934;
    private final String PROP = "PROP";
    private final String DEP = "DEP";

    private ProgressDialog loading;

    private RecyclerView recyclerView;
    private RecyclerTouchListener propTouch;
    private DividerItemDecoration recyclerDecorator;

    private ShareActionProvider mShareActionProvider;

    private String nextUrl;
    private String selfUrl;
    private String previousUrl;
    private String lastUrl;
    private String firstUrl;
    private String label = PROP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);

        loading = new ProgressDialog(MainActivity.this);
        loading.setIndeterminate(true);
        loading.setTitle(getString(R.string.loading));
        loading.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loading.setProgressNumberFormat(null);
        loading.setProgressPercentFormat(null);
        loading.show();

        recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);
        recyclerDecorator = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(recyclerDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPropsByYear(getCurrentYear());

        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        FloatingActionButton leftButton = (FloatingActionButton) findViewById(R.id.arrowLeft);
        FloatingActionButton rightButton = (FloatingActionButton) findViewById(R.id.arrowRight);
        final FloatingActionButton lastPageButton = (FloatingActionButton) findViewById(R.id.lastPage);
        FloatingActionButton firstPageButton = (FloatingActionButton) findViewById(R.id.firstPage);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        final PropConnectionService.propCallBack propcb = this;
        final DepConnectionService.depCallBack depcb = this;

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                startLoading();

                if(label.compareTo(DEP) == 0 && nextUrl != null) {
                    Log.d("MainActivity", "rightButton::DEP:link: " + nextUrl);
                    try {
                        DepConnectionService dcs = new DepConnectionService();
                        dcs.registerCallback(depcb);
                        dcs.execute(nextUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(label.compareTo(PROP) == 0  && nextUrl != null && nextUrl.isEmpty() == false) {
                    Log.d("MainActivity", "rightButton::PROP:link: " + nextUrl);
                    try {
                        PropConnectionService pcs = new PropConnectionService();
                        pcs.registerCallback(propcb);
                        pcs.execute(nextUrl);
                        //PropListResponse responseFromServer = pcs.execute(nextUrl).get();
                        //loadPropsFromUrl(responseFromServer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nada a mostrar.", Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                startLoading();

                if(label.compareTo(DEP) == 0 && previousUrl != null) {
                    try {
                        DepConnectionService dcs = new DepConnectionService();
                        dcs.registerCallback(depcb);
                        dcs.execute(previousUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(label.compareTo(PROP) == 0  && previousUrl != null && previousUrl.isEmpty() == false) {
                    try {
                        PropConnectionService pcs = new PropConnectionService();
                        pcs.registerCallback(propcb);
                        pcs.execute(previousUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nada a mostrar.", Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            }
        });

        lastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                startLoading();

                if(label.compareTo(DEP) == 0 && lastUrl != null) {
                    try {
                        DepConnectionService dcs = new DepConnectionService();
                        dcs.registerCallback(depcb);
                        dcs.execute(lastUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(label.compareTo(PROP) == 0  && lastUrl != null && lastUrl.isEmpty() == false) {
                    try {
                        PropConnectionService pcs = new PropConnectionService();
                        pcs.registerCallback(propcb);
                        pcs.execute(lastUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nada a mostrar.", Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            }
        });

        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                startLoading();

                if(label.compareTo(DEP) == 0 && firstUrl != null) {
                    try {
                        DepConnectionService dcs = new DepConnectionService();
                        dcs.registerCallback(depcb);
                        dcs.execute(firstUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(label.compareTo(PROP) == 0 && firstUrl != null && firstUrl.isEmpty() == false ) {
                    try {
                        PropConnectionService pcs = new PropConnectionService();
                        pcs.registerCallback(propcb);
                        pcs.execute(firstUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nada a mostrar.", Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
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

        MenuItem share = menu.findItem(R.id.action_share);
        if(share != null) {
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Info Já";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Subject");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

            mShareActionProvider.setShareIntent(sharingIntent);
        }

        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if(id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.about, null);
            builder.setView(layout);
            builder.show();
            return true;
        }//*/
        //else
        if(id == R.id.action_contact) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto: infoja.app@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[InfoJá] Dúvidas e Sugestões");
            startActivity(Intent.createChooser(emailIntent, "Enviar e-mail..."));

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
            startLoading();
            label = PROP;
            loadPropsByYear(getCurrentYear());
        } else if (id == R.id.nav_prop_situacao) {
            startLoading();
            label = PROP;
            loadSituationPropsList();
        } else if (id == R.id.nav_prop_ano) {
            label = PROP;
            showYearDialog();
        } else if (id == R.id.nav_deputados) {
            startLoading();
            label = DEP;
            loadDeputados();
        } else if (id == R.id.nav_tutorial) {
            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_app) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.about, null);
            builder.setView(layout);
            builder.show();
        } else if (id == R.id.nav_about_devs) {
            Intent intent = new Intent(MainActivity.this, DevelopersActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    private int findSituationId(final List<Situation> situationList, String situation) {
        int i = 0;

        for(i=0; i<situationList.size(); i++) {
            if(situationList.get(i).getNome().compareTo(situation) == 0) {
                return situationList.get(i).getId();
            }
        }
        return -1;
    }

    private void showYearDialog() {
        int currentYear = getCurrentYear();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.calendar3);
        builderSingle.setTitle("Escolha o ano:");

        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.select_dialog_item);
        for(int i = 0; i<currentYear - INITIAL_YEAR + 1; i++) {
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
                startLoading();
                Integer year = arrayAdapter.getItem(which);
                loadPropsByYear(year.intValue());
            }
        });
        builderSingle.show();

    }

    private void showPropsSituationDialog(final ArrayAdapter<String> arrayAdapter, final List<Situation> situationList) {
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
                startLoading();
                String situation = arrayAdapter.getItem(which);
                int idx = findSituationId(situationList, situation);
                loadPropsBySituationId(idx);
            }
        });
        builderSingle.show();
    }

    private void loadPropsByYear(int year){
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
        Call<PropListResponse> call = service.getProposicaoListByYear(year);
        loadProps(call);
    }

    private void loadPropsBySituationId(int idx){
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
        Call<PropListResponse> call = service.getProposicaoListBySituationId(idx);

        loadProps(call);
    }

    private void loadPropsFromUrl(PropListResponse respostaServidor) throws IOException {
        //verifica aqui se o corpo da resposta não é nulo
        if (respostaServidor != null) {

            setLinks(respostaServidor.getLinks());

            final List<Proposicao> props = respostaServidor.getDados();

            recyclerView.removeOnItemTouchListener(propTouch);

            setPropTouchListener(props, recyclerView);

            recyclerView.addOnItemTouchListener(propTouch);

            ProposicaoAdapter adapter = new ProposicaoAdapter(props, R.layout.list_item_prop, getApplicationContext());
            recyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
        }
        endLoading();
    }

    private void loadProps(Call<PropListResponse> call){
        call.enqueue(new Callback<PropListResponse>() {
            @Override
            public void onResponse(Call<PropListResponse> call, Response<PropListResponse> response) {
                if (response.isSuccessful()) {
                    PropListResponse respostaServidor = response.body();

                    setLinks(respostaServidor.getLinks());

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        final List<Proposicao> props = respostaServidor.getDados();

                        recyclerView.removeOnItemTouchListener(propTouch);

                        setPropTouchListener(props, recyclerView);

                        recyclerView.addOnItemTouchListener(propTouch);

                        ProposicaoAdapter adapter = new ProposicaoAdapter(props, R.layout.list_item_prop, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
                endLoading();
            }

            @Override
            public void onFailure(Call<PropListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSituationPropsList(){
        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
        Call<SituationListResponse> call = service.getAllPropSituation();

        call.enqueue(new Callback<SituationListResponse>() {
            @Override
            public void onResponse(Call<SituationListResponse> call, Response<SituationListResponse> response) {

                if (response.isSuccessful()) {
                    SituationListResponse respostaServidor = response.body();
                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        final List<Situation> situationList = respostaServidor.getDados();
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
                        for(int i = 0; i<situationList.size(); i++) {
                            arrayAdapter.add(situationList.get(i).getNome());
                        }
                        arrayAdapter.sort(new Comparator<String>() {
                            @Override
                            public int compare(String arg1, String arg0) {
                                return arg1.compareTo(arg0);
                            }
                        });
                        endLoading();
                        showPropsSituationDialog(arrayAdapter, situationList);
                    } else {

                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<SituationListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDepsFromUrl(DeputadoListResponse respostaServidor) throws IOException {

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);

        //verifica aqui se o corpo da resposta não é nulo
        if (respostaServidor != null) {
            setLinks(respostaServidor.getLinks());

            final List<Deputado> deputados = respostaServidor.getDados();

            recyclerView.removeOnItemTouchListener(propTouch);
            DeputadoAdapter adapter = new DeputadoAdapter(deputados, R.layout.list_deputado, getApplicationContext());
            recyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
        }
        endLoading();
    }

    private void loadDeputados() {
        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);
        Call<DeputadoListResponse> call = service.getDeputadoList();

        call.enqueue(new Callback<DeputadoListResponse>() {
            @Override
            public void onResponse(Call<DeputadoListResponse> call, Response<DeputadoListResponse> response) {

                if (response.isSuccessful()) {
                    DeputadoListResponse respostaServidor = response.body();

                    setLinks(respostaServidor.getLinks());

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        final List<Deputado> deputados = respostaServidor.getDados();

                        recyclerView.removeOnItemTouchListener(propTouch);
                        DeputadoAdapter adapter = new DeputadoAdapter(deputados, R.layout.list_deputado, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
                endLoading();
            }

            @Override
            public void onFailure(Call<DeputadoListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPropTouchListener(final List<Proposicao> props, final RecyclerView recyclerView){
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
    }

    private void setLinks(List<Link> links){
        previousUrl = null;
        selfUrl = null;
        nextUrl = null;
        firstUrl = null;
        lastUrl = null;

        for(int i = 0; i < links.size(); i++){
            Link aux = links.get(i);
            if (aux.getRel().equals("self")){
                selfUrl = aux.getHref();
            }else if (aux.getRel().equals("next")){
                nextUrl = aux.getHref();
            }else if (aux.getRel().equals("first")){
                firstUrl = aux.getHref();
            }else if (aux.getRel().equals("last")){
                lastUrl = aux.getHref();
            }else if (aux.getRel().equals("previous")){
                previousUrl = aux.getHref();
            }
        }
    }

    private void startLoading(){
        loading.show();
    }

    private void endLoading(){
        loading.dismiss();
    }

    @Override
    public void updateProps(PropListResponse responseFromServer) {
        try {
            loadPropsFromUrl(responseFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDeps(DeputadoListResponse responseFromServer) {
        try {
            loadDepsFromUrl(responseFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package com.example.kuicly.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuicly.CarrinhoActivity;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.listners.CarrinhoListener;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.listners.CursosListener;
import com.example.kuicly.listners.LicaoListener;
import com.example.kuicly.listners.LicoesListener;
import com.example.kuicly.listners.LoginListener;
import com.example.kuicly.utils.CarrinhoItensJsonParser;
import com.example.kuicly.utils.CursoJsonParser;
import com.example.kuicly.utils.LicaoJsonParser;
import com.example.kuicly.utils.LoginJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorCursos {

    private ArrayList<Curso> cursos;
    private ArrayList<Licao> licoes;
    private ArrayList<Carrinho> carrinhos;

    private ArrayList<CarrinhoItens> carrinhoItens;


    private static SingletonGestorCursos instance = null;

    private CursoBDHelper CursoBD= null;

    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPICursos = "http://10.0.2.2/kuicly/backend/web/api/courses/courses";
    private static final String mUrlAPILogin = "http://10.0.2.2/kuicly/backend/web/api/logins/login";

    private static final String mUrlAPILicoes ="http://10.0.2.2/kuicly/backend/web/api/lessons/lessonsbycourse/52?token=pzziFplaKNl6L-dnKUBBz1pdAwk3rNj0";

    private static final String mUrlAPICarrinho ="http://10.0.2.2/kuicly/backend/web/api/carts/";

    private String token_user="";

    private String login;
    private CursosListener cursosListner;
    private CursoListener cursoListner;
    private LoginListener loginListener;

    private LicaoListener licaoListner;

    private LicoesListener licoesListner;

    private CarrinhoItensListener carrinhoItensListener;
    private CarrinhoListener carrinhoListener;

    public static synchronized SingletonGestorCursos getInstance(Context context){
        if(instance == null) {
            instance = new SingletonGestorCursos(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorCursos(Context context){

        cursos = new ArrayList<>();
        CursoBD = new CursoBDHelper(context);
    }
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    public void setCursosListner(CursosListener cursosListner) {
        this.cursosListner = cursosListner;
    }

    public void setCursoListner(CursoListener cursoListner) {
        this.cursoListner = cursoListner;
    }
    public ArrayList<Curso> getCursosBD(){
        cursos = CursoBD.getAllCursosBD();
        return new ArrayList<>(cursos);
    }

    public void setLicaoListner(LicaoListener licaoListner) {
        this.licaoListner = licaoListner;
    }

    public void setLicoesListner(LicoesListener licoesListner) {
        this.licoesListner = licoesListner;
    }

    public void setCarrinhoItensListener(CarrinhoItensListener carrinhoItensListener) {
        this.carrinhoItensListener = carrinhoItensListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;
    }

    /*public void adicionarALLCursosBD(ArrayList<Curso> cursos){
        cursosBD.removerAllCursosBD();
        for(Curso curso: cursos){
            adicionarCursoBD(curso);
        }
    }*/
    /*ublic void adicionarCursoBD(Curso curso){
        cursosBD.adicionarCursoBD(curso);
    }

    public void editarCursoBD(Curso curso){

        if(curso != null) {
            cursosBD.editarCursoBD(curso);
        }
    }

    public void removerCursoBD(int idCurso){
        cursosBD.removerCursoBD(idCurso);
    }*/

    public Curso getCurso(int id){
        for(Curso curso: cursos){
            if(curso.getId() == id){
                return curso;
            }
        }
        return null;
    }

    public Licao getLicao(int id){
        for(Licao licao: licoes){
            if(licao.getId() == id){
                return licao;
            }
        }
        return null;
    }

    public Carrinho getCarrinho(int id){
        for(Carrinho carrinho: carrinhos){
            if(carrinho.getId() == id){
                return carrinho;
            }
        }
        return null;
    }

    //region PEDIDOS API
    /*public void adicionarLivroAPI(final Curso curso, final Context context){
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.POST, mUrlAPICursos, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    adicionarCursoBD(CursoJsonParser.parserJsonCurso(response));

                    if(cursoListner != null){
                        cursoListner.onRefreshDetalhes(MainActivity.ADD);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    //params.put("token",TOKEN);
                    params.put("titulo", curso.getTitle());
                    params.put("descricao", curso.getDescription());
                    params.put("preco", curso.getPrice()+"");
                    params.put("skill_level", curso.getSkill_level()+"");


                    return params;
                }
            };
            volleyQueue.add(req);
        }

    }*/

    public void getAllCursosAPI(final Context context){
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPICursos+"?token="+token_user,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    cursos= CursoJsonParser.parserJsonCursos(response);
                    //adicionarALLCursosBD(cursos);

                    if(cursosListner != null){
                        cursosListner.onRefreshListaCursos(cursos);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
    public void removerCarrinhoItemAPI(final Carrinho carrinho, final Context context){
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.DELETE, mUrlAPICursos+"/"+carrinho.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //removerItemBD(curso.getId());
                    if(carrinhoListener != null){
                        carrinhoListener.onRefreshDetalhes(CarrinhoActivity.DELETE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    /*public void editarCursoAPI(final Curso curso, final Context context){
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.PUT, mUrlAPICursos+'/'+curso.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    editarCursoBD(curso);
                    if(cursoListner != null){
                        cursoListner.onRefreshDetalhes(MainActivity.EDIT);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    //params.put("token",TOKEN);
                    params.put("titulo", curso.getTitle());
                    params.put("descricao", curso.getDescription());
                    params.put("preco", curso.getPrice()+"");
                    params.put("skill_level", curso.getSkill_level()+"");


                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }*/
    //end region

    public void loginAPI(final String username, final String password, final Context context) {
        if (!LoginJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject loginJSON = new JSONObject(response);
                                String token = loginJSON.getString("token");

                                login = LoginJsonParser.parserJsonLogin(response);
                                token_user=token;
                                SharedPreferences sharedToken = context.getSharedPreferences("DADOS", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedToken.edit();
                                editor.putString("token", token);
                                editor.apply();

                                if (loginListener != null) {
                                    loginListener.onUpdateLogin(login);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, "loginAPI erro", Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "loginAPI erro response", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(request); // Adicione a requisição à fila do Volley para ser executada
        }
    }
    public void getAllLicoesAPI(final Context context){
        if(!LicaoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPILicoes,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    licoes= LicaoJsonParser.parserJsonLicoes(response);
                    //adicionarALLCursosBD(cursos);

                    if(licoesListner != null){
                        licoesListner.onRefreshListaLicoes(licoes);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getAllCarrinhoItensAPI(final Context context){
        if(!LicaoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPILicoes,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    carrinhoItens= CarrinhoItensJsonParser.parserJsonCarrinhoItens(response);
                    //adicionarALLCursosBD(cursos);

                    if(carrinhoItensListener != null){
                        carrinhoItensListener.onRefreshListaCarrinhoItens(carrinhoItens);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }


}

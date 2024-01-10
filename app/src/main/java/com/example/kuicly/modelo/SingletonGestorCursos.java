package com.example.kuicly.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.listners.CursosListener;
import com.example.kuicly.listners.LoginListener;
import com.example.kuicly.utils.CursoJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorCursos {

    private ArrayList<Curso> cursos;
    private static SingletonGestorCursos instance = null;

    private CursoBDHelper yii2kuicly= null;

    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPICursos = "http://127.0.0.1/kuicly/backend/web/api/courses";
    private static final String mUrlAPILogin = "http://127.0.0.1/kuicly/backend/web/api/auth/login";

    private static final String TOKEN="AMSI-TOKEN";

    private CursosListener cursosListner;
    private CursoListener cursoListner;
    private LoginListener loginListener;

    public static synchronized SingletonGestorCursos getInstance(Context context){
        if(instance == null) {
            instance = new SingletonGestorCursos(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorCursos(Context context){

        cursos = new ArrayList<>();
        yii2kuicly = new CursoBDHelper(context);
    }
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    public void setCursosListner(CursosListener cursosListner) {
        this.cursosListner = cursosListner;
    }

    public void setLivroListner(CursoListener cursoListner) {
        this.cursoListner = cursoListner;
    }
    public ArrayList<Curso> getCursosBD(){
        cursos = yii2kuicly.getAllCursosBD();
        return new ArrayList<>(cursos);
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
            cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPICursos,null, new Response.Listener<JSONArray>() {
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
    /*public void removerCursoAPI(final Curso curso, final Context context){
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.DELETE, mUrlAPICursos+"/"+curso.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    removerCursoBD(curso.getId());
                    if(cursoListner != null){
                        cursoListner.onRefreshDetalhes(MainActivity.DELETE);
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
    }*/

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
        if (!CursoJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILogin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String token = CursoJsonParser.parserJsonLogin(response);

                            if (loginListener != null) {
                                loginListener.onUpdateLogin(token);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

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

}

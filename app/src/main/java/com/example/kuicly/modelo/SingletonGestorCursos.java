package com.example.kuicly.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kuicly.CarrinhoActivity;
import com.example.kuicly.listners.CarrinhoItemListener;
import com.example.kuicly.listners.CarrinhoItensListener;
import com.example.kuicly.listners.CarrinhoListener;
import com.example.kuicly.listners.CarrinhoTotalListener;
import com.example.kuicly.listners.CursoListener;
import com.example.kuicly.listners.CursosListener;
import com.example.kuicly.listners.FaturaItensListener;
import com.example.kuicly.listners.FaturaListener;
import com.example.kuicly.listners.LicaoListener;
import com.example.kuicly.listners.LicoesListener;
import com.example.kuicly.listners.LoginListener;
import com.example.kuicly.utils.CarrinhoItensJsonParser;
import com.example.kuicly.utils.CarrinhoJsonParser;
import com.example.kuicly.utils.CursoJsonParser;
import com.example.kuicly.utils.FaturaItensJsonParser;
import com.example.kuicly.utils.FaturaJsonParser;
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

    private ArrayList<FaturaItens> faturasItens;

    private ArrayList<Fatura> faturas;

    private Carrinho carrinho;
    private static SingletonGestorCursos instance = null;

    private CursoBDHelper CursoBD= null;

    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPICursos = "http://10.0.2.2/kuicly/backend/web/api/courses/courses";
    private static final String mUrlAPILogin = "http://10.0.2.2/kuicly/backend/web/api/logins/login";

    private static final String mUrlAPILicoes ="http://10.0.2.2/kuicly/backend/web/api/lessons";

    private static final String mUrlAPICarrinho ="http://10.0.2.2/kuicly/backend/web/api/carts";

    private static final String mUrlAPIFatura ="http://10.0.2.2/kuicly/backend/web/api/orders";

    private String login;

    private Fatura fatura;
    private CursosListener cursosListner;
    private CursoListener cursoListner;
    private LoginListener loginListener;

    private LicaoListener licaoListner;

    private LicoesListener licoesListner;

    private CarrinhoItensListener carrinhoItensListener;

    private CarrinhoItemListener carrinhoItemListener;

    private CarrinhoListener carrinhoListener;

    private CarrinhoTotalListener carrinhoTotalListener;

    private FaturaItensListener faturaItensListener;
    private FaturaListener faturaListener;


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

    public void setCarrinhoItemListener(CarrinhoItemListener carrinhoItemListener) {
        this.carrinhoItemListener = carrinhoItemListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;
    }

    public void setCarrinhoTotalListener(CarrinhoTotalListener carrinhoTotalListener) {
        this.carrinhoTotalListener = carrinhoTotalListener;
    }

    public void setFaturaItensListener(FaturaItensListener faturaItensListener) {
        this.faturaItensListener = faturaItensListener;
    }

    public void setFaturaListener(FaturaListener faturaListener) {
        this.faturaListener = faturaListener;
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

    public FaturaItens getFaturaItens(int id){
        for(FaturaItens faturaItens: faturasItens){
            if(faturaItens.getId() == id){
                return faturaItens;
            }
        }
        return null;
    }

    public Fatura getFatura(int id){
        for(Fatura fatura: faturas){
            if(fatura.getId() == id){
                return fatura;
            }
        }
        return null;
    }

    //region PEDIDOS API


    public void getAllCursosAPI(final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPICursos+"?token="+token,null, new Response.Listener<JSONArray>() {
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
                                int id = loginJSON.getInt("id");

                                login = LoginJsonParser.parserJsonLogin(response);

                                SharedPreferences sharedToken = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedToken.edit();
                                editor.putString("token", token);
                                editor.putInt("id", id);
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
    public void getAllLicoesAPI(int cursoId,final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");
        if(!LicaoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPILicoes+"/lessonsbycourse/"+cursoId+"?token="+token,null, new Response.Listener<JSONArray>() {
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
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int id= preferences.getInt("id",0);
        String token= preferences.getString("token","");

        if(!CarrinhoItensJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPICarrinho+"/"+id+"/items?token="+ token,null, new Response.Listener<JSONArray>() {
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

    public void getCarrinhoAPI(final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int id= preferences.getInt("id",0);
        String token= preferences.getString("token","");

        if(!CarrinhoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            StringRequest req=new StringRequest(Request.Method.GET, mUrlAPICarrinho+"/"+id+"?token="+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    carrinho= CarrinhoJsonParser.parserJsonCarrinho(response);
                    //adicionarALLCursosBD(cursos);

                    if(carrinhoTotalListener != null){
                        carrinhoTotalListener.onRefreshTotal(carrinho.getTotal());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "getCarrinhoAPI erro response", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
    public void removerCarrinhoItemAPI(final CarrinhoItens carrinhoItem, final Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int id= preferences.getInt("id",0);
        String token= preferences.getString("token","");
        if(!CarrinhoItensJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.DELETE, mUrlAPICarrinho+"/"+id+"/course/"+carrinhoItem.getId()+"?token"+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //removerItemBD(curso.getId());
                    if(carrinhoItemListener != null){
                        carrinhoItemListener.onRefreshDetalhes(CarrinhoActivity.DELETE);
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

    public void adicionarCarrinhoItemAPI(final Curso curso, final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int id= preferences.getInt("id",0);
        String token= preferences.getString("token","");
        if(!CarrinhoItensJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest req=new StringRequest(Request.Method.GET, mUrlAPICarrinho+"/"+id+"/course/"+curso.getId()+"?token="+token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //adicionarCursoBD(CursoJsonParser.parserJsonCurso(response));

                    if(carrinhoItemListener != null){
                        carrinhoItemListener.onRefreshDetalhes(CarrinhoActivity.ADD);
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

    public void getPagamentoAPI(final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int id= preferences.getInt("id",0);
        String token= preferences.getString("token","");

        if(!FaturaJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            StringRequest req=new StringRequest(Request.Method.GET, mUrlAPICarrinho+"/payment/"+id+"?token="+token,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    fatura = FaturaJsonParser.parserJsonFatura(response);
                    //adicionarALLCursosBD(cursos);

                    SharedPreferences SharedFatura = context.getSharedPreferences("DADOS_FATURA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = SharedFatura.edit();
                    editor.putInt("order_id", fatura.getId());
                    editor.apply();
                    Log.e("Erro de Solicitação", "Falha na solicitação: " + fatura.getId());
                    if(faturaItensListener != null){
                        faturaItensListener.onRefreshListaFaturaItens(faturasItens);
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

    public void getAllFaturasItensAPI(final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");
        SharedPreferences SharedFatura = context.getSharedPreferences("DADOS_FATURA", Context.MODE_PRIVATE);
        int id= SharedFatura.getInt("order_id",0);
        if(!CarrinhoItensJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPIFatura+"/"+id+"/items?token="+ token,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    faturasItens= FaturaItensJsonParser.parserJsonFaturaItens(response);
                    //adicionarALLCursosBD(cursos);

                    if(faturaItensListener != null){
                        faturaItensListener.onRefreshListaFaturaItens(faturasItens);
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

    public void getMeusCursosAPI(final Context context){
        SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");
        int id= preferences.getInt("id",0);
        if(!CursoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Não neeo ligação á internet", Toast.LENGTH_SHORT).show();
          /*  cursos=getCursosBD();
            if(cursosListner != null){
                cursosListner.onRefreshListaCursos(cursos);
            }*/
        }else{
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, mUrlAPICursos+id+"/mycourses/token="+token,null, new Response.Listener<JSONArray>() {
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
}

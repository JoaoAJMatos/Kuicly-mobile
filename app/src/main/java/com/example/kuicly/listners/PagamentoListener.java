package com.example.kuicly.listners;

import com.example.kuicly.modelo.Fatura;

public interface PagamentoListener {
    void onPagamentoSuccess(); // Chamado quando o pagamento é bem-sucedido
    void onPagamentoFailure(String errorMessage);
}

package br.com.alura.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.model.Tipo

class AdicionaTransacaoDialog(
        context: Context,
        viewGroup: ViewGroup) : FormularioTransacaoDialog(context, viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"


    override fun tituloPor(tipo: Tipo) =
            if (tipo == Tipo.RECEITA) R.string.adiciona_receita
            else R.string.adiciona_despesa

}

package br.com.alura.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.alura.financask.R
import br.com.alura.financask.R.id.lista_transacoes_adiciona_menu
import br.com.alura.financask.extensions.formataParaBrasileiro
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class AdicionaTransacaoDialog {

    class AdicionaTransacaoDialog(private val context: Context,
                                  private val viewGroup: ViewGroup) {

        private val viewCriada = criaLayout()

        fun configuraDialog() {
            configuraCampoData()
            configuraCampoCategoria()
            configuraFormulario()
        }

        private fun configuraFormulario() {
            AlertDialog.Builder(context)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .setPositiveButton("Adicionar"
                    ) { _ , _ ->
                        val valorEmTexto = viewCriada
                                .form_transacao_valor.text.toString()
                        val dataEmTexto = viewCriada
                                .form_transacao_data.text.toString()
                        val categoriaEmTexto = viewCriada
                                .form_transacao_categoria.selectedItem.toString()


                        val valor = try {
                            BigDecimal(valorEmTexto)
                        } catch (exception: NumberFormatException) {
                            Toast.makeText(context,
                                    "Falha na conversão de valor",
                                    Toast.LENGTH_LONG)
                                    .show()
                            BigDecimal.ZERO
                        }

                        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                        val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                        val data = Calendar.getInstance()
                        data.time = dataConvertida

                        val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                                valor = valor,
                                data = data,
                                categoria = categoriaEmTexto)

                        atualizaTransacoes(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
        }

        private fun configuraCampoCategoria() {
            val adapter = ArrayAdapter
                    .createFromResource(context,
                            R.array.categorias_de_receita,
                            android.R.layout.simple_spinner_dropdown_item)

            viewCriada.form_transacao_categoria.adapter = adapter
        }

        private fun configuraCampoData() {
            val hoje = Calendar.getInstance()

            val ano = 2017
            val mes = 9
            val dia = 18

            viewCriada.form_transacao_data
                    .setText(hoje.formataParaBrasileiro())
            viewCriada.form_transacao_data
                    .setOnClickListener {
                        DatePickerDialog(context,
                                DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                                    val dataSelecionada = Calendar.getInstance()
                                    dataSelecionada.set(ano, mes, dia)
                                    viewCriada.form_transacao_data
                                            .setText(dataSelecionada.formataParaBrasileiro())
                                }
                                , ano, mes, dia)
                                .show()
                    }
        }

        private fun criaLayout(): View {
            val viewCriada = LayoutInflater.from(context)
                    .inflate(R.layout.form_transacao,
                            viewGroup,
                            false)
            return viewCriada
        }
    }
}
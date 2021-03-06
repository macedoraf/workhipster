package br.com.rafael.workhipster.ui.usuario.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import br.com.rafael.workhipster.R
import br.com.rafael.workhipster.base.BaseFragment
import br.com.rafael.workhipster.model.Usuario
import br.com.rafael.workhipster.ui.usuario.adapter.UsuarioAdapter
import br.com.rafael.workhipster.ui.usuario.presenter.UsuarioPresenter
import br.com.rafael.workhipster.ui.usuario.view.UsuarioView

class ListaUsuarioFragment : BaseFragment<UsuarioPresenter>(), UsuarioView {


    lateinit var recyclerView: RecyclerView

    lateinit var progresssBar: ProgressBar

    private val adapter by lazy { UsuarioAdapter(this) }

    private lateinit var usuarioList: MutableList<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioList = mutableListOf()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lista_usuario, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progresssBar = view.findViewById(R.id.progressBar)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.getDadosUsuario()
    }


    override fun instantietePresenter(): UsuarioPresenter = UsuarioPresenter(this)

    override fun onError(err: Throwable) {
        Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()
    }


    override fun update(listUsuario: List<Usuario>) {
        this.usuarioList.clear()
        this.usuarioList.addAll(listUsuario)
        atualizaAdapter()

    }


    override fun atualizaAdapter() {
        adapter.notifyDataSetChanged()
    }


    override fun showLoading() {
        progresssBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progresssBar.visibility = View.GONE
    }

    override fun getUsuarioList(): List<Usuario> = usuarioList

}

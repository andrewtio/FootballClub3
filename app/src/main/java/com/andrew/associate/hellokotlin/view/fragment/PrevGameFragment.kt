package com.andrew.associate.hellokotlin.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrew.associate.hellokotlin.model.GameItems
import com.andrew.associate.hellokotlin.model.intface.PrevMatchView
import com.andrew.associate.hellokotlin.model.api.ApiRepository
import com.andrew.associate.hellokotlin.model.api.ApiRestInterface
import com.andrew.associate.hellokotlin.model.invisible
import com.andrew.associate.hellokotlin.model.visible
import com.andrew.associate.hellokotlin.R
import com.andrew.associate.hellokotlin.presenter.GameEventPresenter
import com.andrew.associate.hellokotlin.presenter.PrevGamePresenter
import com.andrew.associate.hellokotlin.presenter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_prev_game.*

class PrevGameFragment : Fragment(), PrevMatchView.View
{
    private var gI: MutableList<GameItems> = mutableListOf()

    private lateinit var pMP: PrevGamePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_prev_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val treatment = ApiRepository.getAPI().create(ApiRestInterface::class.java)
        val demand = GameEventPresenter(treatment)

        pMP = PrevGamePresenter(this, demand)
        pMP.getGamePrevItem()

    }

    override fun hideProgress() {
        ProgressGamePrev.invisible()
        rv_game_prev.visibility = View.VISIBLE
    }

    override fun showProgress() {
        ProgressGamePrev.visible()
        rv_game_prev.visibility = View.INVISIBLE
    }

    override fun displayGame(gameItem: List<GameItems>) {
        gI.clear()
        gI.addAll(gameItem)
        val lM = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_game_prev.layoutManager = lM
        rv_game_prev.adapter = RecyclerViewAdapter(gameItem, context)
    }

}
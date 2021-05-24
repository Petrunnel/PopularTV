package com.example.notes.populartv.ui.main_fragment

interface MainContract {

    interface Presenter {
        fun requestRefresh()

        fun getTvPosts()
    }

    interface View {
        fun showTvPosts()

        fun showError()
    }
}
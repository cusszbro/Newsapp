package com.lazday.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.lazday.news.R
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailActivity
import com.lazday.news.ui.login.LoginActivity
import com.lazday.news.ui.news.CategoryAdapter
import com.lazday.news.ui.news.CategoryModel
import com.lazday.news.ui.news.NewsAdapter
import com.lazday.news.util.SessionManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
            bindingToolbar.title = viewModel.title
            binding.listCategory.adapter = categoryAdapter

            bindingToolbar.container.inflateMenu(R.menu.menu_search)
            val menu = binding.toolbar.container.menu
            val search = menu.findItem(R.id.action_search)
            val logout = menu.findItem(R.id.log_out)
            logout.setOnMenuItemClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                sessionManager.deleteAccessToken()
                true
            }

            val searchView = search.actionView as SearchView
            searchView.queryHint = "Search"
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    firstPage()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.query = it }
                    return true
                }
            })

            binding.listNews.adapter = newsAdapter
            viewModel.news.observe(viewLifecycleOwner) {
                Timber.e("articleSize: ${it.articles.size}")
                if (viewModel.page == 1) newsAdapter.clear()
                newsAdapter.add(it.articles)
            }

            viewModel.category.observe(viewLifecycleOwner) {
                Timber.e("category ${viewModel.category.value}")
                NewsAdapter.VIEW_TYPE = if (it.isEmpty()) 1 else 2
                firstPage()
            }

            binding.scroll.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
                if (scrollY == v?.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                    if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false) viewModel.fetch()
                }
            }

            viewModel.message.observe(viewLifecycleOwner) {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.loading.postValue(false)
                }
            }
    }

    private fun firstPage(){
        binding.scroll.scrollTo(0, 0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.fetch()
    }

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticleModel) {
                startActivity(
                    Intent(requireActivity(), DetailActivity::class.java)
                        .putExtra("detail", article)
                )
            }
        })
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryModel) {
                viewModel.category.postValue(category.id)
            }
        })
    }




}
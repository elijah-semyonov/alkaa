package com.escodro.alkaa.ui.category.create

import com.escodro.alkaa.common.extension.applySchedulers
import com.escodro.alkaa.data.local.model.Category
import com.escodro.alkaa.di.provider.DaoProvider
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Class containing the contract methods related to [NewCategoryViewModel].
 */
class NewCategoryContract(daoProvider: DaoProvider) {

    private val categoryDao = daoProvider.getCategoryDao()

    /**
     * Loads the category based on the given id.
     *
     * @param categoryId category id
     */
    fun loadCategory(categoryId: Long): Single<Category> =
        categoryDao.findCategory(categoryId).applySchedulers()

    /**
     * Adds a category.
     *
     * @param category category to be added
     *
     * @return observable to be subscribe
     */
    fun addCategory(category: Category): Observable<Unit> =
        Observable.fromCallable { categoryDao.insertCategory(category) }.applySchedulers()
}

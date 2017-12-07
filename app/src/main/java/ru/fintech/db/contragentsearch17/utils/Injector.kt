package ru.fintech.db.contragentsearch17.utils

import dagger.Component
import ru.fintech.db.contragentsearch17.db.DatabaseService
import ru.fintech.db.contragentsearch17.presenters.*
import javax.inject.Singleton

/**
 * Created by DB on 07.12.2017.
 *
 */
@Singleton
@Component(modules=arrayOf(Dependencies::class))
interface Injector {
    fun inject(adapter: AutoCompleteAdapter)
    fun inject(presenter: DetailsPresenter)
    fun inject(databaseService: DatabaseService)
    fun inject(detailsClickListener: DetailsClickListener)
    fun inject(organizationListAdapter: OrganizationListAdapter)
    fun inject(suggestClickListener: SuggestClickListener)

}
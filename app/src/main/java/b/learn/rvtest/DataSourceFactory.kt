package b.learn.rvtest

import androidx.paging.DataSource


class DataSourceFactory : DataSource.Factory<Long, Passenger>() {

    override fun create(): DataSource<Long, Passenger> {
        return PassengersListDataSource()
    }
}
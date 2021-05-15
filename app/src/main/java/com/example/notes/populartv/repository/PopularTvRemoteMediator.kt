package com.example.notes.populartv.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.db.RemoteKey
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.utilits.API_KEY
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class PopularTvRemoteMediator(
    private val api: PopularTvApi,
    private val db: TvPostDatabase
) : RemoteMediator<Int, TvPost>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvPost>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = api.getTop(apiKey = API_KEY, pageNumber = page)
            val isEndOfList = response.results.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getTvPostDao().deleteAll()
                    db.getKeysDao().deleteAll()
                }
                val prevKey = if (page == FIRST_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.getKeysDao().insertAll(keys)
                db.getTvPostDao().insertAll(response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, TvPost>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvPost>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getKeysDao().remoteKeysTvPostId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, TvPost>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { tv -> db.getKeysDao().remoteKeysTvPostId(tv.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, TvPost>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { tv -> db.getKeysDao().remoteKeysTvPostId(tv.id) }
    }
}
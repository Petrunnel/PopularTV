package com.example.notes.populartv.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.search.SearchRemoteKey
import com.example.notes.populartv.db.search.SearchTvPostDatabase
import com.example.notes.populartv.models.SearchTvPost
import com.example.notes.populartv.utilits.API_KEY
import com.example.notes.populartv.utilits.FIRST_PAGE_INDEX
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class SearchTvRemoteMediator(
    private val api: PopularTvApi,
    private val db: SearchTvPostDatabase,
    private val searchRequest: String
) : RemoteMediator<Int, SearchTvPost>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchTvPost>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }

        }

        try {
            val response = api.getSearchResults(
                apiKey = API_KEY,
                searchRequest = searchRequest,
                pageNumber = page
            )
            val isEndOfList = response.results.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getTvPostDao().deleteAll()
                    db.getKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page == FIRST_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    SearchRemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchTvPost>
    ): SearchRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getKeysDao().remoteKeysTvPostId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchTvPost>): SearchRemoteKey? {
        return state.pages
            .lastOrNull() { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { repo -> db.getKeysDao().remoteKeysTvPostId(repo.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchTvPost>): SearchRemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { repo -> db.getKeysDao().remoteKeysTvPostId(repo.id) }
    }
}
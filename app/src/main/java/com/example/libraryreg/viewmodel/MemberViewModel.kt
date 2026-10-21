package com.example.libraryreg.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.libraryreg.data.AppDatabase
import com.example.libraryreg.data.Member
import com.example.libraryreg.data.MemberRepository
import com.example.libraryreg.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MemberViewModel(application: Application) : AndroidViewModel(application) {
    val repository: MemberRepository

    private val _isPetugasLoggedIn = MutableStateFlow(false)
    val isPetugasLoggedIn: StateFlow<Boolean> = _isPetugasLoggedIn.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        repository = MemberRepository(db.memberDao())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val members: Flow<List<Member>> = searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            repository.allMembers
        } else {
            repository.searchMembers(query)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loginPetugas(username: String, pin: String): Boolean {
        return if (username.trim() == Constants.STAFF_USERNAME && pin.trim() == Constants.STAFF_PIN) {
            _isPetugasLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun logoutPetugas() {
        _isPetugasLoggedIn.value = false
    }

    suspend fun insertMember(member: Member): Long {
        return repository.insertMember(member)
    }

    suspend fun updateMember(member: Member) {
        repository.updateMember(member)
    }

    suspend fun deleteMember(member: Member) {
        repository.deleteMember(member)
    }

    fun getMemberById(id: Int): Flow<Member?> {
        return repository.getMemberById(id)
    }

    suspend fun isNomorIndukExists(nomorInduk: String, excludeId: Int = 0): Boolean {
        return repository.isNomorIndukExists(nomorInduk, excludeId)
    }
}

package com.example.libraryreg.data

import kotlinx.coroutines.flow.Flow

class MemberRepository(private val memberDao: MemberDao) {
    val allMembers: Flow<List<Member>> = memberDao.getAllMembers()

    fun searchMembers(query: String): Flow<List<Member>> {
        return memberDao.searchMembers(query)
    }

    fun getMemberById(id: Int): Flow<Member?> {
        return memberDao.getMemberById(id)
    }

    suspend fun insertMember(member: Member): Long {
        return memberDao.insertMember(member)
    }

    suspend fun updateMember(member: Member) {
        memberDao.updateMember(member)
    }

    suspend fun deleteMember(member: Member) {
        memberDao.deleteMember(member)
    }

    suspend fun isNomorIndukExists(nomorInduk: String, excludeId: Int = 0): Boolean {
        return memberDao.checkNomorIndukExists(nomorInduk, excludeId) > 0
    }
}

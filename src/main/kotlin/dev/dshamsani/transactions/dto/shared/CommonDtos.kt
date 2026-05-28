package dev.dshamsani.transactions.dto.shared

import org.springframework.data.domain.Page


data class PagedResponse<T> (
    val data: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Long
)

fun <T> Page<T>.toPagedResponse(): PagedResponse<T> = PagedResponse(
    data = this.content,
    page = this.number,
    pageSize = this.size,
    totalPages = this.totalPages,
    totalElements = this.totalElements
)
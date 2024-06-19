document.addEventListener("DOMContentLoaded", function() {
    const paginationContainer = document.getElementById('pagination');
    const totalPages = parseInt(paginationContainer.dataset.totalPages);
    const currentPage = parseInt(paginationContainer.dataset.currentPage);
    generatePagination(totalPages, currentPage);
});

function generatePagination(totalPages, currentPage) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    // 첫번째 페이지 생성
    paginationContainer.innerHTML += createPageLink(1, currentPage === 1);

    if (totalPages > 1) {
        // 현재 페이지가 5 이상이면 ... 표시
        if (currentPage > 4) {
            paginationContainer.innerHTML += createEllipsis();
        }

        // 2페이지 ~ 마지막 바로앞 페이지 생성
        for (let i = Math.max(2, currentPage - 3); i <= Math.min(totalPages - 1, currentPage + 3); i++) {
            paginationContainer.innerHTML += createPageLink(i, currentPage === i);
        }

        // 현재 페이지가 총 페이지 -3 보다 작으면 ... 표시
        if (currentPage < totalPages - 3) {
            paginationContainer.innerHTML += createEllipsis();
        }

        // 마지막 페이지 표시
        paginationContainer.innerHTML += createPageLink(totalPages, totalPages === currentPage);
    }
}

function createPageLink(pageNum, isCurrent) {
    const url = new URL(window.location);
    const params = new URLSearchParams(url.search);
    params.set('page', pageNum - 1);
    url.search = params.toString();
    return `<a href='${url.toString()}' class='${isCurrent ? "selected" : ""}'>${pageNum}</a>`
}

function createEllipsis() {
    return `<span>...</span>`
}


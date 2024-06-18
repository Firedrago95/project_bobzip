document.addEventListener("DOMContentLoaded", function() {
    const totalPages = parseInt(document.getElementById('pagination').dataset.totalPages);
    const currentPage = parseInt(document.getElementById('pagination').dataset.currentPage);
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

        // 페이지 생성 최종페이지 바로 앞 페이지까지
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

function createPageLink(pageNum, isCurrent = false) {
    return `<a href='?page=${pageNum - 1}' class='${isCurrent ? "selected" : ""}'>${pageNum}</a>`
}

function createEllipsis() {
    return `<span>...</span>`
}


function generatePagination(totalPages, currentPage) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    // 첫번째 페이지와 4페이지 이상인 경우 '...' 표시
    if (totalPages > 1) {
        if (currentPage == 1) {
            paginationContainer.innerHTML += `<a href='?page=0' class="selected">1</a>`
        } else {
            paginationContainer.innerHTML += `<a href='?page=0'>1</a>`;
        }
        if (currentPage > 4) {
            paginationContainer.innerHTML += `<span>...</span>`;
        }
    }

    // 총 페이지 수 만큼 페이지 생성
    for(let i = Math.max(2, currentPage - 3); i <= Math.min(totalPages - 1, currentPage + 3); i++) {
        if (i == currentPage) {
            paginationContainer.innerHTML += `<a href='?page=${i-1}' class="selected">${i}</a>`;
        } else {
            paginationContainer.innerHTML += `<a href='?page=${i-1}'>${i}</a>`;
        }
    }

    // 마지막 페이지 생성
    if (totalPages > 1) {
        if (currentPage < totalPages - 3) {
            paginationContainer.innerHTML += `<span>...</span>`;
        }
        if (currentPage == totalPages) {
            paginationContainer.innerHTML += `<a href='?page=${totalPages-1}' class="selected">${totalPages}</a>`;
        } else {
            paginationContainer.innerHTML += `<a href='?page=${totalPages-1}'>${totalPages}</a>`;
        }
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const totalPages = document.getElementById('pagination').dataset.totalPages;
    const currentPage = document.getElementById('pagination').dataset.currentPage;
    generatePagination(parseInt(totalPages), parseInt(currentPage));
});
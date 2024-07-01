$(document).ready(function() {
    const recipeId = $('#comments-container').data('recipe-id');

    loadComments(recipeId);

    // 댓글 작성 폼 제출 이벤트 핸들러
    $('#comment-form').submit(function(event) {
        event.preventDefault();

        const commentText = $('#commentText').val();

        $.ajax({
            url: "/reply/add",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                comment: commentText,
                recipeId: recipeId
            }),
            success: function(response) {
                // 댓글 작성 후 댓글 목록을 다시 불러옴
                loadComments(recipeId);
                $('#commentText').val('');
            },
            error: function(xhr) {
                if (xhr.status == 401) {
                    alert(xhr.responseText);
                } else {
                    alert("댓글 작성중 문제가 발생했습니다.")
                }
            }
        });
    });
});

function loadComments(recipeId, page=0) {
    $.ajax({
        url: `/reply/all/${recipeId}?page=${page}`,
        method: 'GET',
        success: function(response) {
            $('#comments-container').empty();
            response.content.forEach(function(comment) {
                const date = new Date(comment.createdTime);
                const formattedDate =  new Intl.DateTimeFormat('ko-KR', {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                    hour: '2-digit',
                    minute: '2-digit'
                }).format(date);

                $('#comments-container').append(`
                    <div class="p-2">
                        <p class="mb-1"><strong>${comment.username}</strong></p>
                        <p class="text-muted small">${formattedDate}</p>
                        <p>${comment.comment}</p>
                    </div>
                    <hr>
                `);
            });

            // 페이지네이션 업데이트
            const totalPages = response.totalPages;
            const currentPage = response.number;
            generatePagination(totalPages, currentPage, recipeId);
        },
        error: function(error) {
            if (error.response) {
                error.response.data
            }
        }
    });
}

function generatePagination(totalPages, currentPage, recipeId) {
    const $paginationContainer = $('#pagination');
    $paginationContainer.empty();

    // 첫 페이지 링크 생성
    $paginationContainer.append(createPageLink(1, currentPage === 0, recipeId));

    if (totalPages > 1) {
        if (currentPage > 4) {
            $paginationContainer.append(createEllipsis());
        }

        for (let i = Math.max(1, currentPage - 3); i <= Math.min(totalPages - 2, currentPage + 3); i++) {
            $paginationContainer.append(createPageLink(i + 1, currentPage === i, recipeId));
        }

        if (currentPage < totalPages - 5) {
            $paginationContainer.append(createEllipsis());
        }

        // 마지막 페이지 링크 생성
        $paginationContainer.append(createPageLink(totalPages, currentPage === totalPages - 1, recipeId));
    }
}

function createPageLink(pageNum, isCurrent, recipeId) {
    const selectedClass = isCurrent ? 'selected' : '';
    return `
        <a class="page-link ${selectedClass}" href="#" onclick="loadComments(${recipeId}, ${pageNum - 1})" return false;>
            ${pageNum}
        </a>
    `;
}

function createEllipsis() {
    return "<span>...</span>";
}
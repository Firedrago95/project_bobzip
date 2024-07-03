$(document).ready(function() {
    const recipeId = $('#comments-container').data('recipe-id');

    loadComments(recipeId);

    // 댓글 작성 폼 제출 이벤트 핸들러
    $('#comment-form').submit(function(event) {
        event.preventDefault();
        const commentText = $('#commentText').val();
        addComment(recipeId, commentText);
    });
});

function addComment(recipeId, commentText) {
    $.ajax({
        url: "/reply/add",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            comment: commentText,
            recipeId: recipeId
        }),
        success: function(response) {
            // 댓글 작성 후, 댓글 HTML 추가
            renderComments(response);
            $('#commentText').val('');

            // 페이지네이션 업데이트
            const totalPages = response.totalPages;
            const currentPage = response.number;
            generatePagination(totalPages, currentPage, recipeId);
        },
        error: function(xhr) {
            if (xhr.status == 401) {
                alert(xhr.responseText);
            } else {
                alert("댓글 작성중 문제가 발생했습니다.")
            }
        }
    });
}

function loadComments(recipeId, page = 0) {
    $.ajax({
        url: `/reply/all/${recipeId}?page=${page}`,
        method: 'GET',
        success: function(response) {
            renderComments(response);

            // 페이지네이션 업데이트
            const totalPages = response.totalPages;
            const currentPage = response.number;
            generatePagination(totalPages, currentPage, recipeId);
        },
        error: function(error) {
            if (error.response) {
                error.response.data;
            }
        }
    });
}

function renderComments(response) {
    $('#comments-container').empty();
    response.content.forEach(appendCommentHtml);
}

function appendCommentHtml(comment) {
    const date = new Date(comment.createdTime);
    const formattedDate = new Intl.DateTimeFormat('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    }).format(date);

    $('#comments-container').append(`
        <div class="p-2 position-relative" id="comment-${comment.id}">
            <div class="d-flex justify-content-between">
                <p class="mb-1 fs-5"><strong>${comment.username}</strong></p>
                <div>
                    <button class="btn btn-sm btn-link" onclick="editComment(${comment.id}, '${comment.comment}')">수정</button>
                    <button class="btn btn-sm btn-link text-danger" onclick="deleteComment(${comment.id})">삭제</button>
                </div>
            </div>
            <p class="text-muted small">${formattedDate}</p>
            <p class="fs-6" id="comment-text">${comment.comment}</p>
        </div>
        <hr>
    `);
}

function editComment(commentId, currentText) {
    // 기존 폼이 있으면 제거
    $('#edit-form').remove();

    const editFormHtml = `
        <form id="edit-form" class="mt-3">
            <textarea id="edit-comment-text" class="form-control mb-2">${currentText}</textarea>
            <button type="button" class="btn btn-primary" onclick="submitEdit(${commentId})">수정하기</button>
            <button type="button" class="btn btn-secondary" onclick="cancelEdit()">닫기</button>
        </form>
    `;

    // 댓글 아래에 수정 폼 추가
    $(`#comment-${commentId}`).append(editFormHtml);
}

function submitEdit(commentId) {
    const updatedText = $('#edit-comment-text').val();

    $.ajax({
        url: `/reply/edit/${commentId}`,
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: {
            comment: updatedText
        },
        success: function(response) {
            // 댓글 내용을 업데이트
            $(`#comment-${commentId} #comment-text`).text(updatedText);
            // 수정 폼 제거
            $('#edit-form').remove();
        },
        error: function(xhr) {
            if (xhr.status == 401) {
                alert(xhr.responseText);
            } else {
                alert("댓글 수정중 문제가 발생했습니다.")
            }
        }
    });
}

function cancelEdit() {
    $('#edit-form').remove();
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
        <a class="page-link ${selectedClass}" onclick="loadComments(${recipeId}, ${pageNum - 1})" return false;>
            ${pageNum}
        </a>
    `;
}

function createEllipsis() {
    return "<span>...</span>";
}

function deleteComment(commentId) {
    if (window.confirm("댓글을 삭제하시겠습니까?")) {
        $.ajax({
            url: `/reply/delete/${commentId}`,
            method: "POST",
            success: function(response) {
                // 댓글 작성 후, 댓글 HTML 추가
                renderComments(response);

                // 페이지네이션 업데이트
                const totalPages = response.totalPages;
                const currentPage = response.number;
                generatePagination(totalPages, currentPage, recipeId);
            },
            error: function(xhr) {
                if (xhr.status == 401) {
                    alert(xhr.responseText);
                } else {
                    alert("댓글을 삭제하는 도중 문제가 발생했습니다.");
                }
            }
        });
    }
}

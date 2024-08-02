$(document).ready(function() {
    loadFridgeIngredients();
});

function loadFridgeIngredients() {
    $.ajax({
        url: "/fridge/all",
        method: "GET",
        success: function(response) {
            response.ingredientNames.forEach(appendIngredient);
        },
        error: function(xhr) {
            if (xhr.status == 401) {
                const currentUrl = window.location.href;
                alert(xhr.responseText);
                window.location.href = "/members/login?redirectURL=" + encodeURIComponent(currentUrl);
            } else {
                alert("댓글 작성중 문제가 발생했습니다.")
            }
        }
    });
}

function appendIngredient(ingredient) {
    const ingredientList = $('#ingredientList');

    const listItem = $('<div></div>')
        .addClass('ingredient-item col-2')
        .text(ingredient);

    const inputForm = $('<input type="hidden" name="ingredients[]" value="'+ingredient+'">');

    const deleteButton = $('<button></button>')
        .text('x')
        .addClass('btn btn-outline-danger btn-sm')
        .css({
            'margin-left': '.5rem',
            '--bs-btn-padding-y': '.1rem',
            '--bs-btn-padding-x': '.4rem',
            '--bs-btn-font-size': '.8rem'
        })
        .click( function() {
            $(this).parent().remove();
        });

    listItem.append(deleteButton);
    listItem.append(inputForm);
    ingredientList.append(listItem);
}

function saveIngredient() {
    const answer = confirm("냉장고 재료를 저장하시겠습니까?");

    if (answer) {
        const ingredients = $('input[name="ingredients[]"]').map(function () {
            return $(this).val();
        }).get();

        $.ajax({
            url: "/fridge/add",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(ingredients),
            success: function(response) {
                alert("냉장고 저장을 완료했습니다.");
            },
            error: function(xhr) {
                if (xhr.status == 401) {
                    const currentUrl = window.location.href;
                    alert(xhr.responseText);
                    window.location.href = "/members/login?redirectURL=" + encodeURIComponent(currentUrl);
                } else {
                    alert("댓글 작성중 문제가 발생했습니다.")
                }
            }
        });
    }
}

function searchRecipeByIngredients(page = 0) {
    const ingredients = $('input[name="ingredients[]"]').map(function() {
        return $(this).val();
    }).get();

    $.ajax({
        url: `/recipe/searchByIngredients?page=${page}`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(ingredients),
        success: function(response) {
            const recipeList = $('#recipeList');
            recipeList.empty();
            response.content.forEach(appendRecipeSearchHTML);

            const totalPages = response.totalPages;
            const currentPage = response.number;
            generatePagination(totalPages, currentPage);
        },
        error: function(xhr) {
            if (xhr.status == 401) {
                const currentUrl = window.location.href;
                alert(xhr.responseText);
                window.location.href = "/members/login?redirectURL=" + encodeURIComponent(currentUrl);
            } else {
                alert("댓글 작성중 문제가 발생했습니다.")
            }
        }
    });
}

function appendRecipeSearchHTML(dto) {
    const recipeList = $('#recipeList');
    const card = $('<div></div>').addClass('card recipe-card');
    const cardBody = $('<div></div>').addClass('card-body');
    const cardTitle = $('<a></a>')
        .addClass('card-title fw-bold fs-5')
        .attr('href', '/recipe/'+dto.recipeId)
        .text(dto.recipeName);
    cardBody.append(cardTitle);

    const availableIngredients = $('<div></div>').addClass('ingredients-list');
    availableIngredients.append($('<strong></strong>').text('가진 재료 : '));
    dto.availableIngredients.forEach((ingredient, index) => {
        availableIngredients.append($('<span></span>').addClass('ingredient-item text-primary').text(ingredient));
        if (index < dto.availableIngredients.length - 1) {
            availableIngredients.append($('<span></span>').text(' / '));
        }
    });

    const requiredIngredients = $('<div></div>').addClass('ingredients-list');
    requiredIngredients.append($('<strong></strong>').text('필요한 재료 : '));
    dto.requiredIngredients.forEach((ingredient, index) => {
        requiredIngredients.append($('<span></span>').addClass('ingredient-item text-danger').text(ingredient));
        if (index < dto.requiredIngredients.length - 1) {
            requiredIngredients.append($('<span></span>').text(' / '));
        }
    });

    cardBody.append(availableIngredients);
    cardBody.append(requiredIngredients);
    card.append(cardBody);
    recipeList.append(card);
}

function generatePagination(totalPages, currentPage) {
    const paginationContainer = $('#pagination');
    paginationContainer.empty();

    paginationContainer.append(createPageLink(1, currentPage === 0));

    if (totalPages > 1) {
        if (currentPage > 4) {
            paginationContainer.append(createElias());
        }

        for (let i = Math.max(1, currentPage - 3); i <= Math.min(totalPages - 2 , currentPage + 3); i++) {
            paginationContainer.append(createPageLink(i + 1, currentPage === i));
        }

        if (currentPage < totalPages - 5) {
            $paginationContainer.append(createEllipsis());
        }

        paginationContainer.append(createPageLink(totalPages, currentPage === totalPages - 1));
    }
}

function createPageLink(pageNum, isCurrent) {
    const selectedClass = isCurrent ? 'select' : '';
    return `<a class="page-link ${selectedClass}" onclick="searchRecipeByIngredients(${pageNum - 1})" return false;>
                ${pageNum}
            </a>`;
}

function createEllipsis() {
    return "<span>...</span>";
}